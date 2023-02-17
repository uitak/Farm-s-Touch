package com.multi.bbs.todayflower.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.todayflower.model.vo.TodayFlower;

@Mapper
public interface TodayFlowerMapper {
	
	// 조회 페이지
	List<TodayFlower> selectTodayFlowerList(Map<String, String> map);
	int selectTodayFlowerCount(Map<String, String> map);
	
	// 상세 페이지
	TodayFlower selectTodayFlowerByNo(String dataNo);
	
	// 상세 페이지 다른 맛집 4개 자신 제외 랜덤 추출
	List<TodayFlower> selectTodayFlowerOtherList(String dataNo);
	
	// 상세 페이지 이미지 리스트 추출
	List<TodayFlower> selectTodayFlowerImgList(String dataNo);
	
}
