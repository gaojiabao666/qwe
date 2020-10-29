package com.xsqwe.admin.utils;

import java.util.ArrayList;
import java.util.List;

public class EpCorePagingUtils {

	public static <T> List<T> getPagingResult(int page, int pageSize, List<T> inputList){
		int number = 0;
		List<T> outputList = new ArrayList<T>();
		for(T element : inputList){
			if(number >= page*pageSize){
				return outputList;
			}
			if(number >= (page-1)*pageSize){
				outputList.add(element);
			}
			number ++;
		}
		return outputList;
	}
}
