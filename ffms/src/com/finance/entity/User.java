// 
// 
// 

package com.finance.entity;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class User
{
    private Integer id;
    private String username;
    private String password;
    private String truename;
    private String email;
    private String phone;
    private String address;
    private Integer sex;
    private Integer age;
    private String appellation;
    private Integer salary;
    private String card;
    private Integer isvalid;
    private String createtime;
    private String updatetime;
    private Integer roleid;
    private String rolename;
    private List<Map<Object, Object>> roleIDsList;
    private String roleIDs;
    private String roleNames;
    
    public User() {
        this.roleIDsList = new ArrayList<Map<Object, Object>>();
        this.roleIDs = "";
        this.roleNames = "";
    }
    
    public List<Map<Object, Object>> getRoleIDsList() {
        return this.roleIDsList;
    }
    
    public void setRoleIDsList(final List<Map<Object, Object>> roleIDsList) {
        this.roleIDsList = roleIDsList;
        if (roleIDsList != null && roleIDsList.size() > 0) {
            for (int size = roleIDsList.size(), i = 0; i < size; ++i) {
                this.roleIDs = String.valueOf(this.roleIDs) + roleIDsList.get(i).get("roleid");
                this.roleNames = String.valueOf(this.roleNames) + roleIDsList.get(i).get("rolename");
                if (i != size - 1) {
                    this.roleIDs = String.valueOf(this.roleIDs) + ",";
                    this.roleNames = String.valueOf(this.roleNames) + ",";
                }
            }
        }
    }
    
    public Integer getRoleid() {
        return this.roleid;
    }
    
    public void setRoleid(final Integer roleid) {
        this.roleid = roleid;
    }
    
    public String getRolename() {
        return this.rolename;
    }
    
    public void setRolename(final String rolename) {
        this.rolename = rolename;
    }
    
    public String getRoleIDs() {
        return this.roleIDs;
    }
    
    public void setRoleIDs(final String roleIDs) {
        this.roleIDs = roleIDs;
    }
    
    public String getRoleNames() {
        return this.roleNames;
    }
    
    public void setRoleNames(final String roleNames) {
        this.roleNames = roleNames;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getTruename() {
        return this.truename;
    }
    
    public void setTruename(final String truename) {
        this.truename = truename;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(final String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
    
    public Integer getSex() {
        return this.sex;
    }
    
    public void setSex(final Integer sex) {
        this.sex = sex;
    }
    
    public Integer getAge() {
        return this.age;
    }
    
    public void setAge(final Integer age) {
        this.age = age;
    }
    
    public String getAppellation() {
        return this.appellation;
    }
    
    public void setAppellation(final String appellation) {
        this.appellation = appellation;
    }
    
    public Integer getSalary() {
        return this.salary;
    }
    
    public void setSalary(final Integer salary) {
        this.salary = salary;
    }
    
    public String getCard() {
        return this.card;
    }
    
    public void setCard(final String card) {
        this.card = card;
    }
    
    public Integer getIsvalid() {
        return this.isvalid;
    }
    
    public void setIsvalid(final Integer isvalid) {
        this.isvalid = isvalid;
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
}
