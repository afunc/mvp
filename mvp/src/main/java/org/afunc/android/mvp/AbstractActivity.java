package org.afunc.android.mvp;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import org.afunc.android.util.DimenUtils;
import org.afunc.android.util.LogUtils;

import java.lang.annotation.Annotation;

/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：MVP模型中把Activity作为view层，可通过getPresenter()调用对应的presenter实例
 *
 * @author 紫紫
 */
public abstract class AbstractActivity<P extends SuperPresenter> extends AppCompatActivity {

    private final String TAG = "SuperActivity";
    protected Dialog mDialog;
    private P mPresenter;


    /**
     * onCreate final 避免被重写 可使用 beforeSuper 和 afterSuper
     */
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        attachPresenter();
        setContentView(setContentResource());
        if (null != getIntent()) {
            handIntent(getIntent());
        }
        afterCreate();
    }

    /**
     * 设置布局
     *
     * @return 返回一个布局ID
     */
    protected abstract @LayoutRes
    int setContentResource();

    protected void handIntent(@NonNull Intent intent) {
    }

    protected void beforeCreate(@Nullable Bundle savedInstanceState) {
    }

    protected void afterCreate() {
        if (null != mPresenter) {
            mPresenter.init();
        }
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findById(@IdRes int id) {
        return (V) super.findViewById(id);
    }

    /**
     * @return 与此activity 绑定的presenter
     */
    public @Nullable
    P getPresenter() {
        return mPresenter;
    }

    /**
     * 生成 P 对象
     */
    @SuppressWarnings("unchecked")
    private final void attachPresenter() {
        Annotation[] annotations = getClass().getAnnotations();
        if (annotations.length > 0) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequirePresenter) {
                    RequirePresenter presenter = (RequirePresenter) annotation;
                    try {
                        mPresenter = (P) presenter.value().newInstance();
                        mPresenter.attachView(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.e(TAG, e);
                    }
                }
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    @CallSuper
    public void setContentView(@LayoutRes int layoutResID) {
        beforeSetContentView();
        super.setContentView(layoutResID);
        afterSetContentView();
    }

    protected void beforeSetContentView() {

    }

    protected void afterSetContentView() {
    }

    /**
     * 显示进度条的dialog
     */
    public void showLoadingDialog(String msg) {
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setPadding(DimenUtils.dp2px(16),
                DimenUtils.dp2px(16),
                DimenUtils.dp2px(16),
                DimenUtils.dp2px(16));
        progressBar.setBackgroundResource(android.R.color.transparent);
        mDialog = new AlertDialog.Builder(this)
                .setView(progressBar)
                .setMessage(msg)
                .create();
        mDialog.setCanceledOnTouchOutside(false);
        showDialog(mDialog);
    }


    public void showDialog(@NonNull Dialog dialog) {
        dismissDialog();
        mDialog = dialog;
        mDialog.show();
    }

    /**
     * 隐藏销毁 对话框
     */
    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * onDestroy final 避免被重写 可使用 beforeSuper 和 afterSuper
     */
    @Override
    @CallSuper
    protected final void onDestroy() {
        beforeDestroy();
        super.onDestroy();
        dismissDialog();
        if (mPresenter != null) {
            mPresenter = null;
        }
        afterDestroy();
    }

    protected void afterDestroy() {
    }

    protected void beforeDestroy() {
    }

    /**
     * 如果是返回键且当前没有fragment 的情况下 就调用 finish() 其他情况 调用 super.onKeyDown(keyCode, event);
     *
     * @param keyCode 按键代码
     * @param event   事件
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}