// 
// 
// 

package com.finance.controller;

import com.finance.entity.Datadic;
import javax.servlet.http.HttpSession;
import com.finance.entity.User;
import javax.servlet.http.HttpServletRequest;
import com.finance.entity.Pay;
import java.util.List;
import java.util.Map;
import com.finance.util.ResponseUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import com.finance.util.StringUtil;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import com.finance.entity.Income;
import org.springframework.web.bind.annotation.RequestMapping;
import com.finance.service.DatadicService;
import com.finance.service.PayService;
import javax.annotation.Resource;
import com.finance.service.IncomeService;
import org.springframework.stereotype.Controller;

@Controller
public class ProduceController
{
    @Resource
    private IncomeService incomeService;
    @Resource
    private PayService payService;
    @Resource
    private DatadicService datadicService;
    
    @RequestMapping({ "/incomeTimeManage.do" })
    public String incomeTimeManage() {
        return "incomeTimeManage";
    }
    
    @RequestMapping({ "/produceIncomeTime.do" })
    public String produceIncomeTime(final Income s_income, final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("incomer", StringUtil.formatLike(s_income.getIncomer()));
        map.put("starttime", s_income.getStarttime());
        map.put("endtime", s_income.getEndtime());
        map.put("roleid", s_income.getRoleid());
        map.put("userid", s_income.getUserid());
        final List<Income> incomeList = this.incomeService.getIncomeLine(map);
        final List<Income> incomers = this.incomeService.getIncomer();
        final JSONArray outerobj = new JSONArray();
        for (int i = 0; i < incomers.size(); ++i) {
            final String curincomer = incomers.get(i).getIncomer();
            final JSONArray incomeArray = new JSONArray();
            for (int j = 0; j < incomeList.size(); ++j) {
                final JSONArray obj = new JSONArray();
                if (incomeList.get(j).getIncomer().equals(curincomer)) {
                    obj.add((Object)incomeList.get(j).getIncometime());
                    obj.add((Object)incomeList.get(j).getMoney());
                    incomeArray.add((Object)obj);
                }
            }
            if (incomeArray.size() > 0) {
                final JSONObject result = new JSONObject();
                result.put((Object)"name", (Object)curincomer);
                result.put((Object)"data", (Object)incomeArray);
                outerobj.add((Object)result);
            }
        }
        ResponseUtil.write(response, outerobj);
        return null;
    }
    
    @RequestMapping({ "/payTimeManage.do" })
    public String payTimeManage() {
        return "payTimeManage";
    }
    
    @RequestMapping({ "/producePayTime.do" })
    public String producePayLine(final Pay s_pay, final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("payer", StringUtil.formatLike(s_pay.getPayer()));
        map.put("starttime", s_pay.getStarttime());
        map.put("endtime", s_pay.getEndtime());
        map.put("roleid", s_pay.getRoleid());
        map.put("userid", s_pay.getUserid());
        final List<Pay> payList = this.payService.getPayLine(map);
        final List<Pay> payers = this.payService.getPayer();
        final JSONArray outerobj = new JSONArray();
        for (int i = 0; i < payers.size(); ++i) {
            final String curpayer = payers.get(i).getPayer();
            final JSONArray payArray = new JSONArray();
            for (int j = 0; j < payList.size(); ++j) {
                final JSONArray obj = new JSONArray();
                if (payList.get(j).getPayer().equals(curpayer)) {
                    obj.add((Object)payList.get(j).getPaytime());
                    obj.add((Object)payList.get(j).getMoney());
                    payArray.add((Object)obj);
                }
            }
            if (payArray.size() > 0) {
                final JSONObject result = new JSONObject();
                result.put((Object)"name", (Object)curpayer);
                result.put((Object)"data", (Object)payArray);
                outerobj.add((Object)result);
            }
        }
        ResponseUtil.write(response, outerobj);
        return null;
    }
    
