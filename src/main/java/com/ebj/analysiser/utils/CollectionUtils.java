package com.ebj.analysiser.utils;

import java.util.List;

public class CollectionUtils {
	public static boolean listIsNotBlank(List<Object> list) {
		return !listIsBlank(list);
	}
	public static boolean listIsBlank(List<Object> list) {
		return list == null || list.size() <= 0;
	}
}
