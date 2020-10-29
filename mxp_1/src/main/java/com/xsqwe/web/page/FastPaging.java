/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.web.page;

import lombok.Data;

/**
 * <p>
 * WEB 分页基础类
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午3:30:40
 * @version 1.0.0
 */
@Data
public class FastPaging {

	public static final int DEFAULT_PAGE = 1;

	public static final int DEFAULT_PAGE_SIZE = 10;

	private int page = DEFAULT_PAGE;

	private int pageSize = DEFAULT_PAGE_SIZE;

}
