// 
// 
// 

package com.finance.controller;

import java.util.Map;
import net.sf.json.JSONArray;
import com.finance.util.StringUtil;
import java.util.HashMap;
import com.finance.entity.PageBean;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import com.finance.util.ResponseUtil;
import com.finance.util.Base64Util;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.finance.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import com.finance.entity.Role;
import java.util.List;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.InitBinder;
import java.beans.PropertyEditor;
import java.text.DateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.WebDataBinder;
import com.finance.service.RoleService;
import javax.annotation.Resource;
import com.finance.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController
{
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor((Class)Date.class, (PropertyEditor)new CustomDateEditor((DateFormat)dateFormat, true));
    }
    
    @RequestMapping({ "/index.do" })
    public String index(final ModelMap map) {
        final List<Role> list = this.roleService.getRoles();
        map.addAttribute("roles", (Object)list);
        return "login";
    }
    
    @RequestMapping({ "/login.do" })
    public String login(final User user, final HttpServletRequest request, final HttpServletResponse response) {
        final JSONObject result = new JSONObject();
        final User resultUsername = this.userService.loginUsername(user);
        if (resultUsername == null) {
            result.put((Object)"errres", (Object)101);
            result.put((Object)"errmsg", (Object)"\u7528\u6237\u540d\u4e0d\u5b58\u5728\uff01");
            result.put((Object)"inputfocus", (Object)"inputUsername");
        }
        else {
            final User resultPassword = this.userService.loginPassword(user);
            if (resultPassword == null) {
                result.put((Object)"errres", (Object)102);
                result.put((Object)"errmsg", (Object)"\u5bc6\u7801\u4e0d\u6b63\u786e\uff01");
                result.put((Object)"inputfocus", (Object)"inputPassword");
            }
            else {
                final User resultRolename = this.userService.loginRolename(user);
                if (resultRolename == null) {
                    result.put((Object)"errres", (Object)103);
                    result.put((Object)"errmsg", (Object)"\u7528\u6237\u89d2\u8272\u4e0d\u5339\u914d\uff01");
                    result.put((Object)"inputfocus", (Object)"rolename");
                }
                else {
                    resultRolename.setPassword(Base64Util.decodeStr(resultRolename.getPassword(), "UTF-8"));
                    final HttpSession session = request.getSession();
                    session.setAttribute("currentUser", (Object)resultRolename);
                    result.put((Object)"errres", (Object)200);
                }
            }
        }
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/sign.do" })
    public String sign() {
        return "sign";
    }
    
    @RequestMapping({ "/gosign.do" })
    public String gosign(final User user, final HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        int resultTotalother = 0;
        final JSONObject result = new JSONObject();
        if (this.isUserExists(user)) {
            result.put((Object)"errres", (Object)false);
            result.put((Object)"errmsg", (Object)"\u7528\u6237\u540d\u5df2\u7ecf\u88ab\u4f7f\u7528\uff01");
            result.put((Object)"inputfocus", (Object)"inputUsername");
            ResponseUtil.write(response, result);
            return null;
        }
        resultTotal = this.userService.addSign(user);
        resultTotalother = this.userService.addUserRole(user);
        if (resultTotal > 0 && resultTotalother > 0) {
            result.put((Object)"errres", (Object)true);
            result.put((Object)"errmsg", (Object)"\u6ce8\u518c\u6210\u529f\uff0c\u8bf7\u8fd4\u56de\u767b\u5f55\uff01");
        }
        else {
            result.put((Object)"errres", (Object)false);
            result.put((Object)"errmsg", (Object)"\u6ce8\u518c\u5931\u8d25");
            result.put((Object)"inputfocus", (Object)"inputUsername");
        }
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/main.do" })
    public String main(final ModelMap map, final HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final User usersession = (User)session.getAttribute("currentUser");
        final List<Role> list = this.roleService.getRoles();
        map.addAttribute("roles", (Object)list);
        final User usermessage = this.userService.getUserById(usersession.getId());
        usermessage.setPassword(Base64Util.decodeStr(usermessage.getPassword(), "UTF-8"));
        map.addAttribute("usermessage", (Object)usermessage);
        return "main";
    }
    
    @RequestMapping({ "/userManage.do" })
    public String userManage(final ModelMap map) {
        final List<Role> list = this.roleService.getRoles();
        map.addAttribute("roles", (Object)list);
        return "userManage";
    }
    
    @RequestMapping({ "/modifyPassword.do" })
    public String modifyPassword(final User user, final HttpServletResponse response) throws Exception {
        final int resultTotal = this.userService.updateUser(user);
        final JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put((Object)"success", (Object)true);
        }
        else {
            result.put((Object)"success", (Object)false);
        }
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/logout.do" })
    public String logout(final HttpSession session) throws Exception {
        session.removeAttribute("currentUser");
        return "redirect:/index.do";
    }
    
    @RequestMapping({ "/userlist.do" })
    public String list(@RequestParam(value = "page", required = false) final String page, @RequestParam(value = "rows", required = false) final String rows, final User s_user, final HttpServletResponse response) throws Exception {
        final PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", StringUtil.formatLike(s_user.getUsername()));
        map.put("truename", StringUtil.formatLike(s_user.getTruename()));
        map.put("appellation", StringUtil.formatLike(s_user.getAppellation()));
        map.put("sex", s_user.getSex());
        map.put("roleid", s_user.getRoleid());
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        final List<User> userList = this.userService.findUser(map);
        for (int i = 0; i < userList.size(); ++i) {
            userList.get(i).setPassword(Base64Util.decodeStr(userList.get(i).getPassword(), "UTF-8"));
        }
        final Long total = this.userService.getTotalUser(map);
        final JSONObject result = new JSONObject();
        final JSONArray jsonArray = JSONArray.fromObject((Object)userList);
        result.put((Object)"rows", (Object)jsonArray);
        result.put((Object)"total", (Object)total);
        ResponseUtil.write(response, result);
        return null;
    }
    
    private boolean isUserExists(final User user) throws Exception {
        long resultTotal = 0L;
        resultTotal = this.userService.getUserIsExists(user);
        boolean rs = false;
        if (resultTotal > 0L) {
            rs = true;
        }
        return rs;
    }
    
    @RequestMapping({ "/usersave.do" })
    public String save(final User user, final HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        final JSONObject result = new JSONObject();
        if (this.isUserExists(user)) {
            result.put((Object)"errres", (Object)false);
            result.put((Object)"errmsg", (Object)"\u7528\u6237\u540d\u5df2\u7ecf\u88ab\u4f7f\u7528\uff01");
            ResponseUtil.write(response, result);
            return null;
        }
        if (user.getId() == null) {
            resultTotal = this.userService.addUser(user);
            this.userService.addUserRole(user);
        }
        else {
            resultTotal = this.userService.updateUser(user);
        }
        if (resultTotal > 0) {
            result.put((Object)"errres", (Object)true);
            result.put((Object)"errmsg", (Object)"\u6570\u636e\u4fdd\u5b58\u6210\u529f\uff01");
        }
        else {
            result.put((Object)"errres", (Object)false);
            result.put((Object)"errmsg", (Object)"\u6570\u636e\u4fdd\u5b58\u5931\u8d25");
        }
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/userdelete.do" })
    public String delete(@RequestParam("ids") final String ids, final HttpServletResponse response) throws Exception {
        final JSONObject result = new JSONObject();
        final String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; ++i) {
            this.userService.deleteUser(Integer.parseInt(idsStr[i]));
        }
        result.put((Object)"errres", (Object)true);
        result.put((Object)"errmsg", (Object)"\u6570\u636e\u5220\u9664\u6210\u529f\uff01");
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/changeTheme.do" })
    public String changeTheme(@RequestParam("currentTheme") final String currentTheme, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final JSONObject result = new JSONObject();
        final HttpSession session = request.getSession();
        session.setAttribute("currentTheme", (Object)currentTheme);
        result.put((Object)"errres", (Object)true);
        result.put((Object)"errmsg", (Object)"\u4e3b\u9898\u5207\u6362\u6210\u529f\uff01");
        ResponseUtil.write(response, result);
        return null;
    }
}
