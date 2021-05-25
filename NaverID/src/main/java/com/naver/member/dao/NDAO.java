package com.naver.member.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.member.dto.Member;

@Repository
public class NDAO {
	@Autowired
	private SqlSessionTemplate sql;

	public int nJoin(Member nmember) {
		
		return sql.insert("Naver.nJoin", nmember);
	}

	public String nLoginId(String mId) {
		
		return sql.selectOne("Naver.nlogin", mId);
	}
}
