// 
// 
// 

package com.finance.dao;

import java.util.List;
import java.util.Map;
import com.finance.entity.User;

public interface UserDao
{
    User loginUsername(User p0);
    
    User loginPassword(User p0);
    
    User loginRolename(User p0);
    
    List<User> findUser(Map<String, Object> p0);
    
    Long getTotalUser(Map<String, Object> p0);
    
    int updateUser(User p0);
    
    int addUser(User p0);
    
    int addUserRole(User p0);
    
    int addSign(User p0);
    
    int deleteUser(Integer p0);
    
    long getUserIsExists(User p0);
    
    User getUserById(Integer p0);
    
    List<User> getAllUser(Map<String, Object> p0);
}
