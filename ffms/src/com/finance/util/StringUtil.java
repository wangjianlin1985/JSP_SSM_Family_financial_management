// 
// 
// 

package com.finance.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
    public static void main(final String[] args) {
        System.out.println(isURL("ftp://xxxx"));
        System.out.println(isURL("http://www.baid.com"));
        System.out.println(isURL("http://toutiao.io/"));
        System.out.println(isURL("http://blog.daocloud.io/dockervsvm/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io"));
    }
    
    public static boolean isEmpty(final String str) {
        return str == null || "".equals(str.trim());
    }
    
    public static boolean isNotEmpty(final String str) {
        return str != null && !"".equals(str.trim());
    }
    
    public static String formatLike(final String str) {
        if (isNotEmpty(str)) {
            return "%" + str + "%";
        }
        return null;
    }
    
    public static String covertNull(final String str) {
        if (str == null || "NULL".equalsIgnoreCase(str)) {
            return "";
        }
        return str;
    }
    
    public static String getFileExtName(final String str) {
        if (str == null || "NULL".equalsIgnoreCase(str)) {
            return "";
        }
        return str.substring(str.lastIndexOf(".") + 1);
    }
    
    public static String trimBeginStr(String oldStr, final String replaceStr) {
        while (oldStr.startsWith(replaceStr)) {
            oldStr = oldStr.replaceFirst(replaceStr, "");
        }
        return oldStr;
    }
    
    public static String trimEndStr(String oldStr, final String replaceStr) {
        oldStr = trimBeginStr(new StringBuffer(oldStr).reverse().toString(), new StringBuffer(replaceStr).reverse().toString());
        return new StringBuffer(oldStr).reverse().toString();
    }
    
    public static boolean isURL(final String url) {
        final String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        final Pattern patt = Pattern.compile(regex);
        final Matcher matcher = patt.matcher(url);
        final boolean isMatch = matcher.matches();
        return isMatch;
    }
}
