package com.xsqwe.utils;

import java.io.Serializable;

import com.itextpdf.text.BaseColor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PDFSearchLocation implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4744469343928701307L;
    /**
     * 绝对定位X
     */
    private Float absX;
    /**
     * 绝对定位Y
     */
    private Float absY;
    /**
     * 页码
     */
    private int page=1;
    /**
     * 替换的文字
     */
    private String addText;
    /**
     * 相对keyWords移动X
     */
    private float relativeX=0f;
    /**
     * 相对keyWords移动Y
     */
    private float relativeY=0f;

    private float fontSize=10f;

    private BaseColor color=null;
}