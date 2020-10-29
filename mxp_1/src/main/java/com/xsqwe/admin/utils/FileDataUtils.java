/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.admin.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.meditrusthealth.fast.common.core.utils.StringUtils;
import com.meditrusthealth.fast.common.core.utils.Tools;
import com.meditrusthealth.fast.ep.core.admin.request.EpCoreFileCopyRequest;
import com.meditrusthealth.fast.ep.core.admin.request.TextEditerRequest;

import java.io.*;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年5月30日 下午3:56:08
 * @version 1.0.0
 */
public final class FileDataUtils {
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 *
	 * @param path
	 * @return
	 */
	public static String getImageStr(String path) {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(path);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码，返回字节数组字符串
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(data);
	}

	/**
	 * base64字符串转化成图片
	 *
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
	public boolean generateImage(String imgStr, String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
		if (StringUtils.isNotEmpty(imgStr)) // 图像数据为空
			return false;
		Decoder decoder = Base64.getDecoder();
		try {
			// Base64解码
			byte[] b = decoder.decode(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(imgFilePath);// 新生成的图片
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 文件复制操作
	public static boolean copyFile(EpCoreFileCopyRequest epCoreFileCopyRequest) throws Exception {

		// 创建一个pdf读入流
		PdfReader reader = new PdfReader(epCoreFileCopyRequest.getSource());
		File copy_file = new File(epCoreFileCopyRequest.getTarget());
		File parent = copy_file.getParentFile();
		// 创建复制路径
		if (!parent.exists()) {
			parent.mkdirs();
		}
		// 创建复制文件
		if (!copy_file.exists()) {
			copy_file.createNewFile();
		}
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(copy_file));

		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(bf, 10);
		font.setStyle(Font.BOLD);
		font.getBaseFont();
		if (Tools.isBlank(epCoreFileCopyRequest.getTextEditers())) {
			stamper.close();
			return true;
		}
		for (TextEditerRequest textEditerRequest : epCoreFileCopyRequest.getTextEditers()) {
			PdfContentByte over = stamper.getOverContent(textEditerRequest.getPage());
			over.setFontAndSize(font.getBaseFont(), 10);
			over.setColorFill(BaseColor.BLACK);
			over.setTextMatrix(textEditerRequest.getLocateX(), textEditerRequest.getLocateY());
			over.showText(textEditerRequest.getText());
			over.endText();
		}
		stamper.close();
		return true;
	}
}
