package com.lxr.commons.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileUtils {

	public static String readString(InputStream in) {

		return null;

	}
	
	public static String readString(String path) throws FileNotFoundException {

		return readString(new FileInputStream(path));

	}
	
}
