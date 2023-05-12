// 
// 
// 

package com.finance.service;

import com.finance.entity.Pay;
import java.util.List;
import java.util.Map;

public interface PayService
{
    List<Pay> findPay(Map<String, Object> p0);
    
    List<Pay> getPayLine(Map<String, Object> p0);
    
    long getTotalPay(Map<String, Object> p0);
    
    int updatePay(Pay p0);
    
    int addPay(Pay p0);
    
    int deletePay(Integer p0);
    
    List<Pay> getPayer();
}
