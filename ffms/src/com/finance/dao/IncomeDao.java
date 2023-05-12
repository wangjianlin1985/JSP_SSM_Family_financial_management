// 
// 
// 

package com.finance.dao;

import com.finance.entity.Income;
import java.util.List;
import java.util.Map;

public interface IncomeDao
{
    List<Income> findIncome(Map<String, Object> p0);
    
    List<Income> getIncomeLine(Map<String, Object> p0);
    
    Long getTotalIncome(Map<String, Object> p0);
    
    int updateIncome(Income p0);
    
    int addIncome(Income p0);
    
    int deleteIncome(Integer p0);
    
    List<Income> getIncomer();
}
