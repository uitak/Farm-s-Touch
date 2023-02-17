package com.multi.bbs.prvateTherpy.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.prvateTherpy.model.vo.PrvateTherpy;

@Mapper
public interface PrvateTherpyMapper {
	
	// 조회 페이지
	List<PrvateTherpy> selectPrvateTherpyList(Map<String, String> map);
	int selectPrvateTherpyCount(Map<String, String> map);
	
	// 상세 페이지
	PrvateTherpy selecPrvateTherpyByNo(String cntntsNo);
	
	// 상세 페이지 다른 약초 2개 자신 제외 랜덤 추출
	List<PrvateTherpy> selectPrvateTherpyOtherList(String cntntsNo);
	
}
