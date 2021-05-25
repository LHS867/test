package com.naver.member.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.naver.member.dao.NDAO;
import com.naver.member.dto.Member;

@Service
public class NService {
	
	@Autowired
	private NDAO ndao;
	
	@Autowired
	HttpSession session;

	private ModelAndView mav = new ModelAndView();
	
	public ModelAndView nJoin(Member nmember) {
		
		int result = ndao.nJoin(nmember);
		
		if(result>0) {
			mav.setViewName("Login");
		} else {
			mav.setViewName("Join");
		}
		
		return mav;
	}

	public ModelAndView nLogin(String mId) {
		
		String loginName = ndao.nLoginId(mId);
		
		session.setAttribute("loginName", loginName);
		
		mav.setViewName("index");
		
		return mav;
	}


}
