// 
// 
// 

package com.finance.util;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtil
{
    public static void write(final HttpServletResponse response, final Object o) {
        try {
            response.setContentType("text/html;charset=utf-8");
            final PrintWriter out = response.getWriter();
            out.println(o.toString());
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
