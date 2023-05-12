// 
// 
// 

package com.finance.service.impl;

import com.finance.util.DateUtil;
import com.finance.entity.Pay;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.finance.dao.PayDao;
import org.springframework.stereotype.Service;
import com.finance.service.PayService;

@Service("payService")
public class PayServiceImpl implements PayService
{
    @Resource
    private PayDao payDao;
    
    @Override
    public List<Pay> findPay(final Map<String, Object> map) {
        return this.payDao.findPay(map);
    }
    
    @Override
    public List<Pay> getPayLine(final Map<String, Object> map) {
        return this.payDao.getPayLine(map);
    }
    
    @Override
    public long getTotalPay(final Map<String, Object> map) {
        return this.payDao.getTotalPay(map);
    }
    
    @Override
    public int updatePay(final Pay pay) {
        pay.setUpdatetime(DateUtil.getCurrentDateStr());
        return this.payDao.updatePay(pay);
    }
    
    @Override
    public int addPay(final Pay pay) {
        pay.setCreatetime(DateUtil.getCurrentDateStr());
        return this.payDao.addPay(pay);
    }
    
    @Override
    public int deletePay(final Integer id) {
        return this.payDao.deletePay(id);
    }
    
    @Override
    public List<Pay> getPayer() {
        return this.payDao.getPayer();
    }
}
