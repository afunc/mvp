package org.afunc.android.mvp;


import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


/**
 * <li>Created by 紫紫 on 2017/9/7</li>
 * <li>Q157596462@outlook.com</li>
 * <li>描述：继承了superActivity </li>
 */
public abstract class ToolbarActivity<P extends SuperPresenter> extends SuperActivity<P> {

    //设置 toolbar 是否显示返回键
    private Toolbar mToolbar;


    protected boolean showHomeBack() {
        return false;
    }

    @Override
    @CallSuper
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = $(setToolbarId());
    }

    protected abstract
    @IdRes
    int setToolbarId();

    @CallSuper
    protected void onCreateAfterSuper() {
        if (null != mToolbar) {
            modifyToolbar(mToolbar);
            setSupportActionBar(mToolbar);
            if (null != getSupportActionBar())
                getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeBack());
        }
    }


    /**
     * 修改Toolbar
     *
     * @param mToolbar Toolbar
     */
    protected void modifyToolbar(@NonNull Toolbar mToolbar) {

    }

    @Override
    @CallSuper
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home && showHomeBack()) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
