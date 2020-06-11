package com.basemodule.ww_test.net.fileLoad.upload.entity;

import java.io.File;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/6/11.
 */
public class FileAndParamName {
    public File file;
    public String paramName;

    public FileAndParamName(File file, String paramName) {
        this.file = file;
        this.paramName = paramName;
    }
}
