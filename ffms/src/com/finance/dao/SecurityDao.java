// 
// 
// 

package com.finance.dao;

import com.finance.entity.Security;
import java.util.List;
import java.util.Map;

public interface SecurityDao
{
    List<Security> findSecurity(Map<String, Object> p0);
    
    Long getTotalSecurity(Map<String, Object> p0);
    
    int updateSecurity(Security p0);
    
    int addSecurity(Security p0);
    
    int deleteSecurity(Integer p0);
    
    List<Security> getAllSecurity();
}
