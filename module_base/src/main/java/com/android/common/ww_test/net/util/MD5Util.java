package com.android.common.ww_test.net.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * on 2017/5/17.
 */

public class MD5Util {
    /**
     * 获取md5
     *
     * @param str 字符串
     * @return md5值
     */
    public static String getMD5(String str) {
        return getMD5(str, 16);
    }

    /**
     * 获取md5
     *
     * @param str   字符串
     * @param radix 进制数（默认16进制）
     * @return md5值
     */
    public static String getMD5(String str, int radix) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5 = new BigInteger(1, messageDigest.digest()).toString(radix <= 0 ? 16 : radix);
            //以0开头时，获取到的md5的位数会少，只修复16进制表示法
            int size = md5.length();
            if (radix == 16 && size < 32) {
                for (int i = 0; i < 32 - size; ++i) {
                    md5 = "0" + md5;
                }
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取hmacMD5加密
     *
     * @param key       密钥
     * @param data      数据
     * @param algorithm 算法值固定为"HmacMD5"
     * @return
     */
    public static byte[] getHmacMD5(byte[] key, byte[] data, String algorithm) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
        try {
            Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
            mac.init(secretKeySpec);
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5("1541211977dg3d#%7fsz"));
        System.out.println(MD5Util.getMD5("1541211977dg3d#%7fsz", 32));
    }

}
