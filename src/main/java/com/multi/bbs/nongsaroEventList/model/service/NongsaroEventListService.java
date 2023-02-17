package com.multi.bbs.nongsaroEventList.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.nongsaroEventList.model.mapper.NongsaroEventListMapper;
import com.multi.bbs.nongsaroEventList.model.vo.NongsaroEventList;

@Service
public class NongsaroEventListService {
	@Autowired
	private NongsaroEventListMapper mapper;

	public int getNongsaroEventListCount(Map<String, String> param) {
		return mapper.selectNongsaroEventListCount(param);
	}
	
	public List<NongsaroEventList> getNongsaroEventListList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", ""+pageInfo.getListLimit());
		param.put("offset", ""+(pageInfo.getStartList() - 1));
		return mapper.selectNongsaroEventListList(param);
	}
	
	
	
}




