package com.android.newcommon.net.download;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.common.R;


class DownloadTaskAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return DownloadManager.getInstance().getDownloadInfoCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0L;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_queue, parent, false);
        }
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfoByIndex(position);
        show(convertView, downloadInfo);
        return convertView;
    }

    private void show(View convertView, DownloadInfo downloadInfo) {
        TextView tvUrl = convertView.findViewById(R.id.tv_url);
        TextView tvSavePath = convertView.findViewById(R.id.tv_save_path);
        TextView tvProgress = convertView.findViewById(R.id.tv_progress);
        ProgressBar progressBar = convertView.findViewById(R.id.progress_bar);

        if (downloadInfo == null) {
            return;
        }

        tvUrl.setText(downloadInfo.url);
        tvSavePath.setText(downloadInfo.fileSavePath + downloadInfo.fileName);

        long total = 0L;
        long current = 0L;
        DownloadHelper downloadHelper = DownloadManager.getInstance().findDownloadHelper(downloadInfo.url);
        if (downloadHelper != null) {
            total = downloadHelper.getTotalProgress();
            current = downloadHelper.getCurrentProgress();
        }

        int percent = 0;
        if (total > 0L && current > 0L) {
            if (current >= total) {
                percent = 100;
            } else {
                percent = (int) (((double) current / (double) total) * 100);
            }
        }
        String progressDesc;
        switch (downloadInfo.state) {
            case DownloadInfo.STATE_WAITING:
                progressDesc = "STATE_WAITING";
                break;
            case DownloadInfo.STATE_DOWNLOADING:
                progressDesc = "STATE_DOWNLOADING";
                break;
            case DownloadInfo.STATE_PAUSE:
                progressDesc = "STATE_PAUSE";
                break;
            case DownloadInfo.STATE_FINISH:
                progressDesc = "STATE_FINISH";
                break;
            case DownloadInfo.STATE_ERROR:
            default:
                progressDesc = "STATE_ERROR";
                break;
        }
        tvProgress.setText(current + "/" + total + ", " + percent + "%, " + progressDesc);
        progressBar.setProgress(percent);
    }
}