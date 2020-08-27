package com.android.newcommon.monitor;

import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * App监控
 *
 * @author huliang
 * @date 2019/5/9
 */

public class CPUUtil {
    private static final String TAG = AppMonitor.class.getSimpleName();

    private static int interval = 30000; // 记录间隔时间


    public static void getProcessCpuRate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                        try {
                            float totalCpuTime1 = getTotalCpuTime();
                            float processCpuTime1 = getAppCpuTime();
                            Thread.sleep(interval);  //sleep一段时间
                            float totalCpuTime2 = getTotalCpuTime();
                            float processCpuTime2 = getAppCpuTime();

                            float cpuRate = 100 * (processCpuTime2 - processCpuTime1) / (totalCpuTime2 - totalCpuTime1);//百分比
                            Log.w("CPU","doFrame: getProcessCpuRate=" + cpuRate);

                            getCpuAvailabilityTest();
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }


            }
        }).start();
    }

    public static void getCpuRateByProcess() {
        java.lang.Process process = null;
        try {
            while (true) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    process = Runtime.getRuntime().exec("top -n 1 -d 0");
                } else {
                    process = Runtime.getRuntime().exec("top -n 1");
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                int cpuIndex = -1;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (TextUtils.isEmpty(line)) {
                        continue;
                    }
                    // 找到CPU指标的下表索引，不同的系统版本下标索引不同
                    int tempIndex = -1;
                    if (line.contains("CPU")) {
                        String[] titles = line.split("\\s+");
                        for (int i = 0; i < titles.length; i++) {
                            if (titles[i].contains("CPU")) {
                                tempIndex = i;
                            }
                        }
                    }
                    if (tempIndex != -1) {
                        cpuIndex = tempIndex;
                        continue;
                    }
                    if (line.startsWith(String.valueOf(Process.myPid()))) {
                        if (cpuIndex == -1) {
                            continue;
                        }
                        String[] param = line.split("\\s+");
                        if (param.length <= cpuIndex) {
                            continue;
                        }
                        String cpu = param[cpuIndex];
                        // 有些设备自带了%，需要去除
                        if (cpu.endsWith("%")) {
                            cpu = cpu.substring(0, cpu.lastIndexOf("%"));
                        }
                        try {
                            Log.w("CPU", "" + Double.parseDouble(cpu) / Runtime.getRuntime().availableProcessors());
                        } catch (NumberFormatException nfe) {
                            continue;
                        }
                    }
                }
                getCpuAvailabilityTest();
                Thread.sleep(interval);  //sleep一段时间
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }








    // 获取系统总CPU使用时间
    private static long getTotalCpuTime() throws Exception {
        String[] cpuInfos = null;
        long totalCpu = 1;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");

            totalCpu = Long.parseLong(cpuInfos[2])
                    + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                    + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                    + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        } catch (Exception ex) {
            throw ex;
        }

        return totalCpu;
    }

    // 获取应用占用的CPU时间
    private static long getAppCpuTime() {
        String[] cpuInfos = null;
        long appCpuTime = 1;
        try {
            int pid = Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
            appCpuTime = Long.parseLong(cpuInfos[13])
                    + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                    + Long.parseLong(cpuInfos[16]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return appCpuTime;
    }


    private static long string2Long(String str) {
        long num;
        try {
            num = Long.parseLong(str);
        } catch (Exception ex) {
            num = 0;
        }
        return num;
    }

    private static void getCpuTempPlanA() {
        float temp = 0f;
        BufferedReader br = null;
        FileReader fr = null;
        try {
            File dir = new File("/sys/class/thermal/");
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (Pattern.matches("thermal_zone[0-9]+", file.getName())) {
                        return true;
                    }
                    return false;
                }
            });

            final int SIZE = files.length;
            String line = "";
            String type = "";
            for (int i = 0; i < SIZE; i++) {
                fr = new FileReader("/sys/class/thermal/thermal_zone" + i + "/type");
                br = new BufferedReader(fr);
                line = br.readLine();
                if (line != null) {
                    type = line;
                }

                fr = new FileReader("/sys/class/thermal/thermal_zone" + i + "/temp");
                br = new BufferedReader(fr);
                line = br.readLine();
                if (line != null) {
                    // MTK CPU
                    if (type.contains("cpu")) {
                        long temperature = Long.parseLong(line);
                        if (temperature < 0) {
                            temp = 0f;
                        } else {
                            temp = temperature / 1000.0f;
                        }
                    } else if (type.contains("tsens_tz_sensor")) {
                        // Qualcomm CPU
                        long temperature = Long.parseLong(line);
                        if (temperature < 0) {
                            temp = 0f;
                        } else if (temperature > 100) {
                            temp = temperature / 10.0f;
                        } else {
                            temp = temperature;
                        }
                    }
                }
            }

            if (fr != null) {
                fr.close();
            }
            if (br != null) {
                br.close();
            }
            //  mMonitorCallback.cpuTemp(temp);
        } catch (Exception e) {

        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    }

    private static Disposable getCpuAvailabilityTest() {
        return getCpuTemperatureFinder()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CpuTemperatureResult>() {
                    @Override
                    public void accept(CpuTemperatureResult cpuTemperatureResult) throws Exception {

                    }
                });
    }

    private static final List<String> CPU_TEMP_FILE_PATHS = Arrays.asList(
            "/sys/devices/system/cpu/cpu0/cpufreq/cpu_temp",
            "/sys/devices/system/cpu/cpu0/cpufreq/FakeShmoo_cpu_temp",
            "/sys/class/thermal/thermal_zone0/temp",
            "/sys/class/i2c-adapter/i2c-4/4-004c/temperature",
            "/sys/devices/platform/tegra-i2c.3/i2c-4/4-004c/temperature",
            "/sys/devices/platform/omap/omap_temp_sensor.0/temperature",
            "/sys/devices/platform/tegra_tmon/temp1_input",
            "/sys/kernel/debug/tegra_thermal/temp_tj",
            "/sys/devices/platform/s5p-tmu/temperature",
            "/sys/class/thermal/thermal_zone1/temp",
            "/sys/class/hwmon/hwmon0/device/temp1_input",
            "/sys/devices/virtual/thermal/thermal_zone1/temp",
            "/sys/devices/virtual/thermal/thermal_zone0/temp",
            "/sys/class/thermal/thermal_zone3/temp",
            "/sys/class/thermal/thermal_zone4/temp",
            "/sys/class/hwmon/hwmonX/temp1_input",
            "/sys/devices/platform/s5p-tmu/curr_temp");

    private static final Maybe<CpuTemperatureResult> getCpuTemperatureFinder() {
        return Observable
                .fromIterable(CPU_TEMP_FILE_PATHS)
                .map(new Function<String, CpuTemperatureResult>() {
                    @Override
                    public CpuTemperatureResult apply(String path) {
                        Double temp = readOneLine(new File(path));
                        String validPath = "";
                        double currentTemp = 0.0D;
                        if (isTemperatureValid(temp)) {
                            validPath = path;
                            currentTemp = temp;
                        } else if (isTemperatureValid(temp / (double) 1000)) {
                            validPath = path;
                            currentTemp = temp / (double) 1000;
                        }
                        Log.e(TAG, "rx:" + String.valueOf(currentTemp));
                        return new CpuTemperatureResult(validPath, currentTemp);
                    }
                }).filter(new Predicate<CpuTemperatureResult>() {
                    @Override
                    public boolean test(CpuTemperatureResult cpuTemperatureResult) throws Exception {
                        return !TextUtils.isEmpty(cpuTemperatureResult.filePath)
                                && (cpuTemperatureResult.temp != 0);
                    }
                }).firstElement();
    }

    /**
     * Container for temperature value and path
     */
    private static class CpuTemperatureResult {
        String filePath;
        double temp;

        CpuTemperatureResult(String filePath, double temp) {
            this.filePath = filePath;
            this.temp = temp;
        }
    }

    private static double readOneLine(File file) {
        FileInputStream fileInputStream = null;
        String s = "";
        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            s = bufferedReader.readLine();
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double result = 0;
        try {
            result = Double.parseDouble(s);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

    private static boolean isTemperatureValid(double temp) {
        return temp >= -30.0D && temp <= 250.0D;
    }

}
