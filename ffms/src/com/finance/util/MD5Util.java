// 
// 
// 

package com.finance.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util
{
    private static final String staticSalt = "ffms";
    
    public static void main(final String[] args) {
        System.out.println("xx=" + encodePwd("123456"));
        System.out.println("yy=" + isPwdRight("123456", "cb323f94f7575f0c5cee65521507e1da"));
    }
    
    public static String encodePwd(final String userPwd) {
        return DigestUtils.md5Hex(String.valueOf(userPwd) + "ffms");
    }
    
    public static boolean isPwdRight(final String userPwd, final String dbPwd) {
        boolean rs = false;
        if (encodePwd(userPwd).equals(dbPwd)) {
            rs = true;
        }
        return rs;
    }
}
