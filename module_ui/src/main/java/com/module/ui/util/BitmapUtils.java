package com.module.ui.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/6/16.
 */
public class BitmapUtils {
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String saveBitmap(File file, Bitmap bmp) {
        return saveBitmap(file, bmp, Bitmap.CompressFormat.JPEG, 100);
    }

    public static String saveBitmap(File file, Bitmap bmp, Bitmap.CompressFormat format, int quality) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(format, quality, fos);

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap resize(Bitmap src, int targetWidth, int targetHeight) {
        final int srcWidth = src.getWidth();
        final int srcHeight = src.getHeight();
        final Matrix matrix = new Matrix();
        matrix.postScale((float) targetWidth / (float) srcWidth,
                (float) targetHeight / (float) srcHeight);
        return Bitmap.createBitmap(src, 0, 0, srcWidth, srcHeight, matrix, true);
    }

    public static Bitmap resizeRatio(Bitmap src, int targetWidth, int targetHeight) {
        final int srcWidth = src.getWidth();
        final int srcHeight = src.getHeight();

        if (srcWidth / (float) srcHeight > targetWidth / (float) targetHeight) {
            targetWidth = srcWidth * targetHeight / srcHeight;
        } else {
            targetHeight = srcHeight * targetWidth / srcWidth;
        }

        return resize(src, targetWidth, targetHeight);
    }

    public static Bitmap grayBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }

    public static void recycle(List<Bitmap> bitmaps) {
        if (bitmaps != null && !bitmaps.isEmpty()) {
            for (Bitmap bitmap : bitmaps) {
                recycle(bitmap);
            }
            bitmaps.clear();
        }
    }

    public static void recycle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

}