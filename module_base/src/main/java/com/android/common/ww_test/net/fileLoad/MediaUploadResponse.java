package com.android.common.ww_test.net.fileLoad;

import com.android.common.ww_test.net.BaseEntity;

/**
 * @author hechunshan
 * @date 2017/6/26
 */
public class MediaUploadResponse extends BaseEntity {

    public int height;
    public String originFileName = "";
    public int size;
    public String uploadFileName = "";
    public String url = "";
    public int width;

    @Override
    public String[] uniqueKey() {
        return new String[0];
    }
}
