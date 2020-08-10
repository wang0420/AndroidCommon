package com.android.newcommon.utils.anr;

import android.content.Context;
import android.util.Log;

import com.android.newcommon.utils.FileUtils;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wangwei
 * @date 2020/7/23.
 */
public class MyBlockCanaryContext extends BlockCanaryContext {
    @Override
    public int provideBlockThreshold() {
        return 500; //配置block阈值，在此持续时间内的dispatch被视为BLOCK。您可以根据设备的性能进行设置。单位为毫秒。
    }

    @Override
    public void onBlock(Context context, BlockInfo blockInfo) {
        //Block拦截器，开发者可以提供自己的动作
        //Toast.makeText(context, "UI阻塞了，耗时：" + blockInfo.timeCost, Toast.LENGTH_SHORT).show();
        Log.i("bqt", "【UI阻塞了】耗时：" + blockInfo.timeCost + "\n" + blockInfo.toString());
    }

    @Override
    public String providePath() {
        //日志文件保存地址，每监测到一次卡顿都将在SD卡上保存一个单独的日志文件
        return FileUtils.getInstance().getAnrFile().getAbsolutePath();
    }

    @Override
    public boolean displayNotification() {
        return true; //设为true时，当监测到卡顿后会在通知栏提醒
    }

    @Override
    public boolean stopWhenDebugging() {
        return false; //是否在debug模式是关闭，如果关闭则返回true
    }

    @Override
    public int provideDumpInterval() {
        //线程 stack dump 间隔，当block发生时使用，BlockCanary将根据当前采样周期在主线程堆栈上dump。
        return provideBlockThreshold(); //因为Looper的实现机制，实际dump间隔会比这里指定的长，特别是当cpu比较繁忙时。
    }

    @Override
    public int provideMonitorDuration() {
        return -1; //配置Monitor持续的时间，过了这个时间以后BlockCanary就会停止，单位是小时，-1表示不会停止
    }

    @Override
    public boolean zip(File[] src, File dest) {
        return false; //参数为压缩前的文件列表和压缩后的文件，返回 true 代表压缩成功
    }

    @Override
    public void upload(File zippedFile) {
        throw new UnsupportedOperationException(); //用以将压缩后的日志文件上传到服务器，zip返回true才会调用。
    }

    @Override
    public List<String> concernPackages() {
        return null; //开发者所关心的包，默认情况下使用进程名称，如果只关心 package with process name，则返回null。
    }

    @Override
    public boolean filterNonConcernStack() {
        return false; //过滤掉那些没有任何包存在于 concernPackages() 方法所指定的包中的 stack。如果开启过滤返回true。
    }

    @Override
    public List<String> provideWhiteList() {
        LinkedList<String> whiteList = new LinkedList<>();
        whiteList.add("org.chromium");
        return whiteList; //提供白名单，白名单中的条目不会显示在ui列表中。如果您不需要白名单过滤器，则返回null。
        //注意，经过测试发现此库有一个bug，如果这里返回null，当点击通知栏或点击桌面上的"Blocks"时，应用就会崩溃！
    }

    @Override
    public boolean deleteFilesInWhiteList() {
        return true; //与白名单一起使用，是否删除那些其stack存在于白名单中的文件。如果删除则返回true。
    }

    @Override
    public String provideQualifier() {
        return "unknown"; //指定限定符，例如version + flavor
    }

    @Override
    public String provideUid() {
        return "uid";
    }

    @Override
    public String provideNetworkType() {
        return "unknown"; //2G, 3G, 4G, wifi, etc.
    }
}