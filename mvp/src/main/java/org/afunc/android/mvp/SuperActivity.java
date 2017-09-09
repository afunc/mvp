package org.afunc.android.mvp;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import org.afunc.android.util.DimenUtils;
import org.afunc.android.util.LogUtils;

import java.lang.annotation.Annotation;

/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：MVP模型中把Activity作为view层，可通过getPresenter()调用对应的presenter实例
 */
public abstract class SuperActivity<P extends SuperPresenter> extends AppCompatActivity {

    private final String TAG = "SuperActivity";
    private Dialog mDialog;
    private Dialog mLoadingDialog;
    protected P mPresenter;


    protected abstract @LayoutRes
    int setContentViewId();

    /**
     * onCreate final 避免被重写 可使用 beforeSuper 和 afterSuper
     */
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        onCreateBeforeSuper(savedInstanceState);
        super.onCreate(savedInstanceState);
        attachPresenter();
        setContentView(setContentViewId());
        if (null != getIntent()) {
            handIntent(getIntent());
        }
        onCreateAfterSuper();
    }

    /**
     * @param intent 不必判空
     */
    protected void handIntent(Intent intent) {
    }

    protected void onCreateBeforeSuper(Bundle savedInstanceState) {
    }

    protected void onCreateAfterSuper() {
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V $(@IdRes int id) {
        return (V) super.findViewById(id);
    }

    public P getPresenter() {
        return mPresenter;
    }

    /**
     * onPostCreate final 避免被重写 可使用 beforeSuper 和 afterSuper
     */
    //在onStart之后回调
    @Override
    protected final void onPostCreate(@Nullable Bundle savedInstanceState) {
        onPostCreateBeforeSuper(savedInstanceState);
        super.onPostCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
        onPostCreateAfterSuper();
    }

    protected void onPostCreateAfterSuper() {
    }

    protected void onPostCreateBeforeSuper(Bundle savedInstanceState) {
    }

    /**
     * onPostResume final 避免被重写 可使用 beforeSuper 和 afterSuper
     */
    //在onResume之后回调
    @Override
    protected final void onPostResume() {
        onPostResumeBeforeSuper();
        super.onPostResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
        onPostResumeAfterSuper();
    }

    private void onPostResumeAfterSuper() {

    }

    private void onPostResumeBeforeSuper() {
    }

    /**
     * 生成 P 对象
     */
    public void attachPresenter() {
        Annotation[] annotations = getClass().getAnnotations();
        if (annotations.length > 0) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequirePresenter) {
                    RequirePresenter presenter = (RequirePresenter) annotation;
                    try {
                        mPresenter = (P) presenter.value().newInstance();
                        mPresenter.attachView(this);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                        LogUtils.e(TAG, e);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        LogUtils.e(TAG, e);
                    }
                }
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.setContentView(layoutResID);
    }

    /**
     * 显示进度条的dialog
     */
    public void showLoadingDialog(String msg) {
        if (mLoadingDialog == null) {
            ProgressBar progressBar = new ProgressBar(this);
            progressBar.setPadding(DimenUtils.dp2px(this, 16),
                    DimenUtils.dp2px(this, 16),
                    DimenUtils.dp2px(this, 16),
                    DimenUtils.dp2px(this, 16));
            progressBar.setBackgroundResource(android.R.color.transparent);
            mLoadingDialog = new AlertDialog.Builder(this)
                    .setView(progressBar)
                    .setMessage(msg)
//                    .setCancelable(false)
                    .create();

        }
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.show();
    }


    public void showLoadingDialog(@NonNull Dialog dialog) {
        dismissLoadingDialog();
        mLoadingDialog = dialog;
        mLoadingDialog.show();
    }


    /**
     * 隐藏销毁加载框
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
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
    protected final void onDestroy() {
        onDestroyBeforeSuper();
        super.onDestroy();
        dismissDialog();
        dismissLoadingDialog();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mPresenter = null;
        onDestroyAfterSuper();
    }

    protected void onDestroyAfterSuper() {
    }

    protected void onDestroyBeforeSuper() {
    }

    //返回键返回事件 默认结束自己
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
