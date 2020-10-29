package com.xsqwe.admin.utils;

import com.xsqwe.utils.Tools;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

public class Compression {

    ZipArchiveOutputStream stream = null;
    File zipFile = null;

    public void zip(Map<String, String> files, String savePath) throws Exception {

        if (zipFile == null) {
            zipFile = new File(savePath);
        }
        if (!zipFile.getParentFile().exists()) {
            zipFile.getParentFile().mkdirs();
        }
        if (stream == null) {
            stream = new ZipArchiveOutputStream(zipFile);
        }
        for (Entry<String, String> entry : files.entrySet()) {

            File file = new File(entry.getKey());
            if (file.isDirectory()) {
                continue;
            }

            InputStream in = new FileInputStream(file);
            String value = entry.getValue();
            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, Tools.isBlank(value) ? file.getName() : value);
            // 添加一个条目
            stream.putArchiveEntry(zipArchiveEntry);
            IOUtils.copy(in, stream);
            // 结束
            stream.closeArchiveEntry();
            in.close();
        }
        finish();
    }

    private void finish() throws IOException {
        zipFile = null;
        stream.finish();
        stream.close();
        stream = null;
    }
}
