package com.android.common.iview;

import com.android.common.base.BaseView;
import com.android.common.bean.DataBean;


/**
 * 处理业务需要哪些方法
 */
public interface MainView extends BaseView {

    void getDataSuccess(DataBean model);

}
