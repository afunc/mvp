package org.afunc.android.mvp;

import android.os.Bundle;
import android.support.annotation.*;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;


/**
 * <li>Created by 紫紫 on 2017/9/7</li>
 * <li>Q157596462@outlook.com</li>
 * <li>描述：继承了superActivity </li>
 */
public abstract class ToolbarActivity<P extends SuperPresenter> extends SuperActivity<P> {

    //设置 toolbar 是否显示返回键
    private Toolbar mToolbar;
    private boolean isHomeBack = false;


    protected void setHomeBack(boolean homeBack) {
        isHomeBack = homeBack;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = $(setToolbarId());
    }

    protected abstract @IdRes
    int setToolbarId();

    /**
     * @return 默认 主题设置为 Theme_AppCompat_Light_NoActionBar
     */
    @StyleRes
    protected int setActivityTheme() {
        return android.support.v7.appcompat.R.style.Theme_AppCompat_Light_NoActionBar;
    }

    @Override
    @CallSuper
    protected void onCreateAfterSuper() {
        super.onPostCreateAfterSuper();
        if (mToolbar != null) {
            modifyToolbar(mToolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeBack);
        }
    }

    protected void modifyToolbar(Toolbar mToolbar) {

    }

    @Override
    @CallSuper
    protected void onCreateBeforeSuper(@Nullable Bundle savedInstanceState) {
        super.onCreateBeforeSuper(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(setActivityTheme());
    }

    @Override
    @CallSuper
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && isHomeBack) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
