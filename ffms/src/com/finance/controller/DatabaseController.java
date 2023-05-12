// 
// 
// 

package com.finance.controller;

import java.io.IOException;
import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import com.finance.util.DateUtil;
import java.io.File;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import com.finance.util.StringUtil;
import java.util.HashMap;
import com.finance.entity.PageBean;
import com.finance.util.ResponseUtil;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import com.finance.entity.Database;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.InputStream;
import com.finance.core.des.DESUtils;
import com.finance.util.CurrentConn;
import java.util.Properties;
import javax.annotation.Resource;
import com.finance.service.DatabaseService;
import org.springframework.stereotype.Controller;

@Controller
public class DatabaseController
{
    @Resource
    private DatabaseService databaseService;
    private static String username;
    private static String password;
    
    static {
        final Properties prop = new Properties();
        try {
            final InputStream is = CurrentConn.class.getResourceAsStream("/db.properties");
            prop.load(is);
            DatabaseController.username = DESUtils.getDecryptString(prop.getProperty("username"));
            DatabaseController.password = DESUtils.getDecryptString(prop.getProperty("password"));
           
            is.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @RequestMapping({ "/databackManage.do" })
    public String databackManage() {
        return "databackManage";
    }
    
    @RequestMapping({ "/datarecoverManage.do" })
    public String datarecoverManage() {
        return "datarecoverManage";
    }
    
    @RequestMapping({ "/dataorderManage.do" })
    public String dataorderManage() {
        return "dataorderManage";
    }
    
    @RequestMapping({ "/datainitManage.do" })
    public String datainitManage() {
        return "datainitManage";
    }
    
    private Boolean save(final Database database) throws Exception {
        int resultTotal = 0;
        resultTotal = this.databaseService.addDatabase(database);
        boolean result = false;
        if (resultTotal > 0) {
            result = true;
        }
        return result;
    }
    
    @RequestMapping({ "/databasedelete.do" })
    public String delete(@RequestParam("ids") final String ids, final HttpServletResponse response) throws Exception {
        final JSONObject result = new JSONObject();
        final String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; ++i) {
            this.databaseService.deleteDatabase(Integer.parseInt(idsStr[i]));
        }
        result.put((Object)"errres", (Object)true);
        result.put((Object)"errmsg", (Object)"\u8bb0\u5f55\u5220\u9664\u6210\u529f\uff01");
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/databaselist.do" })
    public String list(@RequestParam(value = "dataid", required = true) final Integer dataid, @RequestParam(value = "page", required = false) final String page, @RequestParam(value = "rows", required = false) final String rows, final Database s_databack, final HttpServletResponse response) throws Exception {
        final PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", StringUtil.formatLike(s_databack.getUsername()));
        map.put("starttime", s_databack.getStarttime());
        map.put("endtime", s_databack.getEndtime());
        map.put("dataid", dataid);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        final List<Database> databacklist = this.databaseService.findDataBack(map);
        final Long total = this.databaseService.getDataBackTotal(map);
        final JSONObject result = new JSONObject();
        final JSONArray jsonArray = JSONArray.fromObject((Object)databacklist);
        result.put((Object)"rows", (Object)jsonArray);
        result.put((Object)"total", (Object)total);
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/databack.do" })
    public String databack(@RequestParam(value = "location", required = true) final String basepath, @RequestParam(value = "userid", required = true) final Integer userid, final Database databack, final HttpServletResponse response, final HttpSession session) {
        final JSONObject result = new JSONObject();
        try {
            final Runtime rt = Runtime.getRuntime();
            final Process child = rt.exec("mysqldump -u" + DatabaseController.username + " -p" + DatabaseController.password + " ffms t_datadic t_income t_pay t_role t_security t_shares t_trade t_user t_user_role ");
            final InputStream in = child.getInputStream();
            final InputStreamReader xx = new InputStreamReader(in, "utf8");
            final StringBuffer sb = new StringBuffer("");
            final BufferedReader br = new BufferedReader(xx);
            String inStr;
            while ((inStr = br.readLine()) != null) {
                sb.append(String.valueOf(inStr) + "\r\n");
            }
            final String outStr = sb.toString();
            final String regex = "^[A-z]:\\\\(.+?)";
            final String regex2 = "^[A-z]:\\\\";
            if (basepath.equals("")) {
                result.put((Object)"errres", (Object)false);
                result.put((Object)"errmsg", (Object)"\u5907\u4efd\u8def\u5f84\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            }
            else if (!basepath.matches(regex) && !basepath.matches(regex2)) {
                result.put((Object)"errres", (Object)false);
                result.put((Object)"errmsg", (Object)"\u5907\u4efd\u8def\u5f84\u4e0d\u6b63\u786e\uff01");
            }
            else {
                final File file = new File(basepath);
                if (!file.exists()) {
                    file.mkdir();
                }
                final String filepath = String.valueOf(basepath) + "\\" + DateUtil.getCurrentDateCustomFormat("yyyyMMddHHmmss") + ".sql";
                final File files = new File(filepath);
                if (!files.exists()) {
                    file.createNewFile();
                }
                final FileOutputStream fout = new FileOutputStream(filepath);
                final OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
                writer.write(outStr);
                writer.flush();
                in.close();
                xx.close();
                br.close();
                writer.close();
                fout.close();
                databack.setUserid(userid);
                databack.setFilename(String.valueOf(DateUtil.getCurrentDateCustomFormat("yyyyMMddHHmmss")) + ".sql");
                databack.setTime(DateUtil.getCurrentDateCustomFormat("yyyy-MM-dd HH:mm:ss"));
                databack.setLocation(filepath);
                databack.setDataid(1);
                if (this.save(databack)) {
                    result.put((Object)"errres", (Object)true);
                    result.put((Object)"errmsg", (Object)"\u6570\u636e\u5907\u4efd\u6210\u529f\uff01");
                }
                else {
                    result.put((Object)"errres", (Object)false);
                    result.put((Object)"errmsg", (Object)"\u6570\u636e\u5907\u4efd\u5931\u8d25");
                }
            }
        }
        catch (Exception e) {
            System.out.println("\u5f02\u5e38");
            e.printStackTrace();
            result.put((Object)"errres", (Object)false);
            result.put((Object)"errmsg", (Object)"\u6570\u636e\u5907\u4efd\u5931\u8d25");
        }
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/datarecover.do" })
    public String datarecover(@RequestParam(value = "location", required = true) final String location, @RequestParam(value = "filename", required = true) final String filename, @RequestParam(value = "userid", required = true) final Integer userid, final Database datarecover, final HttpServletResponse response, final HttpSession session) {
        final JSONObject result = new JSONObject();
        try {
            final Runtime rt = Runtime.getRuntime();
            final Process child = rt.exec("mysql -u" + DatabaseController.username + " -p" + DatabaseController.password + " ffms");
            final OutputStream out = child.getOutputStream();
            final StringBuffer sb = new StringBuffer("");
            final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(location), "utf-8"));
            String inStr;
            while ((inStr = br.readLine()) != null) {
                sb.append(String.valueOf(inStr) + "\r\n");
            }
            final String outStr = sb.toString();
            final OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");
            writer.write(outStr);
            writer.flush();
            out.close();
            br.close();
            writer.close();
            datarecover.setUserid(userid);
            datarecover.setFilename(filename);
            datarecover.setTime(DateUtil.getCurrentDateCustomFormat("yyyy-MM-dd HH:mm:ss"));
            datarecover.setLocation(location);
            datarecover.setDataid(2);
            if (this.save(datarecover)) {
                result.put((Object)"errres", (Object)true);
                result.put((Object)"errmsg", (Object)"\u6570\u636e\u6062\u590d\u6210\u529f\uff01");
            }
            else {
                result.put((Object)"errres", (Object)false);
                result.put((Object)"errmsg", (Object)"\u6570\u636e\u6062\u590d\u5931\u8d25");
            }
        }
        catch (Exception e) {
            System.out.println("\u5f02\u5e38");
            e.printStackTrace();
            result.put((Object)"errres", (Object)false);
            result.put((Object)"errmsg", (Object)"\u6570\u636e\u6062\u590d\u5931\u8d25");
        }
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/datainit.do" })
    public String datainit(@RequestParam("userid") final Integer userid, final Database datainit, final HttpServletResponse response) throws Exception {
        final JSONObject result = new JSONObject();
        final String[] tables = { "t_income", "t_pay", "t_security", "t_shares", "t_trade" };
        for (int i = 0; i < tables.length; ++i) {
            this.databaseService.truncateTable(tables[i]);
        }
        datainit.setUserid(userid);
        datainit.setTime(DateUtil.getCurrentDateCustomFormat("yyyy-MM-dd HH:mm:ss"));
        datainit.setDataid(3);
        if (this.save(datainit)) {
            result.put((Object)"errres", (Object)true);
            result.put((Object)"errmsg", (Object)"\u6570\u636e\u5e93\u521d\u59cb\u5316\u6210\u529f\uff01");
        }
        else {
            result.put((Object)"errres", (Object)false);
            result.put((Object)"errmsg", (Object)"\u6570\u636e\u5e93\u521d\u59cb\u5316\u5931\u8d25");
        }
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/dataorder.do" })
    public String dataorder(@RequestParam("userid") final Integer userid, @RequestParam("tablename") final String tablename, @RequestParam("startid") final Integer startid, @RequestParam("endid") final Integer endid, final Database dataorder, final HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        final JSONObject result = new JSONObject();
        resultTotal = this.databaseService.deleteOrderdata(tablename, startid, endid);
        if (resultTotal > 0) {
            dataorder.setUserid(userid);
            dataorder.setTime(DateUtil.getCurrentDateCustomFormat("yyyy-MM-dd HH:mm:ss"));
            dataorder.setDataid(4);
            dataorder.setLocation("\u5220\u9664\u3010" + tablename + "\u3011\u8868\u4e2d\u7b2c" + startid + "\u6761\u5230\u7b2c" + endid + "\u6761\u6570\u636e");
            if (this.save(dataorder)) {
                result.put((Object)"errres", (Object)true);
                result.put((Object)"errmsg", (Object)"\u6570\u636e\u6574\u7406\u5b8c\u6210\uff01");
            }
            else {
                result.put((Object)"errres", (Object)true);
                result.put((Object)"errmsg", (Object)"\u6570\u636e\u6574\u7406\u5931\u8d25");
            }
        }
        else {
            result.put((Object)"errres", (Object)true);
            result.put((Object)"errmsg", (Object)"\u6240\u9009\u6570\u636e\u4e0d\u5b58\u5728\uff01");
        }
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/openFileDialog.do" })
    public String openFileDialog(final HttpServletResponse response) {
        final JSONObject result = new JSONObject();
        try {
            Desktop.getDesktop().open(new File("D:\\360Downloads\\apache-tomcat-8.0.44\\DBback"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        result.put((Object)"errres", (Object)true);
        result.put((Object)"errmsg", (Object)"\u6587\u4ef6\u8d44\u6e90\u7ba1\u7406\u5668\u6253\u5f00\u6210\u529f\uff01");
        ResponseUtil.write(response, result);
        return null;
    }
}
