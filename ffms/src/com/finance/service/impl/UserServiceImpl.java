// 
// 
// 

package com.finance.service.impl;

import com.finance.util.DateUtil;
import java.util.List;
import java.util.Map;
import com.finance.util.Base64Util;
import com.finance.entity.User;
import javax.annotation.Resource;
import com.finance.dao.UserDao;
import org.springframework.stereotype.Service;
import com.finance.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService
{
    @Resource
    private UserDao userDao;
    
    @Override
    public User loginUsername(final User user) {
        return this.userDao.loginUsername(user);
    }
    
    @Override
    public User loginPassword(final User user) {
        user.setPassword(Base64Util.encode(user.getPassword(), "UTF-8"));
        return this.userDao.loginPassword(user);
    }
    
    @Override
    public User loginRolename(final User user) {
        return this.userDao.loginRolename(user);
    }
    
    @Override
    public List<User> findUser(final Map<String, Object> map) {
        return this.userDao.findUser(map);
    }
    
    @Override
    public int updateUser(final User user) {
        user.setPassword(Base64Util.encode(user.getPassword(), "UTF-8"));
        user.setUpdatetime(DateUtil.getCurrentDateStr());
        return this.userDao.updateUser(user);
    }
    
    @Override
    public Long getTotalUser(final Map<String, Object> map) {
        return this.userDao.getTotalUser(map);
    }
    
    @Override
    public int addUser(final User user) {
        user.setPassword(Base64Util.encode(user.getPassword(), "UTF-8"));
        user.setCreatetime(DateUtil.getCurrentDateStr());
        return this.userDao.addUser(user);
    }
    
    @Override
    public int addUserRole(final User user) {
        return this.userDao.addUserRole(user);
    }
    
    @Override
    public int addSign(final User user) {
        user.setPassword(Base64Util.encode(user.getPassword(), "UTF-8"));
        user.setCreatetime(DateUtil.getCurrentDateStr());
        return this.userDao.addSign(user);
    }
    
    @Override
    public int deleteUser(final Integer id) {
        return this.userDao.deleteUser(id);
    }
    
    @Override
    public long getUserIsExists(final User user) {
        return this.userDao.getUserIsExists(user);
    }
    
    @Override
    public User getUserById(final Integer id) {
        return this.userDao.getUserById(id);
    }
    
    @Override
    public List<User> getAllUser(final Map<String, Object> map) {
        return this.userDao.getAllUser(map);
    }
}
