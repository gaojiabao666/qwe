package com.xsqwe.admin.entity;

import lombok.Data;

/**
 * @Description
 * @Date 2020/2/25 14:36
 * @Created by zwd
 */
@Data
public class ZipFileArchive {
    /**
     * contain file name
     */
    private String filePath;

    /**
     * replace filePath's file name with name in zip file
     */
    private String name;
}
