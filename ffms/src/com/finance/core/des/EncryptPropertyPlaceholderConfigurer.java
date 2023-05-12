// 
// 
// 

package com.finance.core.des;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
    private String[] encryptPropNames;
    
    public EncryptPropertyPlaceholderConfigurer() {
        this.encryptPropNames = new String[] { "username", "password" };
    }
    
    protected String convertProperty(final String propertyName, final String propertyValue) {
        if (this.isEncryptProp(propertyName)) {
            final String decryptValue = DESUtils.getDecryptString(propertyValue);
            return decryptValue;
        }
        return propertyValue;
    }
    
    private boolean isEncryptProp(final String propertyName) {
        String[] encryptPropNames;
        for (int length = (encryptPropNames = this.encryptPropNames).length, i = 0; i < length; ++i) {
            final String encryptPropName = encryptPropNames[i];
            if (encryptPropName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }
}
