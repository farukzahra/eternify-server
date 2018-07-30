package com.fmz.eternify.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.faces.context.FacesContext;

public class Utils {

	public static String dateToString(Date data, String pattern) {
		String dataStr = null;
		if (data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			dataStr = sdf.format(data);
		}
		return dataStr;
	}

}
