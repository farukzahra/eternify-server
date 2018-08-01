package com.fmz.eternify.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static String splitURL(String url) {
		try {
			return url.split("\\.com/")[1];
		} catch (Exception e) {
		}
		
		return null;
	}

	public static String dateToString(Date data, String pattern) {
		String dataStr = null;
		if (data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			dataStr = sdf.format(data);
		}
		return dataStr;
	}

}
