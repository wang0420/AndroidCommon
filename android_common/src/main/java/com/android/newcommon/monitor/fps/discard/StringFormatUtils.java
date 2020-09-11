package com.android.newcommon.monitor.fps.discard;


import com.android.newcommon.utils.CollectionUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yanquan yanquan.huang@zhenai.com
 * @time 2017/9/6 14:21
 */
public class StringFormatUtils {
    private static final String COMMA = ",";
    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    public static String number2FormatStr(int number) {
        String formatStr;
        if (number < 999) {
            formatStr = String.valueOf(number);
        } else if (number >= 999 && number < 10000) {
            formatStr = "999+";
        } else {
            float f = number / 10000.0f;
            formatStr = String.format(Locale.getDefault(), "%.1f", f) + "w";
        }
        return formatStr;
    }

    public static String videoPlayTimesFormat(int times) {
        String formatStr;
        if (times >= 10000) {
            float f = times / 10000.0f;
            formatStr = String.format(Locale.getDefault(), "%.1f", f) + "w";
        } else {
            formatStr = String.valueOf(times);
        }
        return formatStr;
    }

    public static String numberComment2FormatStr(long number) {
        return numberPraise2FormatStr(number);
    }

    public static String numberPraise2FormatStr(long number) {
        String formatStr;
        if (number < 0) {
            formatStr = "0";
        } else if (number <= 999) {
            formatStr = String.valueOf(number);
        } else {
            formatStr = "999+";
        }
        return formatStr;
    }

    public static String numberMessageCount2FormatStr(long number) {
        String formatStr;
        if (number < 0) {
            formatStr = "0";
        } else if (number < 99) {
            formatStr = String.valueOf(number);
        } else {
            formatStr = "99+";
        }
        return formatStr;
    }

    /**
     * id 列表转 String
     */
    public static String convertIdList2StringParams(List<Long> list) {
        StringBuilder ids = new StringBuilder();
        if (!CollectionUtils.isEmpty(list)) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ids.append(String.valueOf(list.get(i)));
                if (i < size - 1) {
                    ids.append(COMMA);
                }
            }
        }
        return ids.toString();
    }

    /**
     * id 列表转 String
     */
    public static String convertIdListString2StringParams(List<String> list) {
        StringBuilder ids = new StringBuilder();
        if (!CollectionUtils.isEmpty(list)) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ids.append(list.get(i));
                if (i < size - 1) {
                    ids.append(COMMA);
                }
            }
        }
        return ids.toString();
    }

    /**
     * 获取一个字符串中是否包含数字和座机号码(4001-520-520)以及区号号码（0755-1234567），并且返回包含的数字
     *
     * @param content         原始string内容
     * @param numberMinLength 需要查找的数字长度
     * @return
     */
    public static HashMap<Integer, String> getNumberMapOnStringWithStartIndex(String content, int numberMinLength) {
        String patternString = "\\d{" + numberMinLength + ",}|\\d{3,4}-\\d{7,8}|\\d{3,}-\\d{3,}-\\d{3,}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher isNum = pattern.matcher(content);
        HashMap<Integer, String> patternMapWithStartIndex = new HashMap<>();
        while (isNum.find()) {
            int startIndex = isNum.start();
            String numString = isNum.group();
            patternMapWithStartIndex.put(startIndex, numString);
        }
        return patternMapWithStartIndex;
    }

    /**
     * 将文件大小转换为特定单位字符串
     *
     * @param value
     * @return
     */
    public static String bytesToHuman(final long value) {
        final long[] dividers = new long[]{T, G, M, K, 1};
        final String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
        if (value < 1)
            return 0 + " " + units[units.length - 1];
        String result = null;
        for (int i = 0; i < dividers.length; i++) {
            final long divider = dividers[i];
            if (value >= divider) {
                result = format(value, divider, units[i]);
                break;
            }
        }
        return result;
    }

    private static String format(final long value,
                                 final long divider,
                                 final String unit) {
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#.##").format(result) + " " + unit;
    }

    public static long string2Long(String str) {
        long num;
        try {
            num = Long.parseLong(str);
        } catch (Exception ex) {
            num = 0;
        }
        return num;
    }
}
