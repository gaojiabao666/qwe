package com.xsqwe.admin.utils;

import java.beans.PropertyDescriptor;
import java.util.Comparator;

public class EpCoreGetComparatorUtils {

	public static <T> Comparator<T> getComparator(String feild){
		
		return new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				PropertyDescriptor pd1;
				PropertyDescriptor pd2;
				int result = 0;
				try {
					pd1 = new PropertyDescriptor(feild,o1.getClass());
					pd2 = new PropertyDescriptor(feild,o2.getClass());
					result = pd2.getReadMethod().invoke(o2).toString().compareTo(pd1.getReadMethod().invoke(o1).toString());
				}catch(Exception e){
					e.printStackTrace();
				}
				return result;
			}
		};
	
	}
}
