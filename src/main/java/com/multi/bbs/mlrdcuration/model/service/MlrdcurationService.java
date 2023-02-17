package com.multi.bbs.mlrdcuration.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.mlrdcuration.model.mapper.MlrdcurationMapper;
import com.multi.bbs.mlrdcuration.model.vo.MlrdcurationAnwrList;
import com.multi.bbs.mlrdcuration.model.vo.MlrdcurationList;

@Service
public class MlrdcurationService {

	@Autowired
	private MlrdcurationMapper mMapper;

	// 검색 결과로 나오는 게시글 갯수
	public int getMlrdcurationCount(Map<String, String> param) {
		return mMapper.selectMlrdcurationCount(param);	// mapper.xml에 선언했던 id와 mapper.java와 일치
	}

	// 조회페이지에 뿌려줄 게시글 리스트들 및 페이징 처리
	public List<MlrdcurationList> getMlrdcurationList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mMapper.selectMlrdcurationList(param);
	}
	
	// 상세페이지로 가려면 dataNo에 맞는 놈을 물어다가 가야겠지
	@Transactional(rollbackFor = Exception.class)
	public MlrdcurationList findByNo(String dataNO) {
		MlrdcurationList mlrdcurationList = mMapper.selecMlrdcurationByNo(dataNO);
		return mlrdcurationList;
	}
	
	// 상세페이지에 맞는 댓글들이 들어가야지
	@Transactional(rollbackFor = Exception.class)
	public List<MlrdcurationAnwrList> selectMlrdcurationAnwrList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mMapper.selectMlrdcurationAnwrList(param); 
	}
	
	// 상세페이지 댓글 개수
//	public int getSelectMlrdcurationAnwrListCount(String dataNO) {
//		return mMapper.selectMlrdcurationAnwrListCount(dataNO);
//	}
	
}
