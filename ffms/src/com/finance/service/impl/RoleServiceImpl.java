// 
// 
// 

package com.finance.service.impl;

import java.util.Map;
import com.finance.entity.Role;
import java.util.List;
import javax.annotation.Resource;
import com.finance.dao.RoleDao;
import org.springframework.stereotype.Service;
import com.finance.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService
{
    @Resource
    private RoleDao roleDao;
    
    @Override
    public List<Role> getRoles() {
        return this.roleDao.getRoles();
    }
    
    @Override
    public List<Role> findRole(final Map<String, Object> map) {
        return this.roleDao.findRole(map);
    }
    
    @Override
    public Long getTotalRole(final Map<String, Object> map) {
        return this.roleDao.getTotalRole(map);
    }
    
    @Override
    public int updateRole(final Role role) {
        return this.roleDao.updateRole(role);
    }
    
    @Override
    public int addRole(final Role role) {
        return this.roleDao.addRole(role);
    }
    
    @Override
    public int deleteRole(final Integer id) {
        return this.roleDao.deleteRole(id);
    }
}
