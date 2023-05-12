// 
// 
// 

package com.finance.service;

import org.apache.ibatis.annotations.Param;
import com.finance.entity.Database;
import java.util.List;
import java.util.Map;

public interface DatabaseService
{
    List<Database> findDataBack(Map<String, Object> p0);
    
    Long getDataBackTotal(Map<String, Object> p0);
    
    int addDatabase(Database p0);
    
    int deleteDatabase(Integer p0);
    
    int truncateTable(@Param("tablename") String p0);
    
    int deleteOrderdata(@Param("tablename") String p0, @Param("startid") Integer p1, @Param("endid") Integer p2);
}
