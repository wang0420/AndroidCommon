package com.android.common.router;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.Consts;
import com.alibaba.android.arouter.utils.PackageUtils;
import com.android.common.R;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import androidx.fragment.app.Fragment;

/**
 * 路由管理类
 *
 * @author rade.chan
 * @date 2018/8/16.
 */

public class RouterManager {


    private static HashMap<String, RouteMeta> routeMaps;

    public static void gotoActivity(final Context context, String path) {
        ARouter.getInstance().build(path).withTransition(R.anim.slide_right_in, R.anim.slide_left_out).navigation(context, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {

            }

            @Override
            public void onLost(Postcard postcard) {
                super.onLost(postcard);
               /* if (BaseApplication.getInstance().isDebug()) {
                    ToastUtils.toast(context, postcard.getPath() + " is not found!!!");
                }*/
            }
        });
    }

    public static void gotoActivityAndFinish(final Activity activity, String path) {
        gotoActivityAndFinish(activity, path, true);

    }

    public static void gotoActivityAndFinish(final Activity activity, String path, boolean isAnim) {
        Postcard postcard = ARouter.getInstance().build(path);
        if (isAnim) {
            postcard.withTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        postcard.navigation(activity, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                activity.finish();
            }
        });

    }

    public static Postcard getARouter(String path) {
        return ARouter.getInstance().build(path).withTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public static Class<?> getClass(String path) {
        HashMap<String, RouteMeta> maps = getRoutes();
        if (maps != null) {
            RouteMeta routeMeta = maps.get(path);
            if (routeMeta == null) {    //为空的时候，尝试装载
                LogisticsCenter.completion(ARouter.getInstance().build(path));
                routeMeta = maps.get(path);
            }
            if (routeMeta != null) {
                return routeMeta.getDestination();
            }
        }
        return ARouter.getInstance().build(path).getDestination();
    }

    private static HashMap<String, RouteMeta> getRoutes() {
        try {
            if (routeMaps == null) {
                Class clazz = Class.forName("com.alibaba.android.arouter.core.Warehouse");
                Field routes = clazz.getDeclaredField("routes");
                routes.setAccessible(true);
                routeMaps = (HashMap<String, RouteMeta>) routes.get(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routeMaps;
    }

    /**
     * 没有转场动画
     *
     * @param path
     * @return
     */
    public static Postcard getARouterWithFadeAnim(String path) {
        return ARouter.getInstance().build(path).withTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public static void gotoActivity(Uri uri) {
        ARouter.getInstance()
                .build(uri)
                .withTransition(R.anim.slide_right_in, R.anim.slide_left_out)
                .navigation();
    }

//    public static void gotoActivity(Context context, Uri uri) {
//        ARouter.getInstance()
//                .build(uri)
//                .withTransition(R.anim.slide_right_in, R.anim.slide_left_out)
//                .navigation(context);
//    }


    public static void inject(Object object) {
        ARouter.getInstance().inject(object);
    }

    public static Fragment getFragment(String path) {
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }


    /**
     * 修复arouter缓存被删除的bug
     *
     * @param context
     */
    public static void fixARouterCacheError(Context context) {
        try {
            if (!PackageUtils.isNewVersion(context)) {
                SharedPreferences sp = context.getSharedPreferences(Consts.AROUTER_SP_CACHE_KEY, Context.MODE_PRIVATE);
                Set<String> routerMap = new HashSet<>(sp.getStringSet(Consts.AROUTER_SP_KEY_MAP, new HashSet<String>()));
                if (routerMap.isEmpty()) {
                    sp.edit().putString(Consts.LAST_VERSION_NAME, null).putInt(Consts.LAST_VERSION_CODE, -1).apply();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static <T> T getProvider(String providerPath) {
        return (T) ARouter.getInstance().build(providerPath).navigation();
    }
}
