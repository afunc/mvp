package org.afunc.mvp;

import java.lang.annotation.*;

/**
 * @author 紫紫 on 2017/8/7
 *         Q157596462@outlook.com
 *         描述：确认有 某个 类
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePresenter {
    Class<? extends SuperPresenter> value();
}
