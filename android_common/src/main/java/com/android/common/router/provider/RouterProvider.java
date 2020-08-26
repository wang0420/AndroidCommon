package com.android.common.router.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.common.router.CommonProviderPath;
import com.android.common.router.ZARouter;


/**
 * router的接口实现类
 */
@Route(path = CommonProviderPath.ROUTER_PROVIDER)
public class RouterProvider implements IRouterProvider {

    private ZARouter mZARouter;

    @Override
    public IRouterProvider build() {
        mZARouter = ZARouter.build();
        return this;
    }


    @Override
    public boolean router(Context context) {
        if (mZARouter != null) {
            mZARouter.router(context);
        }
        return false;
    }

    @Override
    public IRouterProvider type(int type) {
        if (mZARouter != null) {
            mZARouter.type(type);
        }
        return this;
    }


    @Override
    public IRouterProvider ext(String ext) {
        if (mZARouter != null) {
            mZARouter.ext(ext);
        }
        return this;
    }

    @Override
    public IRouterProvider memberID(long memberID) {
        if (mZARouter != null) {
            mZARouter.memberID(memberID);
        }
        return this;
    }

    @Override
    public IRouterProvider bizId(long bizId) {
        if (mZARouter != null) {
            mZARouter.bizID(bizId);
        }
        return this;
    }

    @Override
    public void init(Context context) {

    }
}
