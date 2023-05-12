// 
// 
// 

package com.finance.exception;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class ExceptionHandler implements HandlerExceptionResolver
{
    public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
        if (ex instanceof NumberFormatException) {
            return new ModelAndView("exception/number");
        }
        if (ex instanceof NullPointerException) {
            return new ModelAndView("exception/null");
        }
        if (!(ex instanceof SocketTimeoutException)) {
            if (!(ex instanceof ConnectException)) {
                final ModelAndView mv = new ModelAndView("redirect:/page-front/errors.html");
                return mv;
            }
        }
        try {
            response.getWriter().write("\u7f51\u7edc\u5f02\u5e38");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("exception/net_error");
    }
}
