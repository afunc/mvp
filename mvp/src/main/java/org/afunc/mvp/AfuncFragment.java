package org.afunc.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.lang.annotation.Annotation;

/**
 * @author 紫紫 on 2017/8/7
 *         Q157596462@outlook.com
 *         描述：MVP模型中把Fragment作为view层，可通过getPresenter()调用对应的presenter实例
 */
public abstract class AfuncFragment<T extends AfuncPresenter> extends Fragment {

    protected String TAG = "AfuncFragment";

    private T mPresenter;
    protected Context mContext;

    private View mView;
    private AfuncActivity activity;


    /**
     * 绑定上下文 到 mContext 与activity中
     *
     * @param context 上下文对象
     */
    @Override
    @CallSuper
    public final void onAttach(Context context) {
        beforeAttach(context);
        super.onAttach(context);
        mContext = context;
        activity = (AfuncActivity) context;
        afterAttach();
    }

    @CallSuper
    protected void afterAttach() {

    }

    @CallSuper
    protected void beforeAttach(Context context) {

    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findById(@IdRes int id) {
        View view = mView.findViewById(id);
        return (V) view;
    }

    /**
     * onCreate final 避免被重写 可使用 beforeSuper 和 afterSuper
     * 在此方法中 调用了 attachPresenter 来绑定 P
     */
    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        attachPresenter();
        afterCreate();
    }

    /**
     * 在attachPresenter 后执行
     */
    @CallSuper
    protected void afterCreate() {

    }

    /**
     * 在onCreate 中先执行
     *
     * @param savedInstanceState 系统状态 bundle
     */
    @CallSuper
    protected void beforeCreate(Bundle savedInstanceState) {
    }

    /**
     * 设置布局
     *
     * @return 布局文件ID
     */
    protected abstract @LayoutRes
    int setContentResource();


    @NonNull
    @Override
    @CallSuper
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeCreatedView(inflater, container, savedInstanceState);
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(setContentResource(), container, false);
        afterCreatedView(mView);
        return mView;
    }

    /**
     * 在 onCreateView 返回前执行
     *
     * @param mView fragment 的视图
     */
    protected void afterCreatedView(View mView) {
        if (null != mPresenter) {
            mPresenter.init();
        }
    }

    /**
     * @param inflater           解析器
     * @param container          viewGroup
     * @param savedInstanceState 系统状态 bundle
     */
    @CallSuper
    protected void beforeCreatedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                        mPresenter = (T) presenter.value().newInstance();
                        mPresenter.attachView(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG,"presenter bind fail!",e);
                    }
                }
            }
        }
    }


    /**
     * onDestroy final 避免被重写 可使用 beforeSuper 和 afterSuper
     */
    @Override
    public final void onDestroy() {
        beforeDestroy();
        super.onDestroy();
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
     * @return 拥有此fragment 的 superActivity
     */
    protected AfuncActivity getHoldingActivity() {
        return activity;
    }


    protected T getPresenter() {
        return mPresenter;
    }
}
