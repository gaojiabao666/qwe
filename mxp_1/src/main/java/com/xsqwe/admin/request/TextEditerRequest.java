/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */

package com.xsqwe.admin.request;

import lombok.Data;

/**
 * <p> 
 * </p>
 *
 * @author  herendong
 * @date  2018年7月10日 下午5:22:44
 * @version  1.0.0
 */
@Data
public class TextEditerRequest {
	private int page;
	private float locateX;
	private float locateY;
	private String text;
}
