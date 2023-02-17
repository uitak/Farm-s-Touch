package com.multi.bbs.fmlgEdcFarmm.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.fmlgEdcFarmm.model.mapper.FmlgEdcFarmmMapper;
import com.multi.bbs.fmlgEdcFarmm.model.vo.FmlgEdcFarmm;
import com.multi.bbs.prvateTherpy.model.vo.PrvateTherpy;

@Service
public class FmlgEdcFarmmService {
	@Autowired
	private FmlgEdcFarmmMapper mapper;
	
	@Transactional(rollbackFor = Exception.class)
	public int insertfmlgEdcFarmmDtl(FmlgEdcFarmm f) {
		return mapper.insertfmlgEdcFarmmDtl(f);
	}
	
	public int getfmlgEdcFarmmDtCount(Map<String, String> param) {
		return mapper.selectfmlgEdcFarmmDtlCount(param);
	}
	
	public List<FmlgEdcFarmm> getFmlgEdcFarmmList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", ""+pageInfo.getListLimit());
		param.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectfmlgEdcFarmmDtlList(param);
	}
	
	// 상세페이지 접속을 위한 cnNo 찾기
	@Transactional(rollbackFor = Exception.class)
	public FmlgEdcFarmm findByNo(String cntntsNo) {
		FmlgEdcFarmm fmlgEdcFarmm = mapper.selecfmlgEdcFarmmDtlByNo(cntntsNo); 
		return fmlgEdcFarmm; 
	}
	
	// 상세페이지 자기 자신 제외 랜덤 1개 약초 추출
	public List<FmlgEdcFarmm> getfmlgEdcFarmmDtlOtherList(String cntntsNo) {
		return mapper.selectfmlgEdcFarmmDtlOtherList(cntntsNo);
	}
	
}
