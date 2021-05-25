package com.naver.member.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.naver.member.api.NaverJoinApi;
import com.naver.member.api.NaverLoginApi;
import com.naver.member.dto.Member;
import com.naver.member.service.NService;

@Controller
public class NController {
	
	private ModelAndView mav = new ModelAndView();
	
	@Autowired
	private NService ns;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private NaverJoinApi naverJoinApi;
	
	@Autowired
	private NaverLoginApi naverLoginApi;
	
	private String apiResult;
	
	Member nmember = new Member();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {

		return "index";
	}
	
	@RequestMapping(value = "/joinform", method = RequestMethod.GET)
	public String Joinform() {

		return "Join";
	}
	
	@RequestMapping(value = "/loginform", method = RequestMethod.GET)
	public String Loginform() {

		return "Login";
	}
	
	// 네이버 아이디로 회원가입 naverjoin
	@RequestMapping(value = "/naverjoin")
	public ModelAndView naverJoin() {
		String naverUrl = naverJoinApi.getAutorizationUrl(session);
		
		mav.addObject("naverUrl", naverUrl);
		mav.setViewName("NaverPass");
		
		return mav;
	}
	
	// 네이버 로그인 성공시 callback호출 메소드
	@RequestMapping(value = "/naverjoinok")
	public ModelAndView callbackJoin(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException, ParseException {
		OAuth2AccessToken oauthToken;
		oauthToken = naverJoinApi.getAccessToken(session, code, state);
		
		// 네이버 로그인 된 사용자 정보 호출
		apiResult = naverJoinApi.getUserProfile(oauthToken);
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		
		JSONObject member = (JSONObject) obj;
		JSONObject memberInfo = (JSONObject) member.get("response");
		
		System.out.println(memberInfo);
		
		// memberInfo : 네이버에 가입할 때 등록했던 유저 정보
		nmember.setmId("n"+(String)memberInfo.get("id"));
		nmember.setmName((String)memberInfo.get("name"));
		nmember.setmBirth((String)memberInfo.get("birthyear")+(String)memberInfo.get("birthday"));
		
		mav = ns.nJoin(nmember);
		
		return mav;
	}
	
	// 네이버 로그인 버튼을 눌렀을 경우 naverlogin
	@RequestMapping(value = "/naverlogin")
	public ModelAndView naverLogin() {
		String naverUrl = naverLoginApi.getAutorizationUrl(session);
		
		mav.addObject("naverUrl", naverUrl);
		mav.setViewName("NaverPass");
		
		return mav;
	}
	
	// 네이버 로그인 성공시 callback호출 메소드
		@RequestMapping(value = "/naverloginok")
		public ModelAndView callbackLogin(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException, ParseException {
			OAuth2AccessToken oauthToken;
			oauthToken = naverLoginApi.getAccessToken(session, code, state);
			
			// 네이버 로그인 된 사용자 정보 호출
			apiResult = naverJoinApi.getUserProfile(oauthToken);
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(apiResult);
			
			JSONObject member = (JSONObject) obj;
			JSONObject memberInfo = (JSONObject) member.get("response");
			
			System.out.println(memberInfo);
			
			String mId = "n"+(String)memberInfo.get("id");
			
			mav = ns.nLogin(mId);

			return mav;
		}
	
}
