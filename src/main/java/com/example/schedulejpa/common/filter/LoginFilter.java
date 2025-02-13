package com.example.schedulejpa.common.filter;


import com.example.schedulejpa.common.constant.SessionConst;
import com.example.schedulejpa.common.response.ErrorResponse;
import com.example.schedulejpa.member.exception.UnAuthorizedAccessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {
    private static final String[] whitelist = {"/api/v1/members/login", "/api/v1/members/logout", "/api/v1/members/signup"};


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpRequest.getRequestURI();

        log.info("인증 체크 필터 시작 {}", requestURI);
        try {
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    //TODO exception handling
                    throw new UnAuthorizedAccessException();
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (UnAuthorizedAccessException e) {
            handleUnAuthorizedAccess(e, httpResponse);
        }

    }

    private void handleUnAuthorizedAccess(UnAuthorizedAccessException e, HttpServletResponse httpResponse) throws IOException {
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setStatus(e.getStatus().value());

        ObjectMapper objectMapper = new ObjectMapper();
        httpResponse.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(e.getCode(), e.getMessage())));
    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
