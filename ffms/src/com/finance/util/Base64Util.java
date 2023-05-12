// 
// 
// 

package com.finance.util;

import sun.misc.BASE64Decoder;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Encoder;

public class Base64Util
{
    public static void main(final String[] args) {
        final String ec = encode("123456", "UTF-8");
        System.out.println(ec);
        final String dc = decodeStr(ec, "UTF-8");
        System.out.println(dc);
    }
    
    public static String encode(final byte[] bstr) {
        return new BASE64Encoder().encode(bstr);
    }
    
    public static String encode(final String str) {
        return encode(str.getBytes());
    }
    
    public static String encode(final String str, final String charset) {
        String rs = "";
        try {
            rs = encode(str.getBytes(charset));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public static String decodeStr(final String str) {
        byte[] bt = null;
        try {
            final BASE64Decoder decoder = new BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bt);
    }
    
    public static String decodeStr(final String str, final String charset) {
        String rs = "";
        try {
            final BASE64Decoder decoder = new BASE64Decoder();
            final byte[] bt = decoder.decodeBuffer(str);
            rs = new String(bt, charset);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public static byte[] decode(final String str) {
        byte[] bt = null;
        try {
            final BASE64Decoder decoder = new BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bt;
    }
}
