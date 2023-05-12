// 
// 
// 

package com.finance.entity;

public class Shares
{
    private Integer id;
    private Integer userid;
    private Integer roleid;
    private Integer securityid;
    private String sharesname;
    private String holder;
    private String createtime;
    private String updatetime;
    private String username;
    private String securityname;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Integer getUserid() {
        return this.userid;
    }
    
    public void setUserid(final Integer userid) {
        this.userid = userid;
    }
    
    public Integer getRoleid() {
        return this.roleid;
    }
    
    public void setRoleid(final Integer roleid) {
        this.roleid = roleid;
    }
    
    public Integer getSecurityid() {
        return this.securityid;
    }
    
    public void setSecurityid(final Integer securityid) {
        this.securityid = securityid;
    }
    
    public String getSharesname() {
        return this.sharesname;
    }
    
    public void setSharesname(final String sharesname) {
        this.sharesname = sharesname;
    }
    
    public String getHolder() {
        return this.holder;
    }
    
    public void setHolder(final String holder) {
        this.holder = holder;
    }
    
    public String getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(final String createtime) {
        this.createtime = createtime;
    }
    
    public String getUpdatetime() {
        return this.updatetime;
    }
    
    public void setUpdatetime(final String updatetime) {
        this.updatetime = updatetime;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getSecurityname() {
        return this.securityname;
    }
    
    public void setSecurityname(final String securityname) {
        this.securityname = securityname;
    }
}
