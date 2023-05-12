// 
// 
// 

package com.finance.service.impl;

import com.finance.util.DateUtil;
import com.finance.entity.Income;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.finance.dao.IncomeDao;
import org.springframework.stereotype.Service;
import com.finance.service.IncomeService;

@Service("incomeService")
public class IncomeServiceImpl implements IncomeService
{
    @Resource
    private IncomeDao incomeDao;
    
    @Override
    public List<Income> findIncome(final Map<String, Object> map) {
        return this.incomeDao.findIncome(map);
    }
    
    @Override
    public List<Income> getIncomeLine(final Map<String, Object> map) {
        return this.incomeDao.getIncomeLine(map);
    }
    
    @Override
    public Long getTotalIncome(final Map<String, Object> map) {
        return this.incomeDao.getTotalIncome(map);
    }
    
    @Override
    public int addIncome(final Income income) {
        income.setCreatetime(DateUtil.getCurrentDateStr());
        return this.incomeDao.addIncome(income);
    }
    
    @Override
    public int updateIncome(final Income income) {
        income.setUpdatetime(DateUtil.getCurrentDateStr());
        return this.incomeDao.updateIncome(income);
    }
    
    @Override
    public int deleteIncome(final Integer id) {
        return this.incomeDao.deleteIncome(id);
    }
    
    @Override
    public List<Income> getIncomer() {
        return this.incomeDao.getIncomer();
    }
}
