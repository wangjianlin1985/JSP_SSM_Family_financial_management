// 
// 
// 

package com.finance.dao;

import java.util.Map;
import com.finance.entity.Datadic;
import java.util.List;

public interface DatadicDao
{
    List<Datadic> getDatadicIncome();
    
    List<Datadic> getDatadicPay();
    
    List<Datadic> getDatadicSecurity();
    
    List<Datadic> getDatadicTrade();
    
    List<Datadic> getDatadicname();
    
    List<Datadic> findDatadic(Map<String, Object> p0);
    
    Long getTotalDatadic(Map<String, Object> p0);
    
    int updateDatadic(Datadic p0);
    
    int addDatadic(Datadic p0);
    
    int deleteDatadic(Integer p0);
}
