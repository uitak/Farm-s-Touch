package com.multi.bbs.prvateTherpy.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.prvateTherpy.model.mapper.PrvateTherpyMapper;
import com.multi.bbs.prvateTherpy.model.vo.PrvateTherpy;

@Service
public class PrvateTherpyService {
	
	@Autowired
	private PrvateTherpyMapper pMapper;

	// 검색 결과로 나오는 약초 갯수
	public int getPrvateTherpyCount(Map<String, String> param) {
		return pMapper.selectPrvateTherpyCount(param);	// mapper.xml에 선언했던 id와 mapper.java와 일치
	}

	// 조회페이지에 뿌려줄 약초 리스트들 및 페이징 처리
	public List<PrvateTherpy> getPrvateTherpyList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return pMapper.selectPrvateTherpyList(param);
	}

	// 상세페이지 접속을 위한 cnNo 찾기
	@Transactional(rollbackFor = Exception.class)
	public PrvateTherpy findByNo(String cntntsNo) {
		PrvateTherpy prvateTherpy = pMapper.selecPrvateTherpyByNo(cntntsNo); 
		return prvateTherpy; 
	}

	// 상세페이지 자기 자신 제외 랜덤 1개 약초 추출
	public List<PrvateTherpy> getPrvateTherpyOtherList(String cntntsNo) {
		return pMapper.selectPrvateTherpyOtherList(cntntsNo);
	}

}
