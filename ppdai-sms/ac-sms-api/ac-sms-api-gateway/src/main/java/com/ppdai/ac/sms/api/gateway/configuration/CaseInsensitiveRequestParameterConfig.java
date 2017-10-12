package com.ppdai.ac.sms.api.gateway.configuration;

import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by kiekiyang on 2017/6/29.
 */
@WebFilter(filterName = "CaseInsensitiveRequestParameter", urlPatterns = "/*")
public class CaseInsensitiveRequestParameterConfig extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(new CaseInsensitiveParameterNameHttpServletRequest(request), response);
    }

    public static class CaseInsensitiveParameterNameHttpServletRequest extends HttpServletRequestWrapper {
        private final LinkedCaseInsensitiveMap<String[]> map = new LinkedCaseInsensitiveMap<>();

        @SuppressWarnings("unchecked")
        public CaseInsensitiveParameterNameHttpServletRequest(HttpServletRequest request) {
            super(request);
            map.putAll(request.getParameterMap());
        }

        @Override
        public String getParameter(String name) {

            String[] array = this.map.get(name);
            if (array != null && array.length > 0)
                return array[0];
            return null;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return Collections.unmodifiableMap(this.map);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(this.map.keySet());
        }

        @Override
        public String[] getParameterValues(String name) {
            return this.map.get(name);
        }

    }
}
