package com.multi.bbs.frmhsTasteHosDtl.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.frmhsTasteHosDtl.model.mapper.FrmhsTasteHosMapper;
import com.multi.bbs.frmhsTasteHosDtl.model.vo.FrmhsTasteHos;

@Service
public class FrmhsTasteHosService {
	
	@Autowired
	private FrmhsTasteHosMapper fMapper;

	// 검색 결과로 나오는 맛집 갯수
	public int getFrmhsTasteHosCount(Map<String, String> param) {
		return fMapper.selectFrmhsTasteHosCount(param);	// mapper.xml에 선언했던 id와 mapper.java와 일치
	}

	// 조회페이지에 뿌려줄 맛집 리스트들 및 페이징 처리
	public List<FrmhsTasteHos> getFrmhsTasteHosList(PageInfo pageInfo, Map<String, String> param) {
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return fMapper.selectFrmhsTasteHosList(param);
	}
	
	// 상세페이지 접속을 위한 cnNo 찾기
	@Transactional(rollbackFor = Exception.class)
	public FrmhsTasteHos findByNo(String cntntsNo) {
		FrmhsTasteHos frmhsTasteHos = fMapper.selectFrmhsTasteHosByNo(cntntsNo); 
		return frmhsTasteHos; 
	}

	// 상세페이지 자기 자신 제외 랜덤 4개 맛집 추출
	public List<FrmhsTasteHos> getFrmhsTasteHosOtherList(String cntntsNo) {
		return fMapper.selectFrmhsTasteHosOtherList(cntntsNo);
	}
	
	public List<FrmhsTasteHos> getFrmhsTasteHosImgList(String cntntsNo) {
		return fMapper.selectFrmhsTasteHosImgList(cntntsNo);
	}
	

}
