package com.xsqwe.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportExcelUtils<T> {
    Logger logger = LoggerFactory.getLogger(ExportExcelUtils.class);
    public static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###.00");

    public ExportExcelUtils() {
    }

    public void exportExcel(Collection<T> dataset, OutputStream out) {
        this.exportExcel("测试POI导出EXCEL文档", (String[])null, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out) {
        this.exportExcel("测试POI导出EXCEL文档", headers, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
        this.exportExcel("测试POI导出EXCEL文档", headers, dataset, out, pattern);
    }

    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth((short)20);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setAlignment((short)2);
        HSSFFont font = workbook.createFont();
        font.setColor((short)20);
        font.setFontHeightInPoints((short)12);
        font.setBoldweight((short)700);
        style.setFont(font);
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor((short)43);
        style2.setFillPattern((short)1);
        style2.setBorderBottom((short)1);
        style2.setBorderLeft((short)1);
        style2.setBorderRight((short)1);
        style2.setBorderTop((short)1);
        style2.setAlignment((short)2);
        style2.setVerticalAlignment((short)1);
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight((short)400);
        style2.setFont(font2);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)4, 2, (short)6, 5));
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        comment.setAuthor("leno");
        HSSFRow row = sheet.createRow(0);

        for(short i = 0; i < headers.length; ++i) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        Iterator<T> it = dataset.iterator();
        int index = 0;

        while(it.hasNext()) {
            ++index;
            row = sheet.createRow(index);
            T t = it.next();
            Field[] fields = t.getClass().getDeclaredFields();

            for(short i = 0; i < fields.length; ++i) {
                HSSFCell cell = row.createCell(i);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName);
                    Object value = getMethod.invoke(t);
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date)value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof BigDecimal) {
                        textValue = this.decimalFormat.format(value);
                    } else if (value instanceof byte[]) {
                        row.setHeightInPoints(60.0F);
                        sheet.setColumnWidth(i, (short)2856);
                        byte[] bsValue = (byte[])((byte[])value);
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short)6, index, (short)6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(bsValue, 5));
                    } else if (value != null && !"".equals(value)) {
                        textValue = value.toString();
                    } else {
                        textValue = null;
                    }

                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor((short)12);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException var41) {
                    var41.printStackTrace();
                } catch (NoSuchMethodException var42) {
                    var42.printStackTrace();
                } catch (IllegalArgumentException var43) {
                    var43.printStackTrace();
                } catch (IllegalAccessException var44) {
                    var44.printStackTrace();
                } catch (InvocationTargetException var45) {
                    var45.printStackTrace();
                } finally {
                    ;
                }
            }
        }

        try {
            workbook.write(out);
        } catch (IOException var40) {
            var40.printStackTrace();
        }

    }

    @SuppressWarnings("rawtypes")
	public static Workbook listToExcelWorkbook(List list, int rowNumber, String[] headColumns, String[] headKeys, Workbook wb) throws Exception {
        return listToExcelWorkbook(list, rowNumber, headColumns, headKeys, wb, 1);
    }

    public static void createHeader(int rowNumber, String[] headColumns, Sheet sheet) {
        if (headColumns != null && headColumns.length > 0) {
            Row row = sheet.createRow(0);

            for(int i = 0; i < headColumns.length; ++i) {
                Cell cell = row.createCell((short)i);
                cell.setCellValue(headColumns[i]);
            }
        }

    }

    @SuppressWarnings("rawtypes")
	public static Workbook listToExcelWorkbook(List list, int rowNumber, String[] headColumns, String[] headKeys, Workbook wb, int SheetNo) throws Exception {
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        Sheet sheet;
        if (((Workbook)wb).getNumberOfSheets() == 0) {
            sheet = ((Workbook)wb).createSheet("sheet1");
        } else if (((Workbook)wb).getNumberOfSheets() == SheetNo) {
            sheet = ((Workbook)wb).getSheetAt(((Workbook)wb).getNumberOfSheets() - 1);
        } else {
            sheet = ((Workbook)wb).createSheet("sheet" + SheetNo);
        }

        if (rowNumber == 0) {
            createHeader(rowNumber, headColumns, sheet);
        }

        writeData2Sheet(list, rowNumber, headKeys, sheet);
        return (Workbook)wb;
    }

    @SuppressWarnings("rawtypes")
	public static void writeData2Sheet(List list, int rowNumber, String[] headKeys, Sheet sheet) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map map = null;
        int colId = 0;
        int i = 0;

        for(int l = list.size(); i < l; ++i) {
            Object o = list.get(i);
            if (o instanceof Map) {
                map = (Map)o;
            } else {
                map = PropertyUtil.toMap(o);
            }

            Row row = sheet.createRow((short)i + 1 + rowNumber);
            String[] var10 = headKeys;
            int var11 = headKeys.length;

            for(int var12 = 0; var12 < var11; ++var12) {
                String headKey = var10[var12];
                Object obj = map.get(headKey);
                String value = StringUtils.defaultIfEmpty(String.valueOf(obj), "").trim();
                value = value.replaceAll("null", "");
                value = value.replaceAll("\t", " ");
                value = value.replaceAll("\r", " ");
                value = value.replaceAll("\n", " ");
                Cell cell = row.createCell((short)colId);
                if (NumberUtils.isNumeric(obj)) {
                    cell.setCellValue(Double.valueOf(value).doubleValue());
                } else {
                    cell.setCellValue(value);
                }

                ++colId;
            }
        }

    }
}