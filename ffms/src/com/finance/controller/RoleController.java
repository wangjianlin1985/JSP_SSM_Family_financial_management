// 
// 
// 

package com.finance.controller;

import java.util.List;
import java.util.Map;
import com.finance.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.finance.util.StringUtil;
import java.util.HashMap;
import com.finance.entity.PageBean;
import javax.servlet.http.HttpServletResponse;
import com.finance.entity.Role;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.ModelMap;
import javax.annotation.Resource;
import com.finance.service.RoleService;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController
{
    @Resource
    private RoleService roleService;
    
    @RequestMapping({ "/roleManage.do" })
    public String roleManage(final ModelMap map) {
        return "roleManage";
    }
    
    @RequestMapping({ "/rolelist.do" })
    public String list(@RequestParam(value = "page", required = false) final String page, @RequestParam(value = "rows", required = false) final String rows, final Role s_role, final HttpServletResponse response) throws Exception {
        final PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("rolename", StringUtil.formatLike(s_role.getRolename()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        final List<Role> roleList = this.roleService.findRole(map);
        final Long total = this.roleService.getTotalRole(map);
        final JSONObject result = new JSONObject();
        final JSONArray jsonArray = JSONArray.fromObject((Object)roleList);
        result.put((Object)"rows", (Object)jsonArray);
        result.put((Object)"total", (Object)total);
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/rolesave.do" })
    public String save(final Role role, final HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        final JSONObject result = new JSONObject();
        if (role.getId() == null) {
            resultTotal = this.roleService.addRole(role);
        }
        else {
            resultTotal = this.roleService.updateRole(role);
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
    
    @RequestMapping({ "/roledelete.do" })
    public String delete(@RequestParam("ids") final String ids, final HttpServletResponse response) throws Exception {
        final JSONObject result = new JSONObject();
        final String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; ++i) {
            this.roleService.deleteRole(Integer.parseInt(idsStr[i]));
        }
        result.put((Object)"errres", (Object)true);
        result.put((Object)"errmsg", (Object)"\u6570\u636e\u5220\u9664\u6210\u529f\uff01");
        ResponseUtil.write(response, result);
        return null;
    }
}
