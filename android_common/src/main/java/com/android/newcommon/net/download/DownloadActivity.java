package com.android.newcommon.net.download;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.common.R;
import com.android.newcommon.utils.FileUtils;
import com.android.newcommon.net.ZNetwork;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.NonNull;


public class DownloadActivity extends RxAppCompatActivity {

    private Button btnDownload;
    private ProgressBar progressBar;
    private TextView tvInfo;
    private DownloadInfo mDownloadInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        tvInfo = findViewById(R.id.tv_info);
        btnDownload = findViewById(R.id.btn_download);
        progressBar = findViewById(R.id.progress_bar);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                download();
            }
        });

        findViewById(R.id.btn_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDownloadInfo != null) {
                    ZNetwork.removeDownloadTask(mDownloadInfo);
                }
            }
        });
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
        }
    }

    private void download() {
        String url = "http://1251661065.vod2.myqcloud.com/98deaa00vodgzp1251661065/431995b15285890784241773480/Yc9fOum6HFYA.mp3";
        if (mDownloadInfo == null) {
            mDownloadInfo = getDownloadInfo(url);
        }
        DownloadManager.getInstance().addDownloadTask(mDownloadInfo, new IDownloadCallback() {
            @Override
            public void onProgress(DownloadInfo info, long progress, long total, boolean done) {
                int currentProgress = (int) ((progress / (double) total) * 100);
                progressBar.setProgress(currentProgress);
                tvInfo.setText("progress:" + progress + " total:" + total + " done:" + done
                        + "\n current progress:" + currentProgress);
                Log.i("download", "currentProgress:" + currentProgress + "progress =" + progress + ",total =" + total);
            }

            @Override
            public void onSuccess(DownloadInfo info, String result) {
                tvInfo.setText(result);
                Log.w("download", "onSuccess:" + result);
            }

            @Override
            public void onFailed(DownloadInfo info, String errorMsg) {
                tvInfo.setText(errorMsg);
                Log.w("download", "errorMsg:" + errorMsg);
            }
        });
    }

    public static String getFileTypeFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String[] names = url.split("\\.");
        if (names.length > 1) {
            return names[names.length - 1];
        } else {
            return url;
        }
    }

    public static DownloadInfo getDownloadInfo(String downloadUrl) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.url = downloadUrl;
        downloadInfo.fileName = System.currentTimeMillis() + "." + getFileTypeFromUrl(downloadUrl);
        downloadInfo.fileSavePath = FileUtils.getInstance().getVideoFile().getAbsolutePath();
        downloadInfo.setBreakPointEnable(true);
        downloadInfo.immediately = true;
        return downloadInfo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
