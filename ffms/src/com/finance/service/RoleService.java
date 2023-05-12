// 
// 
// 

package com.finance.service;

import java.util.Map;
import com.finance.entity.Role;
import java.util.List;

public interface RoleService
{
    List<Role> getRoles();
    
    List<Role> findRole(Map<String, Object> p0);
    
    Long getTotalRole(Map<String, Object> p0);
    
    int updateRole(Role p0);
    
    int addRole(Role p0);
    
    int deleteRole(Integer p0);
}
