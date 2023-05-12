// 
// 
// 

package com.finance.dao;

import com.finance.entity.Trade;
import java.util.List;
import java.util.Map;

public interface TradeDao
{
    List<Trade> findTrade(Map<String, Object> p0);
    
    Long getTotalTrade(Map<String, Object> p0);
    
    int updateTrade(Trade p0);
    
    int addTrade(Trade p0);
    
    int deleteTrade(Integer p0);
}
