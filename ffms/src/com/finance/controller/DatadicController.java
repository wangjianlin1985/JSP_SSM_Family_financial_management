// 
// 
// 

package com.finance.controller;

import java.util.Map;
import com.finance.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.finance.util.StringUtil;
import java.util.HashMap;
import com.finance.entity.PageBean;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import com.finance.entity.Datadic;
import java.util.List;
import org.springframework.ui.ModelMap;
import javax.annotation.Resource;
import com.finance.service.DatadicService;
import org.springframework.stereotype.Controller;

@Controller
public class DatadicController
{
    @Resource
    private DatadicService datadicService;
    
    @RequestMapping({ "/datadicManage.do" })
    public String datadicManage(final ModelMap map) {
        final List<Datadic> list = this.datadicService.getDatadicname();
        map.addAttribute("datadicnames", (Object)list);
        return "datadicManage";
    }
    
    @RequestMapping({ "/datadiclist.do" })
    public String list(@RequestParam(value = "page", required = false) final String page, @RequestParam(value = "rows", required = false) final String rows, final Datadic s_datadic, final HttpServletResponse response) throws Exception {
        final PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("datadicname", s_datadic.getDatadicname());
        map.put("datadicvalue", StringUtil.formatLike(s_datadic.getDatadicvalue()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        final List<Datadic> datadicList = this.datadicService.findDatadic(map);
        final Long total = this.datadicService.getTotalDatadic(map);
        final JSONObject result = new JSONObject();
        final JSONArray jsonArray = JSONArray.fromObject((Object)datadicList);
        result.put((Object)"rows", (Object)jsonArray);
        result.put((Object)"total", (Object)total);
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/datadicsave.do" })
    public String save(final Datadic datadic, final HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        final JSONObject result = new JSONObject();
        if (datadic.getId() == null) {
            resultTotal = this.datadicService.addDatadic(datadic);
        }
        else {
            resultTotal = this.datadicService.updateDatadic(datadic);
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
    
    @RequestMapping({ "/datadicdelete.do" })
    public String delete(@RequestParam("ids") final String ids, final HttpServletResponse response) throws Exception {
        final JSONObject result = new JSONObject();
        final String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; ++i) {
            this.datadicService.deleteDatadic(Integer.parseInt(idsStr[i]));
        }
        result.put((Object)"errres", (Object)true);
        result.put((Object)"errmsg", (Object)"\u6570\u636e\u5220\u9664\u6210\u529f\uff01");
        ResponseUtil.write(response, result);
        return null;
    }
}
