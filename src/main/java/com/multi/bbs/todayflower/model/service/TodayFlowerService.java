package com.multi.bbs.todayflower.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.todayflower.model.mapper.TodayFlowerMapper;
import com.multi.bbs.todayflower.model.vo.TodayFlower;

@Service
public class TodayFlowerService {

	@Autowired
	private TodayFlowerMapper fMapper;

	// 검색 결과로 나오는 맛집 갯수
	public int getTodayFlowerCount(Map<String, String> param) {
		return fMapper.selectTodayFlowerCount(param);	// mapper.xml에 선언했던 id와 mapper.java와 일치
	}

	// 조회페이지에 뿌려줄 꽃 리스트들 및 페이징 처리
	public List<TodayFlower> getTodayFlowerList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return fMapper.selectTodayFlowerList(param);
	}
	
	// 상세페이지 접속을 위한 cnNo 찾기
	@Transactional(rollbackFor = Exception.class)
	public TodayFlower findByNo(String dataNo) {
		TodayFlower todayFlower = fMapper.selectTodayFlowerByNo(dataNo); 
		return todayFlower; 
	}

	// 상세페이지 자기 자신 제외 랜덤 4개 맛집 추출
	public List<TodayFlower> getTodayFlowerOtherList(String dataNo) {
		return fMapper.selectTodayFlowerOtherList(dataNo);
	}
	
	public List<TodayFlower> getTodayFlowerImgList(String dataNo) {
		return fMapper.selectTodayFlowerImgList(dataNo);
	}
}
