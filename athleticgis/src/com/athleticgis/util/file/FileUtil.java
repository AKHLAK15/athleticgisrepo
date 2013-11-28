package com.athleticgis.util.file;

public class FileUtil {
	public static String fileExtension(String fileName) {
		String extension = null;

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
}
