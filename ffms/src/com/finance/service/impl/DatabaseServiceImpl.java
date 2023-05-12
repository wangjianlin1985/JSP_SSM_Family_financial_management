// 
// 
// 

package com.finance.service.impl;

import org.apache.ibatis.annotations.Param;
import com.finance.entity.Database;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.finance.dao.DatabaseDao;
import org.springframework.stereotype.Service;
import com.finance.service.DatabaseService;

@Service("databaseService")
public class DatabaseServiceImpl implements DatabaseService
{
    @Resource
    private DatabaseDao databaseDao;
    
    @Override
    public List<Database> findDataBack(final Map<String, Object> map) {
        return this.databaseDao.findDataBack(map);
    }
    
    @Override
    public Long getDataBackTotal(final Map<String, Object> map) {
        return this.databaseDao.getDataBackTotal(map);
    }
    
    @Override
    public int addDatabase(final Database database) {
        return this.databaseDao.addDatabase(database);
    }
    
    @Override
    public int deleteDatabase(final Integer id) {
        return this.databaseDao.deleteDatabase(id);
    }
    
    @Override
    public int truncateTable(@Param("tablename") final String tablename) {
        return this.databaseDao.truncateTable(tablename);
    }
    
    @Override
    public int deleteOrderdata(@Param("tablename") final String tablename, @Param("startid") final Integer startid, @Param("endid") final Integer endid) {
        return this.databaseDao.deleteOrderdata(tablename, startid, endid);
    }
}
