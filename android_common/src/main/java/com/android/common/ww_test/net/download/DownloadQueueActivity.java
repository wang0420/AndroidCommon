package com.android.common.ww_test.net.download;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.common.R;
import com.android.common.utils.FileUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.NonNull;


public class DownloadQueueActivity extends RxAppCompatActivity {
    private static final String ACTION_REFRESH_DOWNLOAD_LIST = "ACTION_REFRESH_DOWNLOAD_LIST";

    private Button btnAdd, btnModifyMaxParallel;
    private ListView listView;
    private DownloadTaskAdapter listAdapter;
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
    private String[] taskUrls = new String[]{
            "https://photo.zastatic.com/images/cms/live/imitation/20190613/29874818766653667.svga",
            "https://photo.zastatic.com/images/cms/live/imitation/20190620/30489982792121691.svga",
            "https://photo.zastatic.com/images/cms/live/imitation/20190621/30565922655239396.svga",
            "https://photo.zastatic.com/images/cms/live/imitation/20190627/31071362762436158.bundle",
            "https://photo.zastatic.com/images/cms/live/imitation/20190704/31702397867428773.svga",
            "https://photo.zastatic.com/images/cms/live/imitation/20190705/31790667451072740.bundle",
            "https://photo.zastatic.com/images/cms/live/imitation/20190418/21081370649562744.webp",
            "https://photo.zastatic.com/images/cms/live/imitation/20190819/1155369192989876.svga",
            "https://photo.zastatic.com/images/cms/live/imitation/20190822/1410750894192262.svga",
            "https://photo.zastatic.com/images/cms/live/imitation/20190822/1410750894192262.svga"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_queue);

        btnAdd = findViewById(R.id.btn_add_download_task);
        btnModifyMaxParallel = findViewById(R.id.btn_modify_max_parallel);
        listView = findViewById(R.id.list_view);

        listAdapter = new DownloadTaskAdapter();
        listView.setAdapter(listAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
//                download();
            }
        });
        int max = DownloadManager.getInstance().getMaxParallelCount();
        btnModifyMaxParallel.setText("修改并行数：" + max);
        btnModifyMaxParallel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int max = DownloadManager.getInstance().getMaxParallelCount() + 1;
                DownloadManager.getInstance().setMaxParallelCount(max);
                btnModifyMaxParallel.setText("修改并行数：" + max);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
            boolean allGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                }
            }
            if (allGranted) {
                download();
            } else {
                Toast.makeText(this, "请授权使用存储", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void download() {
        int next = listAdapter.getCount();
        if (next >= taskUrls.length) {
            return;
        }

//        String url = taskUrls[next];
        String url = "http://1251661065.vod2.myqcloud.com/98deaa00vodgzp1251661065/431995b15285890784241773480/Yc9fOum6HFYA.mp3?t=" + System.currentTimeMillis();
        DownloadInfo mDownloadInfo = getDownloadInfo(url);
        DownloadManager.getInstance().addDownloadTask(mDownloadInfo, new IDownloadCallback() {
            @Override
            public void onProgress(DownloadInfo info, long progress, long total, boolean done) {
                notifyListAdapterRefresh();
            }

            @Override
            public void onSuccess(DownloadInfo info, String result) {
                notifyListAdapterRefresh();
            }

            @Override
            public void onFailed(DownloadInfo info, String errorMsg) {
                notifyListAdapterRefresh();
            }
        });
    }

    private void notifyListAdapterRefresh() {
        Intent i = new Intent(ACTION_REFRESH_DOWNLOAD_LIST);
        listAdapter.notifyDataSetChanged();
       // LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_REFRESH_DOWNLOAD_LIST.equals(intent.getAction())) {
                listAdapter.notifyDataSetChanged();
            }
        }
    };

    public static DownloadInfo getDownloadInfo(String downloadUrl) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.url = downloadUrl;
        downloadInfo.fileName = System.currentTimeMillis() + "." + getFileTypeFromUrl(downloadUrl);
        downloadInfo.fileSavePath =  FileUtils.getInstance().getVideoFile().getAbsolutePath();
        downloadInfo.setBreakPointEnable(true);
        return downloadInfo;
    }
}

