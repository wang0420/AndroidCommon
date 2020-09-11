package com.android.newcommon.monitor.fps;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;

import com.android.newcommon.floatview.FloatPageManager;
import com.android.newcommon.monitor.LogHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangwei
 * @date 2020/9/11.
 */
public enum AppActiveMatrixDelegate {

    INSTANCE;

    private Controller controller = new Controller();
    private String visibleScene = "default";

    private static final String TAG = "AppActiveMatrixDelegate";


    public void init(Application application) {
        application.registerComponentCallbacks(controller);
        application.registerActivityLifecycleCallbacks(controller);
    }


    private void updateScene(Activity activity) {
        visibleScene = activity.getClass().getName();
    }

    public String getVisibleScene() {
        return visibleScene;
    }

    public static String getTopActivityName() {
        long start = System.currentTimeMillis();
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);

            Map<Object, Object> activities;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                activities = (HashMap<Object, Object>) activitiesField.get(activityThread);
            } else {
                activities = (ArrayMap<Object, Object>) activitiesField.get(activityThread);
            }
            if (activities.size() < 1) {
                return null;
            }
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity.getClass().getName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long cost = System.currentTimeMillis() - start;
            Log.w("TAG--", "[getTopActivityName] Cost:" + cost);
        }
        return null;
    }

    int startedActivityCounts;

    private final class Controller implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

        @Override
        public void onActivityStarted(Activity activity) {
            LogHelper.e(TAG, "" + activity.getClass().getName());
            if (startedActivityCounts == 0) {
                FloatPageManager.getInstance().notifyForeground();
            }
            startedActivityCounts++;
            updateScene(activity);
        }


        @Override
        public void onActivityStopped(Activity activity) {
            startedActivityCounts--;
            if (startedActivityCounts == 0) {
                FloatPageManager.getInstance().notifyBackground();
            }
            if (getTopActivityName() == null) {

            }
        }


        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onLowMemory() {

        }

        @Override
        public void onTrimMemory(int level) {
            Log.e("TAG--", "[onTrimMemory] level:%s" + level);
            if (level == TRIM_MEMORY_UI_HIDDEN) { // fallback

            }
        }
    }


}
