package com.android.common.router.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 请添加注释说明
 *
 * @author rade.chan
 * @date 2018/10/8.
 */
public interface IRouterProvider extends IProvider {

    IRouterProvider build();

    boolean router(final Context context);

    IRouterProvider type(int type);


    IRouterProvider ext(String ext);

    IRouterProvider memberID(long memberID);

    IRouterProvider bizId(long bizId);

}
