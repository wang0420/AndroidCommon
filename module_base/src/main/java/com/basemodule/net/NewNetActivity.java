package com.basemodule.net;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basemodule.BaseApplication;
import com.basemodule.R;
import com.basemodule.za_test.net.LoginService;
import com.basemodule.za_test.net.ZANetwork;
import com.basemodule.za_test.net.ZAResponse;
import com.basemodule.za_test.net.utils.ZANetworkCallback;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.HashMap;

import androidx.annotation.Nullable;
import io.reactivex.Observable;


public class NewNetActivity extends RxAppCompatActivity {
    private TextView text;
    private Button button1, go;
    // https://www.wanandroid.com/article/list/0/json?cid=60


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        initViews();
    }

    protected void initViews() {
        text = findViewById(R.id.text);
        button1 = findViewById(R.id.button1);
        go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRequest();
            }
        });

    }


    private void startRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("phone", "18565851235");
        Observable<ZAResponse<ZAResponse.Data>> observable
                = ZANetwork.getService(LoginService.class)
                .login(params);

        ZANetwork.with(this)
                .api(observable)
                .callback(new ZANetworkCallback<ZAResponse<ZAResponse.Data>>() {

                    @Override
                    public void onBusinessSuccess(ZAResponse<ZAResponse.Data> response) {
                        if (response.data != null) {
                            text.setText(response.data.toString());
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


