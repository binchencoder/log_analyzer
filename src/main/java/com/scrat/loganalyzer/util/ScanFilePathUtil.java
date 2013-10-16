package com.scrat.loganalyzer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanFilePathUtil {
	public static List<String> listLogFilePaths(String dir) {
		File directory = new File(dir);
		File[] files = directory.listFiles();
		List<String> pathList = new ArrayList<String>();
		for (File file : files) {
			if (file.isFile()) {
				pathList.add(file.getAbsolutePath());
			}
		}
		return pathList;
	}
	
//	public static List<String> listDirectoryPaths(String dir) {
//		File directory = new File(dir);
//		File[] files = directory.listFiles();
//		List<String> pathList = new ArrayList<String>();
//		for (File file : files) {
//			if (file.isDirectory()) {
//				pathList.add(file.getAbsolutePath());
//			}
//		}
//		return pathList;
//	}
	
	public static void main(String[] args) {
		String dir = "d:/tmp";
		System.out.println(listLogFilePaths(dir));
	}
}
