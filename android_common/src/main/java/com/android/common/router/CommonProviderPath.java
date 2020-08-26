package com.android.common.router;

/**
 * provider router路径
 * 三级命名  路径命名规则  /所在的module/所属的模块/类名
 *
 */
public interface CommonProviderPath {

    String ACCOUNT_PROVIDER = ModuleName.COMMON + "/provider/AccountProvider";
    String ROUTER_PROVIDER = ModuleName.COMMON + "/provider/RouterProvider";

}
