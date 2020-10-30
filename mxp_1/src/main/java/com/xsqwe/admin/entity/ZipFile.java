package com.xsqwe.admin.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Date 2020/2/25 14:16
 * @Created by zwd
 */
@Data
public class ZipFile {

    private String dirName;

    List<ZipFileArchive> zipFileArchives;
}
