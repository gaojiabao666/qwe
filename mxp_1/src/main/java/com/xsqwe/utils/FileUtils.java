package com.xsqwe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class FileUtils {
	private String fileName = "";
	private String filePath = "";
	private String fileShortName = "";
	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	public FileUtils(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public FileUtils() {
	}

	public static String extractFileName(String fullFileName) {
		return fullFileName.substring(fullFileName.lastIndexOf("\\") + 1);
	}

	public static boolean isEmptyFile(CommonsMultipartFile orginalFile) {
		return orginalFile == null || orginalFile.isEmpty();
	}

	public static String getFileName(CommonsMultipartFile orginalFile) {
		return orginalFile.getOriginalFilename();
	}

	public static List<String> getFileList(String path) {
		return getFileList(path, 0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getFileList(String path, int minutes) {
		List<String> fileLists = new ArrayList();
		File file = new File(path);
		File[] array = file.listFiles();

		for (int i = 0; i < array.length; ++i) {
			if (array[i].isFile()
					&& DateUtils.curTimestamp().getTime() - array[i].lastModified() > (long) (minutes * '\uea60')) {
				fileLists.add(array[i].getName());
			}
		}

		return fileLists;
	}

	public static void remove(String fileName) {
		(new File(fileName)).delete();
	}

	public static Double getFileSize(String srcFile) {
		File file = new File(srcFile);
		if (!file.exists() && !file.isFile()) {
			throw new RuntimeException("src file not exits!");
		} else {
			return new Double((double) file.length() / 1000.0D);
		}
	}

	public static String formatFileSize(String filePath) {
		File file = new File(filePath);
		return file.exists() && file.isFile() ? formatFileSize(file) : "";
	}

	public static String formatFileSize(File srcFile) {
		return srcFile.isFile() ? formatFileSize(srcFile.length()) : "";
	}

	public static String formatFileSize(long fileSize) {
		String showSize = "";
		if (fileSize >= 0L && fileSize < 1024L) {
			showSize = fileSize + "B";
		} else if (fileSize >= 1024L && fileSize < 1048576L) {
			showSize = Long.toString(fileSize / 1024L) + "KB";
		} else if (fileSize >= 1048576L && fileSize < 1073741824L) {
			showSize = Long.toString(fileSize / 1048576L) + "MB";
		} else if (fileSize >= 1073741824L) {
			showSize = Long.toString(fileSize / 1073741824L) + "GB";
		}

		return showSize;
	}

	public static boolean move(String srcFile, String destPath, String destFile) {
		File file = new File(srcFile);
		if (!file.exists()) {
			throw new RuntimeException("src file not exits!");
		} else {
			File dir = new File(destPath);
			dir.mkdirs();
			log.info("目录是否存在？ = " + dir.exists());
			log.info("能否创建目录？ = " + dir.mkdirs());
			System.out.println("目录是否存在？ = " + dir.exists());
			System.out.println("能否创建目录？ = " + dir.mkdirs());
			File newFile = new File(dir, destFile);
			log.info("destFile = " + destFile);
			log.info("newFile = " + newFile.getName());
			System.out.println("newFile.getName() = " + newFile.getName());
			if (newFile.exists()) {
				throw new RuntimeException("dest file already exits!");
			} else {
				boolean success = file.renameTo(newFile);
				return success;
			}
		}
	}

	public static File copyTo(File file, File directory, String filename) throws Exception {
		if (filename != null && !"".equals(filename)) {
			if (directory == null) {
				throw new Exception("error");
			} else {
				try {
					if (!directory.exists() && !directory.mkdirs()) {
						throw new Exception("Can not create the directory!");
					}

					if (!directory.isDirectory()) {
						throw new Exception("The given direoctry name is not a directory!");
					}
				} catch (SecurityException var4) {
					throw new Exception("error");
				}

				String pathname = directory.getAbsolutePath();
				if (!pathname.endsWith(File.separator)) {
					pathname = pathname + File.separator;
				}

				while (filename.startsWith(File.separator)) {
					filename = filename.substring(1);
				}

				return copyTo(file, new File(pathname + filename));
			}
		} else {
			throw new Exception("error");
		}
	}

	public static File copyTo(File source, File desc) throws Exception {
		if (source == null) {
			throw new Exception("The source file is null!");
		} else {
			FileInputStream fis = null;
			FileOutputStream fos = null;

			try {
				if (!source.exists()) {
					throw new Exception("The source file does not exists!");
				} else {
					fis = new FileInputStream(source);
					fos = new FileOutputStream(desc);
					byte[] bufferBytes = new byte[4096];
					int bytesRead = 0;

					while (bytesRead != -1) {
						bytesRead = fis.read(bufferBytes);
						if (bytesRead > 0) {
							fos.write(bufferBytes, 0, bytesRead);
						}
					}

					fos.close();
					fis.close();
					File var6 = desc;
					return var6;
				}
			} catch (SecurityException var17) {
				log.error("SecurityException", var17);
				throw new Exception(var17.getMessage());
			} catch (FileNotFoundException var18) {
				log.error("FileNotFoundException", var18);
				throw new Exception(var18);
			} catch (IOException var19) {
				log.error("IOException", var19);
				throw new Exception(var19);
			} finally {
				try {
					if (fos != null) {
						fos.close();
					}

					if (fis != null) {
						fis.close();
					}
				} catch (Exception var16) {
					log.error("error", var16);
				}

			}
		}
	}

	public static File create(String filePath, String fileName, boolean isDelete) throws Exception {
		File file = new File(filePath + File.separator + fileName);
		if (file.exists()) {
			if (isDelete) {
				file.delete();
				file.createNewFile();
			}
		} else {
			file.createNewFile();
		}

		return file;
	}

	public static File create(String filePath, String fileName) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		file = new File(filePath + File.separator + fileName);
		if (file.exists()) {
			file.delete();
		}

		file.createNewFile();
		return file;
	}

	public static File copyTo(File file, String directory, String filename) throws Exception {
		if (directory == null) {
			throw new Exception("error");
		} else {
			return copyTo(file, new File(directory), filename);
		}
	}

	public static String getFileType(String fileName) {
		return fileName.indexOf(".") >= 0 ? fileName.substring(fileName.lastIndexOf(".") + 1) : fileName;
	}

	public static String rename(String name, String append, boolean isAfter) {
		String ret = "";
		int i = name.lastIndexOf(".");
		if (i == -1) {
			return name;
		} else {
			if (!isAfter) {
				ret = append + "_" + name;
			} else {
				ret = name.substring(0, i) + "_" + append + name.substring(i);
			}

			return ret;
		}
	}

	public static String rename(String name, String append) {
		return rename(name, append, true);
	}

	public static String changeType(String name, String fileType) {
		String ret = "";
		int i = name.lastIndexOf(".");
		if (i == -1) {
			ret = name + "." + fileType;
		} else {
			ret = name.substring(0, i) + "." + fileType;
		}

		return ret;
	}

	public static void createDirectory(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			org.apache.commons.io.FileUtils.forceMkdir(file);
		}

	}

	public static void copyDirectory(File srcDir, File destDir) throws IOException {
		org.apache.commons.io.FileUtils.copyDirectory(srcDir, destDir);
	}

	public static void deleteDirectory(File directory) throws IOException {
		org.apache.commons.io.FileUtils.deleteDirectory(directory);
	}

	public static void cleanDirectory(File directory) throws IOException {
		org.apache.commons.io.FileUtils.deleteDirectory(directory);
	}

	public static void moveDirectory(File srcDir, File destDir) throws IOException {
		org.apache.commons.io.FileUtils.moveDirectory(srcDir, destDir);
	}

	public static void moveFileToDirectory(File srcFile, File destDir, boolean createDestDir) throws IOException {
		File file = new File(destDir, srcFile.getName());
		if (file.exists()) {
			file.delete();
		}

		org.apache.commons.io.FileUtils.moveFileToDirectory(srcFile, destDir, createDestDir);
	}

	public static void moveFile(File srcFile, File destFile) throws IOException {
		org.apache.commons.io.FileUtils.moveFileToDirectory(srcFile, destFile, true);
	}

	public static boolean isValidsFileType(String fileType, String[] fileTypes) {
		fileType = fileType.toLowerCase();
		String[] var2 = fileTypes;
		int var3 = fileTypes.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			String str = var2[var4];
			if (str.equals(fileType)) {
				return true;
			}
		}

		return false;
	}

	public static FileUtils setFileCommonPara(String directoryName, String imageRootPath, String imagePathPrefix)
			throws IOException {
		PropertyUtil.load();
		String directoryPath = directoryName + File.separator + DateUtils.curDateStr8();
		String directoryFullPath = PropertyUtil.getStringProperty(imageRootPath) + File.separator + directoryPath;
		createDirectory(directoryFullPath);
		FileUtils fileUtils = new FileUtils();
		fileUtils.setFileShortName();
		String filePath = PropertyUtil.getStringProperty(imagePathPrefix) + File.separator + directoryPath
				+ File.separator + fileUtils.getFileShortName();
		String fileName = directoryFullPath + File.separator + fileUtils.getFileShortName();
		fileUtils.setFileName(fileName);
		fileUtils.setFilePath(filePath);
		return fileUtils;
	}

	public String getFileName() {
		this.rwl.readLock().lock();

		String var1;
		try {
			var1 = this.fileName;
		} finally {
			this.rwl.readLock().unlock();
		}

		return var1;
	}

	public void setFileName(String fileName) {
		this.rwl.writeLock().lock();

		try {
			this.fileName = fileName;
		} finally {
			this.rwl.writeLock().unlock();
		}

	}

	public String getFilePath() {
		this.rwl.readLock().lock();

		String var1;
		try {
			var1 = this.filePath;
		} finally {
			this.rwl.readLock().unlock();
		}

		return var1;
	}

	public void setFilePath(String filePath) {
		this.rwl.writeLock().lock();

		try {
			this.filePath = filePath;
		} finally {
			this.rwl.writeLock().unlock();
		}

	}

	public String getFileShortName() {
		this.rwl.readLock().lock();

		String var1;
		try {
			var1 = this.fileShortName;
		} finally {
			this.rwl.readLock().unlock();
		}

		return var1;
	}

	public void setFileShortName() {
		this.rwl.writeLock().lock();

		try {
			this.fileShortName = DateUtils.curDateStr("yyMMddHHmmssSSS") + RandomUtils.generateNumber(2) + ".pdf";
		} finally {
			this.rwl.writeLock().unlock();
		}

	}

	public static void main(String[] args) {
		getFileList("/Users/lancy/Documents/temp/ftpFiles/prsImage/", 5);
	}
}
