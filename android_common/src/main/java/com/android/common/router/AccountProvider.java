package com.android.common.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * @author wangwei
 * @date 2020/8/26.
 */
@Route(path = CommonProviderPath.ACCOUNT_PROVIDER)
public class AccountProvider implements IAccountProvider {
    @Override
    public long getUserId() {
        return 1222;
    }

    @Override
    public void init(Context context) {

    }
}
