package com.xsqwe.core.annotation.utils;

import com.xsqwe.utils.Tools;
import com.xsqwe.web.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年6月12日 下午4:10:31
 * @version 1.0.0
 */

public abstract class AbstractEpColumnDicAdapter implements IEpColumnDicAdapter {

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractEpColumnDicAdapter.class);

	protected static final String NAME_SUFFIX = "DicDesc";

	protected static final String DESC_SUFFIX = "字典描述";

	@Override
	public EpColumnData getDescColumnData(EpColumnData source) {
		if (source == null) {
			return null;
		}
		if (!source.isFlag()) {
			return null;
		}
		if (source.getValue() == null) {
			return null;
		}
		try {
			Object dicValue = getDicValue(source);
			if (dicValue == null) {
				return null;
			}
			EpColumnData dicEpColumnData = new EpColumnData();
			dicEpColumnData.setDirName(source.getDirName());
			dicEpColumnData.setName(getDicName(source.getName()));
			dicEpColumnData.setDesc(getDicDesc(source.getDesc()));
			dicEpColumnData.setDictCode(source.getDictCode());
			dicEpColumnData.setValue(dicValue);
			dicEpColumnData.setFlag(source.isFlag());
			dicEpColumnData.setSecurity(getDicSecurity());
			dicEpColumnData.setDisplay(source.isDisplay());
			dicEpColumnData.setRequired(source.isRequired());
			return dicEpColumnData;
		} catch (Exception e) {
			LOG.error("字典适配转换异常{}", e.getMessage(), e);
		}
		return null;
	}

	private boolean getDicSecurity() {
		return false;
	}

	private String getDicName(String name) {
		if (Tools.isBlank(name)) {
			return Tools.EMPTY_STRING;
		}
		return name.concat(NAME_SUFFIX);
	}

	private String getDicDesc(String desc) {
		if (Tools.isBlank(desc)) {
			return Tools.EMPTY_STRING;
		}
		return desc.concat(DESC_SUFFIX);
	}

	public abstract Object getDicValue(EpColumnData epColumnData) throws CommonException;

}
