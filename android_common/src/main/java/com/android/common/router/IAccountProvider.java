package com.android.common.router;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author wangwei
 * @date 2020/8/26.
 */
public interface IAccountProvider extends IProvider {
    long getUserId();

}
