package com.onyshkiv.filter;

import com.onyshkiv.entity.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    Map<Role, List<String>> map = new HashMap<>();
    List<String> unregister = List.of("signup", "signin", "bookpage");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        map.put(new Role(1), List.of("signup", "signin", "signout", "userbooks", "bookpage", "addbook", "editprofile", "changepassword"));
        map.put(new Role(2), List.of("signup", "signin", "signout", "bookpage", "editprofile", "changepassword", "getorders", "givebook", "signup", "getusersbook", "userinfo", "givebookback"));
        map.put(new Role(3), List.of("signup", "signin", "signout", "bookpage", "editprofile", "changepassword", "getlibrarians", "getreaders", "deleteuser", "userinfo", "changeuserstatus", "addbookpage", "createbook", "authandpub", "createauthor", "createpublication", "renameauthor", "renamepublication", "editbookpage", "editbook","deletebook"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        Integer roleId = (Integer) session.getAttribute("user_role");
        String action = httpServletRequest.getParameter("action");
        if (accessAllowed(action,roleId)) {
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(httpServletRequest.getContextPath()+"/");
        }



    }
    private boolean accessAllowed(String action, Integer roleId){
        if (action == null) {
            return true;
        }
        action= action.toLowerCase();
        if (roleId == null) {
            return unregister.contains(action);
        }
        Role userRole = new Role(roleId);
        return map.get(userRole).contains(action);
    }
}
