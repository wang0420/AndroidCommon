package com.module.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.module.ui.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_danmuku.*
import java.util.concurrent.TimeUnit


class DanmukuActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danmuku)

        for (i in 1..5) {
            layout_live_video_danmaku.handleOtherDanmaku("我是第${i}条弹幕")
        }

        Observable.interval(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val randoms = (0..100).random()
                    print(randoms)
                    layout_live_video_danmaku.handleOtherDanmaku("我是第${randoms}条弹幕")
                }


    }


}
