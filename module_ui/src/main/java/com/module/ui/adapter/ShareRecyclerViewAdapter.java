package com.module.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.module.ui.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 使用BottomSheetDialog实现简单分享功能
 * 描述：带有分享功能的 RecyclerView的适配器
 * @author luzhaowei
 * @email 2497727771@qq.com
 * @time 2017/8/17 16:38
 */
public class ShareRecyclerViewAdapter extends RecyclerView.Adapter<ShareRecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private BottomSheetDialog dialog;
    private List<AppBean> list;

    public ShareRecyclerViewAdapter(Activity activity, BottomSheetDialog dialog) {
        this.activity = activity;
        this.dialog = dialog;
        list = getShareAppList();
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Log.e("11111", list.get(i).toString());
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.item_recyclerview_layout_share, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.appTextView.setText(list.get(position).appName);
        holder.iconImageView.setImageDrawable(list.get(position).icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                AppBean appBean = list.get(position);
                shareIntent.setComponent(new ComponentName(appBean.pkgName, appBean.appLauncherClassName));
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "分享内容");
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(shareIntent);
                if (dialog != null) {
                    dialog.dismiss();
                }

//                    Intent intent2 = new Intent(Intent.ACTION_SEND);
//                    Uri uri = Uri.fromFile(new File("xxx.jpg"));
//                    intent2.setComponent(new ComponentName(appInfo.pkgName, appInfo.appLauncherClassName));
//                    intent2.putExtra(Intent.EXTRA_STREAM, uri);
//                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent2.setType("image/*");
//                    startActivity(Intent.createChooser(intent2, "share"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    @SuppressLint("WrongConstant")
    public List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
//      intent.setType("*/*");
        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return mApps;
    }


    private List<AppBean> getShareAppList() {
        List<AppBean> shareAppInfos = new ArrayList<AppBean>();
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> resolveInfos = getShareApps(activity);
        if (null == resolveInfos) {
            return null;
        } else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                AppBean appBean = new AppBean();
                appBean.pkgName = (resolveInfo.activityInfo.packageName);
//              showLog_I(TAG, "pkg>" + resolveInfo.activityInfo.packageName + ";name>" + resolveInfo.activityInfo.name);
                appBean.appLauncherClassName = (resolveInfo.activityInfo.name);
                appBean.appName = (resolveInfo.loadLabel(packageManager).toString());
                appBean.icon = (resolveInfo.loadIcon(packageManager));
                shareAppInfos.add(appBean);
            }
        }
        return shareAppInfos;
    }





    //////////////////////////////////////////////////
    //  RecyclerView 适配器的 ViewHolder
    /////////////////////////////////////////////////
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconImageView;
        public TextView appTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImageView = (ImageView) itemView.findViewById(R.id.app_icon_iv);
            appTextView = (TextView) itemView.findViewById(R.id.app_tv);
        }
    }

    /**
     * 封装的实体类
     */
    class AppBean {
        public Drawable icon;
        public String appName;
        public String pkgName;
        public String appLauncherClassName;
    }
}
