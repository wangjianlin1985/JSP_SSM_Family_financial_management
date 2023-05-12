// 
// 
// 

package com.finance.core.des;

import sun.misc.BASE64Decoder;
import javax.crypto.Cipher;
import sun.misc.BASE64Encoder;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class DESUtils
{
    private static Key key;
    private static String KEY_STR;
    
    static {
        DESUtils.KEY_STR = "ffms";
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            final SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(DESUtils.KEY_STR.getBytes());
            generator.init(56, random);
            DESUtils.key = generator.generateKey();
            generator = null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getEncryptString(final String str) {
        final BASE64Encoder base64en = new BASE64Encoder();
        try {
            final byte[] strBytes = str.getBytes("UTF8");
            final Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, DESUtils.key);
            final byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return base64en.encode(encryptStrBytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getDecryptString(final String str) {
        final BASE64Decoder base64De = new BASE64Decoder();
         
        try {
            final byte[] strBytes = base64De.decodeBuffer(str);
            final Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, DESUtils.key);
            final byte[] decryptStrBytes = cipher.doFinal(strBytes);
            return new String(decryptStrBytes, "UTF8");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
         
    }
    
    public static void main(final String[] args) throws Exception {
        try {
            //System.out.println(getEncryptString("root"));
            //System.out.println(getEncryptString("123456"));
            //System.out.println(getDecryptString("M4uyVNmTp8I="));
            System.out.println(getDecryptString("3Vtg/56BiWY="));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
