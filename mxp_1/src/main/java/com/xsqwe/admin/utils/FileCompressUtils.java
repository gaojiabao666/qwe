package com.xsqwe.admin.utils;

import com.xsqwe.admin.constants.EpCoreAdminConstants;
import com.xsqwe.admin.entity.ZipFile;
import com.xsqwe.admin.entity.ZipFileArchive;
import com.xsqwe.admin.enums.EpCoreAdminEnum;
import com.xsqwe.utils.Tools;
import com.xsqwe.web.ExceptionUtils;
import com.xsqwe.web.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class FileCompressUtils {
	
	ZipArchiveOutputStream stream = null;
	File zipFile = null;


	public void zip(List<ZipFileArchive> zipFileArchives, String savePath) throws CommonException {
		zipDir("",zipFileArchives,savePath);
	}
	
	public void zipDir(String dirName, List<ZipFileArchive> zipFileArchives, String savePath) throws CommonException{
		createFile(savePath);
		dirName = processDirName(dirName);
		
		addZipArchive(dirName, zipFileArchives);
		finish();
	}
	
	public void zipDirs(List<ZipFile> zipFiles, String savePath) throws CommonException{
		createFile(savePath);
		try {
			for (ZipFile zipFile : zipFiles) {
				String dirName = zipFile.getDirName();
				List<ZipFileArchive> zipFileArchives = zipFile.getZipFileArchives();
				dirName = processDirName(dirName);
				addZipArchive(dirName, zipFileArchives);
			}
		} catch (Exception e) {
			throw ExceptionUtils.create(EpCoreAdminEnum.CPMPRESS_FILE_FAIL,e);
		}finally {
			finish();
		}
	}
	void addZipArchive(String dirName, List<ZipFileArchive> zipFileArchives) throws CommonException{
		FileInputStream in = null;
		try {
			if (stream == null) {
				stream = new ZipArchiveOutputStream(zipFile);
			}
			for (ZipFileArchive zipFileArchive : zipFileArchives) {

				File file = new File(zipFileArchive.getFilePath());
				String name = zipFileArchive.getName();
				if(file.isDirectory()){
					continue;
				}
				// 添加一个条目
				ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(dirName+getName(file.getName(), name));
				stream.putArchiveEntry(zipArchiveEntry);
				in = new FileInputStream(file);
				IOUtils.copy(in, stream);
				stream.closeArchiveEntry();
				in.close();
			}
			
		} catch (Exception e) {
			log.warn("压缩文件失败,{}",e.getMessage());
			throw ExceptionUtils.create(EpCoreAdminEnum.CPMPRESS_FILE_FAIL,e);
		}finally {
			zipFile = null;
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			}
		}
	}
	private String getName(String name, String value){
		if(Tools.isBlank(value)){
			return name;
		}
		int indexOf = name.lastIndexOf(".");
		int lastIndexOf = value.lastIndexOf(".");
		if(indexOf != -1 && lastIndexOf == -1){
			String suffix = name.substring(indexOf);
			return value + suffix;
		}
		if(lastIndexOf == -1){
			value = value.concat(EpCoreAdminConstants.IMAGE_TYPE);
		}
		return value;
	}
	private void createFile(String savePath){
		if(zipFile ==null){
			zipFile = new File(savePath);
		}
		if(!zipFile.getParentFile().exists()){
			zipFile.getParentFile().mkdirs();
		}
	}
	private String processDirName(String dirName){
		if(dirName.startsWith(File.separator)){
			dirName = dirName.substring(1);
		}
		if(dirName.length() !=0 && !dirName.endsWith(File.separator)){
			dirName = dirName + File.separator;
		}
		return dirName;
	}
	private void finish(){
		if(stream != null){
			try {
				stream.finish();
				stream.close();
			} catch (IOException e) {
				stream = null;
			}
		}
	}
}
