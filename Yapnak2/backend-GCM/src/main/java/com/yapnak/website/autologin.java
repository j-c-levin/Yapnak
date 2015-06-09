package com.yapnak.website;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Joshua on 09/06/2015.
 */
public class autologin implements Filter {

    private String contextPath;

    public void init(FilterConfig fc) throws ServletException {
        contextPath = fc.getServletContext().getContextPath();
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //TODO: Check cookies and put this into index
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            resp.sendRedirect(contextPath + "/index.jsp"); //or page where you want to redirect
        }
        fc.doFilter(request, response);
    }

    public void destroy() {
    }
}
