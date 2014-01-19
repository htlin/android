package org.easycomm.util;

public class FileUtil {

	public static String getName(String fileWithExt) {
		int dot = fileWithExt.indexOf(".");
		if (dot >= 0) {
			return fileWithExt.substring(0, dot);
		} else {
			return fileWithExt;
		}
	}

}
