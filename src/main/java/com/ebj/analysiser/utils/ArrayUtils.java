package com.ebj.analysiser.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
	public static String[][] merge2DmArray(String[][] content1, String[][] content2) {
		String[][] newArrey = new String[][] {};
		List<String[]> list = new ArrayList<String[]>();
		list.addAll(Arrays.<String[]> asList(content1));
		list.addAll(Arrays.<String[]> asList(content2));
		return list.toArray(newArrey);
	}
	
	public static String[][] merge2DmArray(List<String[][]> stringArrayList) {
		String[][] newArrey = new String[][] {};
		List<String[]> list = new ArrayList<String[]>();
		for (String[][] content : stringArrayList) {
			list.addAll(Arrays.<String[]> asList(content));
		}
		return list.toArray(newArrey);
	}
}
