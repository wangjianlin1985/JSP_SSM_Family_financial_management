// 
// 
// 

package com.finance.dao;

import com.finance.entity.Shares;
import java.util.List;
import java.util.Map;

public interface SharesDao
{
    List<Shares> findShares(Map<String, Object> p0);
    
    Long getTotalShares(Map<String, Object> p0);
    
    int updateShares(Shares p0);
    
    int addShares(Shares p0);
    
    int deleteShares(Integer p0);
    
    List<Shares> getSharesName();
}
