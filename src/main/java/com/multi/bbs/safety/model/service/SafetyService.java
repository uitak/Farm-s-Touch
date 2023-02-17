package com.multi.bbs.safety.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.safety.model.mapper.SafetyMapper;
import com.multi.bbs.safety.model.vo.Safety;

@Service
public class SafetyService {
	@Autowired
	private SafetyMapper smapper;
	
	// 검색 결과 나오는 갯수
	public int getSafetyCount(Map<String, String> param) {
		return smapper.selectSafetyCount(param);
	}
	
	
//	@Transactional(rollbackFor = Exception.class)
	public List<Safety> getSafetyList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", ""+pageInfo.getListLimit());	
		param.put("offset", ""+ (pageInfo.getStartList() -1));
		return smapper.selectSafetyList(param); 
	}
	
}