    @RequestMapping({ "/moneyAnalysis.do" })
    public String moneyAnalysis(final HttpServletRequest request, final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<String, Object>();
        final HttpSession session = request.getSession();
        final User curUser = (User)session.getAttribute("currentUser");
        map.put("roleid", curUser.getRoleid());
        map.put("userid", curUser.getId());
        final List<Income> incomeList = this.incomeService.getIncomeLine(map);
        final List<Pay> payList = this.payService.getPayLine(map);
        final JSONObject result = new JSONObject();
        int totalIncomeMoney = 0;
        int totalPayMoney = 0;
        int totalLostMoney = 0;
        for (int i = 0; i < incomeList.size(); ++i) {
            if (incomeList.get(i).getIncomer().equals(curUser.getUsername())) {
                totalIncomeMoney += incomeList.get(i).getMoney();
            }
        }
        for (int j = 0; j < payList.size(); ++j) {
            if (payList.get(j).getPayer().equals(curUser.getUsername())) {
                totalPayMoney += payList.get(j).getMoney();
            }
        }
        totalLostMoney = totalIncomeMoney - totalPayMoney;
        result.put((Object)"totalIncomeMoney", (Object)totalIncomeMoney);
        result.put((Object)"totalPayMoney", (Object)totalPayMoney);
        result.put((Object)"totalLostMoney", (Object)totalLostMoney);
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/typePieManage.do" })
    public String typePieManage() {
        return "typePieManage";
    }
    
    @RequestMapping({ "/produceIncomeType.do" })
    public String produceIncomeType(final Income s_income, final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("incomer", StringUtil.formatLike(s_income.getIncomer()));
        map.put("starttime", s_income.getStarttime());
        map.put("endtime", s_income.getEndtime());
        map.put("roleid", s_income.getRoleid());
        map.put("userid", s_income.getUserid());
        final List<Income> incomeList = this.incomeService.getIncomeLine(map);
        final List<Datadic> incomeTypes = this.datadicService.getDatadicIncome();
        final JSONArray incomeArray = new JSONArray();
        final JSONObject result = new JSONObject();
        Integer totalIncomeMoney = 0;
        for (int k = 0; k < incomeList.size(); ++k) {
            totalIncomeMoney += incomeList.get(k).getMoney();
        }
        for (int i = 0; i < incomeTypes.size(); ++i) {
            final JSONArray obj = new JSONArray();
            Integer incomeMoney = 0;
            for (int j = 0; j < incomeList.size(); ++j) {
                if (incomeList.get(j).getDataid().equals(incomeTypes.get(i).getId())) {
                    incomeMoney += incomeList.get(j).getMoney();
                }
            }
            obj.add((Object)(String.valueOf(incomeTypes.get(i).getDatadicvalue()) + "\uff1a" + Math.round(10000 * incomeMoney / totalIncomeMoney) / 100.0 + "%"));
            obj.add((Object)incomeMoney);
            incomeArray.add((Object)obj);
        }
        result.put((Object)"name", (Object)"(\u7c7b\u578b\u2014\u2014\u91d1\u989d)\u6536\u5165\u997c\u72b6\u56fe");
        result.put((Object)"data", (Object)incomeArray);
        ResponseUtil.write(response, result);
        return null;
    }
    
    @RequestMapping({ "/producePayType.do" })
    public String producePayType(final Pay s_pay, final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("payer", StringUtil.formatLike(s_pay.getPayer()));
        map.put("starttime", s_pay.getStarttime());
        map.put("endtime", s_pay.getEndtime());
        map.put("roleid", s_pay.getRoleid());
        map.put("userid", s_pay.getUserid());
        final List<Pay> payList = this.payService.getPayLine(map);
        final List<Datadic> payTypes = this.datadicService.getDatadicPay();
        final JSONArray payArray = new JSONArray();
        final JSONObject result = new JSONObject();
        Integer totalPayMoney = 0;
        for (int k = 0; k < payList.size(); ++k) {
            totalPayMoney += payList.get(k).getMoney();
        }
        for (int i = 0; i < payTypes.size(); ++i) {
            final JSONArray obj = new JSONArray();
            Integer payMoney = 0;
            for (int j = 0; j < payList.size(); ++j) {
                if (payList.get(j).getDataid().equals(payTypes.get(i).getId())) {
                    payMoney += payList.get(j).getMoney();
                }
            }
            obj.add((Object)(String.valueOf(payTypes.get(i).getDatadicvalue()) + "\uff1a" + Math.round(10000 * payMoney / totalPayMoney) / 100.0 + "%"));
            obj.add((Object)payMoney);
            payArray.add((Object)obj);
        }
        result.put((Object)"name", (Object)"(\u7c7b\u578b\u2014\u2014\u91d1\u989d)\u652f\u51fa\u997c\u72b6\u56fe");
        result.put((Object)"data", (Object)payArray);
        ResponseUtil.write(response, result);
        return null;
    }
}
