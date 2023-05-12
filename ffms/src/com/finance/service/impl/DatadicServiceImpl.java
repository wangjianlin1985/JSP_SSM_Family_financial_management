// 
// 
// 

package com.finance.service.impl;

import java.util.Map;
import com.finance.entity.Datadic;
import java.util.List;
import javax.annotation.Resource;
import com.finance.dao.DatadicDao;
import org.springframework.stereotype.Service;
import com.finance.service.DatadicService;

@Service("datadicService")
public class DatadicServiceImpl implements DatadicService
{
    @Resource
    private DatadicDao datadicDao;
    
    @Override
    public List<Datadic> getDatadicIncome() {
        return this.datadicDao.getDatadicIncome();
    }
    
    @Override
    public List<Datadic> getDatadicPay() {
        return this.datadicDao.getDatadicPay();
    }
    
    @Override
    public List<Datadic> getDatadicSecurity() {
        return this.datadicDao.getDatadicSecurity();
    }
    
    @Override
    public List<Datadic> findDatadic(final Map<String, Object> map) {
        return this.datadicDao.findDatadic(map);
    }
    
    @Override
    public Long getTotalDatadic(final Map<String, Object> map) {
        return this.datadicDao.getTotalDatadic(map);
    }
    
    @Override
    public int updateDatadic(final Datadic datadic) {
        return this.datadicDao.updateDatadic(datadic);
    }
    
    @Override
    public int addDatadic(final Datadic datadic) {
        return this.datadicDao.addDatadic(datadic);
    }
    
    @Override
    public int deleteDatadic(final Integer id) {
        return this.datadicDao.deleteDatadic(id);
    }
    
    @Override
    public List<Datadic> getDatadicname() {
        return this.datadicDao.getDatadicname();
    }
    
    @Override
    public List<Datadic> getDatadicTrade() {
        return this.datadicDao.getDatadicTrade();
    }
}
