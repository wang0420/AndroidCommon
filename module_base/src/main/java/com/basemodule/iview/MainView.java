package com.basemodule.iview;

import com.basemodule.base.BaseView;
import com.basemodule.bean.DataBean;


/**
 * 处理业务需要哪些方法
 */
public interface MainView extends BaseView {

    void getDataSuccess(DataBean model);

}
