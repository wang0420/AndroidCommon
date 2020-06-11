package com.basemodule.za_test.permission.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.basemodule.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import io.reactivex.functions.Action;

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/21 17:30
 * ModifyTime: 17:30
 * Description:
 */
public class ZAPermissionUI {
    private Context mContext;
    private Dialog mDialog;
    PermissionTipView permissionTipView;
    String mTitle;
    List<PermissionBean> mPermissionItems;
    IPermissionUIAction mIPermissionUIAction;
    int mLayoutId = R.layout.view_stub_permission_tip;

    int mAnimStyleId = -1;
    boolean isTouchOutsideCancled;
    private PermisionStatusAction mGrantedAction;

    Object mHost;

    public ZAPermissionUI(@NonNull FragmentActivity activity) {
        mHost = activity;
        mContext = activity;
        mDialog = new Dialog(mContext, R.style.GeneralDialog);
        mDialog.setOnDismissListener(dialog -> {
            if (mDialog != null) {
                mDialog = null;
            }
        });
    }

    public ZAPermissionUI(@NonNull Fragment fragment) {
        this.mHost = fragment;
        if (fragment.getContext() != null) {
            mContext = fragment.getContext();
            mDialog = new Dialog(fragment.getContext(), R.style.GeneralDialog);
            mDialog.setOnDismissListener(dialog -> {
                if (mDialog != null) {
                    mDialog = null;
                }
            });
        }
    }

    public ZAPermissionUI title(String title) {
        this.mTitle = title;
        return this;
    }

    public ZAPermissionUI permissions(List<PermissionBean> permissions) {
        this.mPermissionItems = permissions;
        return this;
    }

    public ZAPermissionUI layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }



    public ZAPermissionUI animation(int animStyleId) {
        this.mAnimStyleId = animStyleId;
        return this;
    }

    public ZAPermissionUI touchOutsideCancled(boolean cancel) {
        this.isTouchOutsideCancled = cancel;
        return this;
    }

    public ZAPermissionUI uiAction(IPermissionUIAction action) {
        this.mIPermissionUIAction = action;
        return this;
    }

    public ZAPermissionUI onGranted(PermisionStatusAction action) {
        mGrantedAction = action;
        return this;
    }



    public void release() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
            permissionTipView = null;
        }
    }


    public void build() {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (mHost != null && mContext != null) {
            permissionTipView = new PermissionTipView(mContext) {
                @Override
                public void onCloseClick() {
                    if (mIPermissionUIAction != null) {
                        mIPermissionUIAction.onCloseClick(ZAPermissionUI.this);
                        release();
                    } else {
                        release();
                    }
                }

                @Override
                public int getLayoutId() {
                    return mLayoutId;
                }

                @Override
                public void onSubmitClick() {
                    List<String> permissions = new ArrayList<>();
                    for (PermissionBean item : mPermissionItems) {
                        permissions.add(item.type);
                    }
                    Toast.makeText(mContext,"申请",Toast.LENGTH_SHORT).show();
                    if (mGrantedAction != null) {

                    }
           /*         RxPermissionUtil.requestPermission(mHost, permissions.toArray(new String[]{}),
                            new RxPermissionCallBack() {
                                @Override
                                public void onGranted() {
                                    if (mGrantedAction != null) {
                                        mGrantedAction.onAction(permissions);
                                    }
                                }

                                @Override
                                public void onDenied(List<String> deniedPermissions) {
                                    if (mDeniedAction != null) {
                                        mDeniedAction.onAction(deniedPermissions);
                                    }
                                }
                            });*/
                    if (mIPermissionUIAction != null) {
                        mIPermissionUIAction.onSubmitClick(ZAPermissionUI.this, permissions.toArray(new String[]{}));
                        release();
                    } else {
                        release();
                    }

                }
            };
        } else {
            Log.i("ZAPermissionUI", "mHost == null");
        }
        permissionTipView.updatePermissionList(mPermissionItems);
        permissionTipView.updateTitle(mTitle);

        Window window = mDialog.getWindow();
        mDialog.setContentView(permissionTipView);
        if (mAnimStyleId != -1) {
            window.setWindowAnimations(mAnimStyleId);
        }
        mDialog.setCanceledOnTouchOutside(isTouchOutsideCancled);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

}
