// 
// 
// 

package com.finance.service.impl;

import com.finance.util.DateUtil;
import com.finance.entity.Shares;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.finance.dao.SharesDao;
import org.springframework.stereotype.Service;
import com.finance.service.SharesService;

@Service("sharesService")
public class SharesServiceImpl implements SharesService
{
    @Resource
    private SharesDao sharesDao;
    
    @Override
    public List<Shares> findShares(final Map<String, Object> map) {
        return this.sharesDao.findShares(map);
    }
    
    @Override
    public Long getTotalShares(final Map<String, Object> map) {
        return this.sharesDao.getTotalShares(map);
    }
    
    @Override
    public int addShares(final Shares shares) {
        shares.setCreatetime(DateUtil.getCurrentDateStr());
        return this.sharesDao.addShares(shares);
    }
    
    @Override
    public int updateShares(final Shares shares) {
        shares.setUpdatetime(DateUtil.getCurrentDateStr());
        return this.sharesDao.updateShares(shares);
    }
    
    @Override
    public int deleteShares(final Integer id) {
        return this.sharesDao.deleteShares(id);
    }
    
    @Override
    public List<Shares> getSharesName() {
        return this.sharesDao.getSharesName();
    }
}
