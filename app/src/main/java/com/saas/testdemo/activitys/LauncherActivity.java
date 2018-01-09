/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.saas.testdemo.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LauncherActivity extends MvpBaseActivity<RequestView, RequestPresenter> implements RequestView {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().clickRequest("开始请求拉");
            }
        });

    }

    @Override
    protected RequestPresenter createPresenter() {
        return new RequestPresenter();
    }


    @Override
    public void requestLoading() {
        button.setText("请求中,请稍后...");
    }

    @Override
    public void resultSuccess(String result) {
        button.setText(result);
    }

    @Override
    public void resultFailure(String result) {

    }
}