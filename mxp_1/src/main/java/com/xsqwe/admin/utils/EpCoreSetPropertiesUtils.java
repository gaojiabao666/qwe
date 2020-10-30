package com.xsqwe.admin.utils;

import com.xsqwe.admin.enums.EpCoreAdminEnum;
import com.xsqwe.core.annotation.utils.EpColumnData;
import com.xsqwe.core.annotation.utils.EpColumnUtils;
import com.xsqwe.utils.DateUtils;
import com.xsqwe.web.ExceptionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class EpCoreSetPropertiesUtils {

	public static void setEmptyValueToProperties(Object object) throws Exception {
		if (object == null)
			throw ExceptionUtils.create(EpCoreAdminEnum.OBECCT_IS_BLANK);

		Field[] declaredFields = object.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), object.getClass());
			boolean condition1 = field.getType().equals(Date.class);
			boolean condition2 = field.getType().equals(String.class);
			if (condition1 || condition2)
				pd.getWriteMethod().invoke(object, condition1 ? new Date() : "");
			else
				setEmptyValueToProperties(pd.getReadMethod().invoke(object));
		}
	}

	public static void setPropertiesValue(String dirName, String fieldName, String fieldValue,
                                          Object insuranceApplyRecordResp) throws Exception {
		List<EpColumnData> epColumnDataList = EpColumnUtils.getEpColumnDataList(insuranceApplyRecordResp);

		for (EpColumnData epColumnData : epColumnDataList) {
			String Dir = epColumnData.getDirName();
			if (Dir.equals(dirName)) {
				Field declaredField = insuranceApplyRecordResp.getClass().getDeclaredField(fieldName);
				PropertyDescriptor pd = new PropertyDescriptor(fieldName, insuranceApplyRecordResp.getClass());
				boolean condition = declaredField.getType().equals(Date.class);
				pd.getWriteMethod().invoke(insuranceApplyRecordResp,
						condition ? (DateUtils.toDate(fieldValue)) : fieldValue);
			}

		}

	}
}
