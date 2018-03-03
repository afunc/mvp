package org.afunc.mvp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Annotation;

/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：MVP模型中把Activity作为view层，可通过getPresenter()调用对应的presenter实例
 *
 * @author 紫紫
 */
public abstract class AfuncActivity<P extends AfuncPresenter> extends AppCompatActivity {

    protected String TAG = "AfuncActivity";
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

    @CallSuper
    protected void handIntent(@NonNull Intent intent) {
    }

    @CallSuper
    protected void beforeCreate(@Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
    }

    @CallSuper
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
    final void attachPresenter() {
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
                        Log.e(TAG,"presenter bind fail!",e);
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

    @CallSuper
    protected void beforeSetContentView() {

    }

    @CallSuper
    protected void afterSetContentView() {
    }


    /**
     * onDestroy final 避免被重写 可使用 beforeSuper 和 afterSuper
     */
    @Override
    @CallSuper
    protected final void onDestroy() {
        beforeDestroy();
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter = null;
        }
        afterDestroy();
    }

    @CallSuper
    protected void afterDestroy() {
    }

    @CallSuper
    protected void beforeDestroy() {
    }
}
