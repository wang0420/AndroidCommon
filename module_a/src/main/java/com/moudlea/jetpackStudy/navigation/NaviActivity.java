package com.moudlea.jetpackStudy.navigation;

import android.os.Bundle;

import com.moudlea.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

/**
 * todo 目前已知使用Navigation框架，配置navGraph需要AndroidStudio3.2以上版本，在settings--experimental--enable navigation editor
 * todo 就能使用类似于写layout的形式，看navGraph的配置了。而且配置的fragment每次触发都是创建新的对象实例（返回不会新建），
 * 这个google官方已经知晓，切不修改，认为这个是符合新的设计理念规范的。
 * navigation框架的演示
 *
 *
 * Navigation 是 Google 新推出的库，其作用简单的说就是用于简化界面间跳转的，Activity 和 Fragment 都可以
 *
 * 使用navigation跳转的fragment，似乎不支持单例模式，都是新创建对象。google官方知晓，似乎这就是他们的一个设计规范。
 *
 * navi——graph编辑器，需要在androidStudio3。2版本以上，settings--experimental中，enable一下。
 */
public class NaviActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    /**
     * navigation相关的操作
     *
     * @return 是否向上返回 栈
     */
    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.fg_main_navi).navigateUp();
    }
}
