package com.basemodule.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileUtils.getInstance().makeFilePath("www");
 * Log.w("wangwei", "1----" + FileUtils.getInstance().getFileOrDirSize(FileUtils.getInstance().getImageFile()));
 * Log.w("wangwei", "----" + FileUtils.getInstance().getImageFile().getAbsolutePath());
 * Log.w("wangwei", "----" + FileUtils.getInstance().getVideoFile().getAbsolutePath());
 * Log.w("wangwei", "----" + FileUtils.getInstance().getCacheSize(FileUtils.getInstance().getImageFile().getAbsolutePath()));
 */
public class FileUtils {

    public static final String ZAFOLDER = "/wangWeiZa";//根目录
    public static final String DEFAULT_PHOTO_DIR_NAME = "photo";
    public static final String DEFAULT_VIDEO_DIR_NAME = "video";

    private static FileUtils instance = null;


    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }


    /**
     * app文件根目录
     */
    public File getRootFolder() {
        File file = new File(Environment.getExternalStorageDirectory(), ZAFOLDER);
        if (file.exists()) {
            if (!file.isDirectory()) {
                file.delete();
                file.mkdirs();
            }
        } else {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件夹 存放在根目录
     * /storage/emulated/0/-->wangWeiZa/
     */
    public File createFolder(String folderName) {
        if (isSDCardAvailable()) {
            File dir = new File(getRootFolder(), "/" + folderName);
            if (!dir.exists()) {
                dir.mkdirs();
            } else if (!dir.isDirectory()) {
                dir.delete();
                dir.mkdirs();
            }
            return dir;
        } else {
            return null;
        }
    }

    /**
     * 获取图片文件夹
     */
    public File getImageFile() {
        File file = new File(getRootFolder(), DEFAULT_PHOTO_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;

    }

    /**
     * 获取视频文件夹
     */
    public File getVideoFile() {
        File file = new File(getRootFolder(), DEFAULT_VIDEO_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;

    }


    // 生成文件，并写入内容
    public File makeFilePath(String fileName) {
        String strContent = "我是写入的内容";
        File file = null;
        try {
            fileName = fileName + ".txt";
            file = new File(getVideoFile(), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 获得当前大小 M
     */
    public float getCacheSize(String CACHE_PATH) {
        // TODO:设置数据显示
        float size = getFileOrDirSize(new File(CACHE_PATH));
        float size_show = (float) (Math.round(size / 1024.0f / 1024 * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
        if (size_show == 0) size_show = size == 0 ? 0 : 0.01f;
        return size_show;
    }


    /**
     * 获取文件或文件夹大小
     *
     * @param fileOrDir 文件或文件夹对象
     * @return 字节数
     */
    public long getFileOrDirSize(File fileOrDir) {
        if (fileOrDir == null || !fileOrDir.exists()) {
            return 0L;
        }

        if (fileOrDir.isDirectory()) {
            File[] children = fileOrDir.listFiles();
            long length = 0L;
            for (File child : children) {
                length += getFileOrDirSize(child);
            }
            return length;
        } else {
            return getFileSize(fileOrDir);
        }
    }

    /**
     * 获取文件大小
     *
     * @param file 文件对象
     * @return 字节数
     */
    private long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0L;
        }
        return file.length();
    }


    /**
     * 删除文件或目录
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            for (File child : children) {
                deleteFile(child);// 递规的方式删除文件夹
            }
            file.delete();// 删除目录本身
        } else {
            file.delete();
        }
    }


    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (file.isDirectory()) {// 如果是目录，删除
                        file.delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("TAG", "清理成功！");
            // tv_cache_size.setText(getCacheSize() + "M   ");
        }
    }


    /**
     * 复制 asset 文件到 SDCard
     *
     * @param context
     * @param asset   asseet文件名称
     * @param sdPath
     * @return
     */
    public static File copyAsset(Context context, String asset, String sdPath) {
        return copyAsset(context, asset, sdPath, false);
    }

    /**
     * 复制 asset 文件到 SDCard
     *
     * @param context
     * @param asset    asset 文件，全路径
     * @param sdPath   目标文件夹
     * @param onlyFile 只复制文件，不包含路径
     * @return
     */
    public static File copyAsset(Context context, String asset, String sdPath, boolean onlyFile) {
        if (TextUtils.isEmpty(asset)) {
            return null;
        }
        InputStream is = null;
        OutputStream os = null;
        File destFile = new File(sdPath, onlyFile ? getName(asset) : asset);
        if (destFile.exists()) {
            destFile.delete();
        }
        try {
            destFile.getParentFile().mkdirs();
            is = context.getAssets().open(asset);
            os = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return destFile;
    }

    /**
     * 获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        URI uri = URI.create(filePath);
        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        return new File(path).getName();
    }

    /**
     * 从Uri获取文件路径
     *
     * @param context Context
     * @param uri     Uri
     * @return 文件路径
     */
    public static String getPathFromUri(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String path = null;
        if (scheme == null)
            path = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{
                    MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        path = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return path;
    }


    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) { // api >= 19
            return getRealPathFromUriAboveApi19(context, uri);
        } else { // api < 19
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    /**
     * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    /**
     * 适配api19及以上,根据uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    /**
     * 把字节数组保存为一个文件
     *
     * @param b
     * @param outputFilePath
     * @return
     */
    public static File saveFileFromBytes(byte[] b, String outputFilePath) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFilePath);
            stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 文件重命名
     *
     * @param oldPath
     * @param newPath
     */
    public static boolean renameFile(String oldPath, String newPath) {
        if (TextUtils.equals(oldPath, newPath)) {
            return true;
        }

        File oldFile = new File(oldPath);
        File newFile = new File(newPath);

        if (!oldFile.exists()) {
            return false;
        }
        if (newFile.exists()) {
            newFile.delete();
        }

        return oldFile.renameTo(newFile);
    }

    /**
     * 获取 SD 卡可用空间
     *
     * @return -1:SD卡不可用
     */
    public static long getUsableSpace() {
        if (!isSDCardAvailable()) {
            return -1;
        }
        File sdcardFile = Environment.getExternalStorageDirectory();
        return sdcardFile.getUsableSpace();
    }

    /**
     * 判断手机SDCard是否已安装并可读写
     *
     * @return
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 计算图片文件的 分辨率
     */
    public static int[] computeSize(File srcImg) {
        int[] size = new int[2];


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeFile(srcImg.getAbsolutePath(), options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;

        return size;
    }


    /**
     * 保存图片文件到图库
     */
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片

        String fileName = createPhotoFileName();
        File file = new File(getImageFile(), fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            // CustomToast.toast(context,"已成功保存到相册中");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
                .parse("file://" + getImageFile().toString())));
    }

    /**
     * 创建图片不同的文件名
     **/
    public static String createPhotoFileName() {
        String fileName = "";
        Date date = new Date(System.currentTimeMillis());  //系统当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        fileName = dateFormat.format(date) + ".jpg";
        return fileName;
    }


}
