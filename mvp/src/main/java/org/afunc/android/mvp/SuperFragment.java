package org.afunc.android.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.afunc.android.util.LogUtils;

import java.lang.annotation.Annotation;

/**
 * Created by 紫紫 on 2017/8/7
 * Q157596462@outlook.com
 * 描述：MVP模型中把Fragment作为view层，可通过getPresenter()调用对应的presenter实例
 */
public abstract class SuperFragment<T extends SuperPresenter> extends Fragment {

    private final String TAG = "SuperFragment";

    private T mPresenter;
    private Context mContext;

    private View mView;
    private SuperActivity activity;


    /**
     * 绑定上下文 到 mContext 与activity中
     *
     * @param context 上下文对象
     */
    @Override
    @CallSuper
    public void onAttach(Context context) {
        onAttachBeforeSuper(context);
        super.onAttach(context);
        mContext = context;
        activity = (SuperActivity) context;
        onAttachAfterSuper();
    }

    protected void onAttachAfterSuper() {

    }

    protected void onAttachBeforeSuper(Context context) {

    }


    @SuppressWarnings("unchecked")
    public <V extends View> V $(@IdRes int id) {
        View view = mView.findViewById(id);
        return (V) view;
    }

    /**
     * onCreate final 避免被重写 可使用 beforeSuper 和 afterSuper
     * 在此方法中 调用了 attachPresenter 来绑定 P
     */
    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onCreateBeforeSuper(savedInstanceState);
        super.onCreate(savedInstanceState);
        attachPresenter();
        onCreateAfterSuper();
    }

    /**
     * 在attachPresenter 后执行
     */
    protected void onCreateAfterSuper() {
    }

    /**
     * 在onCreate 中先执行
     *
     * @param savedInstanceState 系统状态 bundle
     */
    protected void onCreateBeforeSuper(Bundle savedInstanceState) {
    }

    protected abstract @LayoutRes
    int setContentViewId();


    @NonNull
    @Override //container ---> activity
    @CallSuper
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onCreatedViewBeforeSuper(inflater, container, savedInstanceState);
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(setContentViewId(), container, false);
        onCreatedViewAfterSuper(mView);
        return mView;
    }

    /**
     * 在 onCreateView 返回前执行
     *
     * @param mView fragment 的视图
     */
    protected void onCreatedViewAfterSuper(View mView) {
    }

    /**
     * @param inflater           解析器
     * @param container          viewGroup
     * @param savedInstanceState 系统状态 bundle
     */
    protected void onCreatedViewBeforeSuper(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }


    /**
     * 生成 P 对象
     */
    @SuppressWarnings("unchecked")
    private void attachPresenter() {
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
                        LogUtils.e(TAG + "", e);
                    }
                }
            }
        }
    }


    /**
     * onDestroy final 避免被重写 可使用 beforeSuper 和 afterSuper
     */
    @Override
    @CallSuper
    public void onDestroy() {
        onDestroyBeforeSuper();
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter = null;
        }
        onDestroyAfterSuper();
    }

    protected void onDestroyAfterSuper() {
    }

    protected void onDestroyBeforeSuper() {
    }


    /**
     * @return 拥有此fragment 的 superActivity
     */
    public SuperActivity getHoldingActivity() {
        return activity;
    }


    public T getPresenter() {
        return mPresenter;
    }

    public Context getContext() {
        return mContext;
    }
}
