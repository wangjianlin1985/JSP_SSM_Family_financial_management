// 
// 
// 

package com.finance.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
    public static String formatDate(final Date date, final String format) {
        String result = "";
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            result = sdf.format(date);
        }
        return result;
    }
    
    public static Date formatString(final String str, final String format) throws ParseException {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }
    
    public static String getCurrentDateStr() {
        final Date date = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    
    public static String getCurrentDateCustomFormat(final String format) {
        final Date date = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    
    public static void main(final String[] args) throws ParseException {
        System.out.println(getCurrentDateStr());
        System.out.println(getCurrentDateCustomFormat("yyyyMMddHHmmss"));
        System.out.println(getCurrentDateCustomFormat("yyyy-MM-dd HH:mm:ss"));
    }
}
