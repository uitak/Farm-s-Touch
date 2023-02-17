package com.multi.bbs.fmlgEdcFarmm.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.fmlgEdcFarmm.model.vo.FmlgEdcFarmm;

@Mapper
public interface FmlgEdcFarmmMapper {
	int insertfmlgEdcFarmmDtl(FmlgEdcFarmm f);
	int selectfmlgEdcFarmmDtlCount(Map<String, String> param);
	List<FmlgEdcFarmm> selectfmlgEdcFarmmDtlList(Map<String, String> map);
	
	// 상세 페이지
	FmlgEdcFarmm selecfmlgEdcFarmmDtlByNo(String cntntsNo);
	
	// 상세 페이지 다른 약초 2개 자신 제외 랜덤 추출
	List<FmlgEdcFarmm> selectfmlgEdcFarmmDtlOtherList(String cntntsNo);

}
