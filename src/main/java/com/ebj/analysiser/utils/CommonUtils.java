package com.ebj.analysiser.utils;

import java.io.File;

public class CommonUtils {
	public static String getFileType(File file) {
		if (file.isDirectory()) {
			return file.getName() + " is a directory.";
		}else {
			return file.getName() + " is a common file.";
		}
	}
}
