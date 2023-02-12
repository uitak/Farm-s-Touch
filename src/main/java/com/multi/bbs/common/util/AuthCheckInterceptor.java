package com.multi.bbs.common.util;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object authInfo = session.getAttribute("loginMember");
			if (authInfo != null) {
				return true;
			}
		}
		/*
		request.setAttribute("msg", "로그인 후 이용가능한 서비스입니다.");
		request.setAttribute("location", "/");
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/board/msg");
		requestDispatcher.forward(request, response);
		*/
		
		response.sendRedirect(request.getContextPath() + "/login");
		return false;
	}
}
