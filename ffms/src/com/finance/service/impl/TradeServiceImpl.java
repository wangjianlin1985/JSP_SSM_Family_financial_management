// 
// 
// 

package com.finance.service.impl;

import com.finance.util.DateUtil;
import com.finance.entity.Trade;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.finance.dao.TradeDao;
import org.springframework.stereotype.Service;
import com.finance.service.TradeService;

@Service("tradeService")
public class TradeServiceImpl implements TradeService
{
    @Resource
    private TradeDao tradeDao;
    
    @Override
    public List<Trade> findTrade(final Map<String, Object> map) {
        return this.tradeDao.findTrade(map);
    }
    
    @Override
    public Long getTotalTrade(final Map<String, Object> map) {
        return this.tradeDao.getTotalTrade(map);
    }
    
    @Override
    public int addTrade(final Trade trade) {
        trade.setMoney(trade.getPrice() * trade.getNumber());
        trade.setCreatetime(DateUtil.getCurrentDateStr());
        return this.tradeDao.addTrade(trade);
    }
    
    @Override
    public int updateTrade(final Trade trade) {
        trade.setMoney(trade.getPrice() * trade.getNumber());
        trade.setUpdatetime(DateUtil.getCurrentDateStr());
        return this.tradeDao.updateTrade(trade);
    }
    
    @Override
    public int deleteTrade(final Integer id) {
        return this.tradeDao.deleteTrade(id);
    }
}
