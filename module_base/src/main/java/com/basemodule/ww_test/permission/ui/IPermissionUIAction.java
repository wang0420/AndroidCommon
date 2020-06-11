package com.basemodule.ww_test.permission.ui;

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/22 11:24
 * ModifyTime: 11:24
 * Description:
 */
public interface IPermissionUIAction {

    void onCloseClick(ZPermissionUI permissionUI);

    void onSubmitClick(ZPermissionUI permissionUI, String[] permissions);
}
