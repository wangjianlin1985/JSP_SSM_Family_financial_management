// 
// 
// 

package com.finance.controller;

import com.finance.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.finance.util.StringUtil;
import com.finance.entity.PageBean;
import javax.servlet.http.HttpServletResponse;
import com.finance.entity.Shares;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import javax.servlet.http.HttpSession;
import com.finance.entity.Security;
import java.util.List;
import java.util.HashMap;
import com.finance.entity.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.InitBinder;
import java.beans.PropertyEditor;
import java.text.DateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.WebDataBinder;
import com.finance.service.SecurityService;
import com.finance.service.UserService;
import javax.annotation.Resource;
import com.finance.service.SharesService;
import org.springframework.stereotype.Controller;

@Controller
public class SharesController
{
    @Resource
    private SharesService sharesService;
    @Resource
    private UserService userService;
    @Resource
    private SecurityService securityService;
    
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor((Class)Date.class, (PropertyEditor)new CustomDateEditor((DateFormat)dateFormat, true));
    }
    
    @RequestMapping({ "/sharesManage.do" })
    public String payManage(final ModelMap map, final HttpServletRequest request) {
        final List<Security> list = this.securityService.getAllSecurity();
        map.addAttribute("allSecuritys", (Object)list);
        final HttpSession session = request.getSession();
        final User curuser = (User)session.getAttribute("currentUser");
        final Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("userid", curuser.getId());
        userMap.put("roleid", curuser.getRoleid());
        final List<User> userlist = this.userService.getAllUser(userMap);
        map.addAttribute("allUsers", (Object)userlist);
        return "sharesManage";
    }
    
    @RequestMapping({ "/shareslist.do" })
    public String list(@RequestParam(value = "page", required = false) final String page, @RequestParam(value = "rows", required = false) final String rows, final Shares s_shares, final HttpServletResponse response) throws Exception {
        final PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("securityname", StringUtil.formatLike(s_shares.getSecurityname()));
        map.put("sharesname", StringUtil.formatLike(s_shares.getSharesname()));
        map.put("holder", StringUtil.formatLike(s_shares.getHolder()));
        map.put("roleid", s_shares.getRoleid());
        map.put("userid", s_shares.getUserid());
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        final List<Shares> sharesList = this.sharesService.findShares(map);
        final Long total = this.sharesService.getTotalShares(map);
        final JSONObject result = new JSONObject();
        final JSONArray jsonArray = JSONArray.fromObject((Object)sharesList);
        result.put((Object)"rows", (Object)jsonArray);
        result.put((Object)"total", (Object)total);
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/sharessave.do" })
    public String save(final Shares shares, final HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        final JSONObject result = new JSONObject();
        if (shares.getId() == null) {
            resultTotal = this.sharesService.addShares(shares);
        }
        else {
            resultTotal = this.sharesService.updateShares(shares);
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
    
    @RequestMapping({ "/sharesdelete.do" })
    public String delete(@RequestParam("ids") final String ids, final HttpServletResponse response) throws Exception {
        final JSONObject result = new JSONObject();
        final String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; ++i) {
            this.sharesService.deleteShares(Integer.parseInt(idsStr[i]));
        }
        result.put((Object)"errres", (Object)true);
        result.put((Object)"errmsg", (Object)"\u6570\u636e\u5220\u9664\u6210\u529f\uff01");
        ResponseUtil.write(response, result);
        return null;
    }
}
