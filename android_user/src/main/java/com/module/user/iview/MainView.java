package com.module.user.iview;

import com.basemodule.base.BaseView;
import com.module.user.bean.DataBean;


/**
 * 处理业务需要哪些方法
 */
public interface MainView extends BaseView {

    void getDataSuccess(DataBean model);

}
