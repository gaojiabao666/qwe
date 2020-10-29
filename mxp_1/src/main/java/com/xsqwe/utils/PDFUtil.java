package com.xsqwe.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.xsqwe.web.enums.CommonCodeEnum;
import com.xsqwe.web.exception.CommonException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PDFUtil {
    private static int page = 0;

    public List<float[]> searchwords(String keywords, InputStream is) throws Exception {
        List<float[]> ls = new ArrayList<>();
        try {
            PdfReader reader = new PdfReader(is);
            int pageNum = reader.getNumberOfPages();
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(reader);
            for (page = 1; page < pageNum; page++) {
                pdfReaderContentParser.processContent(page, new RenderListener() {
                    @Override
                    public void renderText(TextRenderInfo renderInfo) {
                        String text = renderInfo.getText();
                        if (null != text) {
                            if (Tools.isBlank(keywords)) {
                                return;
                            }
                            if (text.contains(keywords)) {
                                Rectangle2D.Float boundingRectange = renderInfo.getBaseline().getBoundingRectange();
                                ls.add(new float[] { boundingRectange.x, boundingRectange.y, page });
                            }
                        }
                    }

                    @Override
                    public void renderImage(ImageRenderInfo renderInfo) {

                    }

                    @Override
                    public void endTextBlock() {

                    }

                    @Override
                    public void beginTextBlock() {

                    }
                });
            }
        } catch (Exception e) {
            log.error("PDF处理异常:{}", e);
            throw new CommonException(CommonCodeEnum.COMMON_EXCEPTION.getCode(), "文件处理错误");
        }
        return ls;
    }

    public byte[] addKeywords(List<PDFSearchLocation> locations, InputStream is) throws CommonException {
        try {
            PdfReader reader = new PdfReader(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);
            for (PDFSearchLocation location : locations) {
                if (Tools.isBlank(location.getAddText())) {
                    continue;
                }
                if (location.getPage() <= 0) {
                    location.setPage(1);
                }
                PdfContentByte over = stamper.getOverContent(location.getPage());
                over.beginText();
                over.moveText(location.getAbsX() + location.getRelativeX(),
                        location.getAbsY() + location.getRelativeY()); // 设置文字的位置
                BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                over.setFontAndSize(bfChinese, location.getFontSize());
                if(location.getColor()!=null){
                    over.setColorFill(location.getColor());
                }
                over.showText(location.getAddText());
                over.endText();
            }
            stamper.close();
            reader.close();
            return bos.toByteArray();
        } catch (Exception e) {
            log.error("PDF处理异常:{}", e);
            throw new CommonException(CommonCodeEnum.COMMON_EXCEPTION.getCode(), "文件处理错误");
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
    }

    public void addKeywords(List<PDFSearchLocation> locations, File file, String saveasFilepath)
            throws CommonException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = addKeywords(locations, new FileInputStream(file));
            if (bytes == null || bytes.length == 0) {
                throw new CommonException(CommonCodeEnum.COMMON_EXCEPTION.getCode(), "文件处理错误");
            }
            fos = new FileOutputStream(saveasFilepath);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            log.error("PDF处理异常:{}", e);
            throw new CommonException(CommonCodeEnum.COMMON_EXCEPTION.getCode(), "文件处理错误");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }



    public static void main(String[] args) throws Exception {
        PDFUtil pdf = new PDFUtil();
        List<PDFSearchLocation> ls = new ArrayList<>();
        PDFSearchLocation location = new PDFSearchLocation();
        location.setAddText("陆则国");
        location.setRelativeX(60);
        location.setAbsX(89.784f);
        location.setAbsY(547.63f);
        ls.add(location);

        location = new PDFSearchLocation();
        location.setAddText("陆则国");
        location.setRelativeX(60);
        location.setAbsX(89.784f);
        location.setAbsY(422.35f);
        ls.add(location);

        PDFSearchLocation location1 = new PDFSearchLocation();
        location1.setAddText("31000000000");
        location1.setAbsY(547.63f);
        location1.setAbsX(286.97f);
        location1.setRelativeX(80);
        ls.add(location1);

        System.out.print(JSON.toJSONString(pdf.searchwords("申请", new FileInputStream("/Users/skirrund/Desktop/wkt.pdf"))));

    //    byte[] bytes =  pdf.replaceKeywords(ls,new FileInputStream("/Users/skirrund/Desktop/wkt.pdf"));       
    //    if(bytes!=null&&bytes.length>0){
    //        FileOutputStream fos = new FileOutputStream("/Users/skirrund/Desktop/wkt123.pdf");
    //         fos.write(bytes);
    //         fos.flush();
    //         fos.close();
    //    }
   }
}
