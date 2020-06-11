package com.basemodule.net;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basemodule.BaseApplication;
import com.basemodule.R;
import com.basemodule.za_test.net.LoginService;
import com.basemodule.za_test.net.MessageCodeEntity;
import com.basemodule.za_test.net.ZANetwork;
import com.basemodule.za_test.net.ZAResponse;
import com.basemodule.za_test.net.utils.Callback;
import com.basemodule.za_test.net.utils.ZANetworkCallback;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;


public class NewNetActivity extends RxAppCompatActivity {
    private TextView text;
    private Button button1, go;
    // https://www.wanandroid.com/article/list/0/json?cid=60


    /*使用bindToLifecycle()
    https://www.jianshu.com/p/8311410de676
以Activity为例，在Activity中使用bindToLifecycle()方法，完成Observable发布的事件和当前的组件绑定，实现生命周期同步。从而实现当前组件生命周期结束时，自动取消对Observable订阅，代码如下：
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        initViews();
        // 当执行onDestory()时， 自动解除订阅
      /*  Observable.interval(3, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.w("TAG", "Unsubscribing subscription from onCreate()");
                    }
                })
                //bindUntilEvent()，内部传入指定生命周期参数
                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                //  .compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long num) throws Exception {
                        Log.w("TAG", "Started in onCreate(), running until onDestory(): " + num);
                    }
                });
*/
    }

    protected void initViews() {
        text = findViewById(R.id.text);
        button1 = findViewById(R.id.button1);
        go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   testDestoryRequest();
                startRequest();
            }
        });

    }

    /**
     * 测试关闭Activity 后 自动取消订阅
     */
    private void testDestoryRequest() {
        Observable observable = Observable.interval(3, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.w("TAG", "Unsubscribing subscription from onCreate()");
                    }
                });
        ZANetwork.with(this)
                .api(observable)
                .callback(new Callback<Long>() {
                    @Override
                    public void onNext(Long response) {
                        Log.w("TAG", "next" + response);

                    }
                });

    }


    private void startRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("phone", "18565851235");
     /*   Observable<ZAResponse<MessageCodeEntity>> observable
                = ZANetwork.getService(LoginService.class)
                .login(params);
*/
        ZANetwork.with(this)
                .api(ZANetwork.getService(LoginService.class).login(params))
                .callback(new ZANetworkCallback<ZAResponse<MessageCodeEntity>>() {

                    @Override
                    public void onBusinessSuccess(ZAResponse<MessageCodeEntity> response) {
                        if (response.data != null) {
                            text.setText("" + new Gson().toJson(response.data));
                            Toast.makeText(BaseApplication.getInstance(), response.data.toString(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BaseApplication.getInstance(), "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onBusinessError(int errorCode, String errorMessage) {
                        text.setText(errorCode + " " + errorMessage);
                        Toast.makeText(BaseApplication.getInstance(), errorMessage, Toast.LENGTH_SHORT).show();
                    }

                });

    }


}


