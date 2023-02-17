package com.multi.bbs.agriculture.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.bbs.agriculture.model.mapper.VarietyMapper;
import com.multi.bbs.agriculture.model.vo.Variety;
import com.multi.bbs.common.util.PageInfo;

@Service
public class VarietyService {
	
	@Autowired
	private VarietyMapper mapper;
	
	public int getVarietyCount(Map<String, String> param) {
		return mapper.selectVarietyCount(param);
	}
	
	public List<Variety> getVarietyList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectVarietyList(param);
	}
	
	public Variety findByNo(String cntntsNo) {
		Variety variety = mapper.selectVarietyByNo(cntntsNo);
	
		return variety;
	}
}
