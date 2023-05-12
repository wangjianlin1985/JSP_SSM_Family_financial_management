// 
// 
// 

package com.finance.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import com.finance.entity.User;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter
{
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String[] notFilter = { "/sign.do", "/index.do", "/gosign.do", "/login.do" };
        final String uri = request.getRequestURI();
        if (uri.indexOf(".do") == -1 && uri.indexOf(".jsp") == -1) {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        boolean doFilter = true;
        String[] array;
        for (int length = (array = notFilter).length, i = 0; i < length; ++i) {
            final String s = array[i];
            if (uri.indexOf(s) != -1) {
                doFilter = false;
                break;
            }
        }
        if (!doFilter) {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
            return;
        }
        final User user = (User)request.getSession().getAttribute("currentUser");
        String loginPage = "";
        if (user == null) {
            if (request.getServerPort() == 80) {
                loginPage = String.valueOf(request.getScheme()) + "://" + request.getServerName() + request.getContextPath() + "/index.do";
            }
            else {
                loginPage = String.valueOf(request.getScheme()) + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/index.do";
            }
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            final PrintWriter out = response.getWriter();
            final StringBuilder builder = new StringBuilder();
            builder.append("<!doctype html><html><head><meta charset=\"UTF-8\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><meta http-equiv=\"X-UA-Compatible\" content=\"chrome=1\"/>");
            builder.append("<script type=\"text/javascript\">");
            builder.append("alert('\u672a\u767b\u5f55\uff0c\u8bf7\u767b\u5f55\u540e\u64cd\u4f5c\uff01');");
            builder.append("window.top.location.href='");
            builder.append(loginPage);
            builder.append("';");
            builder.append("</script>");
            builder.append("</html>");
            out.print(builder.toString());
        }
        else {
            filterChain.doFilter((ServletRequest)request, (ServletResponse)response);
        }
    }
}
