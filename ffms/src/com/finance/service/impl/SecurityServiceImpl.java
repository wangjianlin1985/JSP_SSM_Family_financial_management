// 
// 
// 

package com.finance.service.impl;

import com.finance.util.DateUtil;
import com.finance.entity.Security;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.finance.dao.SecurityDao;
import org.springframework.stereotype.Service;
import com.finance.service.SecurityService;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService
{
    @Resource
    private SecurityDao securityDao;
    
    @Override
    public List<Security> findSecurity(final Map<String, Object> map) {
        return this.securityDao.findSecurity(map);
    }
    
    @Override
    public Long getTotalSecurity(final Map<String, Object> map) {
        return this.securityDao.getTotalSecurity(map);
    }
    
    @Override
    public int updateSecurity(final Security security) {
        security.setUpdatetime(DateUtil.getCurrentDateStr());
        return this.securityDao.updateSecurity(security);
    }
    
    @Override
    public int addSecurity(final Security security) {
        security.setCreatetime(DateUtil.getCurrentDateStr());
        return this.securityDao.addSecurity(security);
    }
    
    @Override
    public int deleteSecurity(final Integer id) {
        return this.securityDao.deleteSecurity(id);
    }
    
    @Override
    public List<Security> getAllSecurity() {
        return this.securityDao.getAllSecurity();
    }
}
