package com.multi.bbs.index.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multi.bbs.index.model.mapper.IndexMapper;
import com.multi.bbs.index.model.vo.FrmhsTasteHosIndex;
import com.multi.bbs.index.model.vo.PrvateTherpyIndex;
import com.multi.bbs.index.model.vo.TechVideoIndex;
import com.multi.bbs.index.model.vo.TodayFlowerIndex;
import com.multi.bbs.prvateTherpy.model.vo.PrvateTherpy;

@Service
public class IndexService {

	@Autowired
	private IndexMapper iMapper;
	
	// 오늘의 꽃
	public List<TodayFlowerIndex> getTodayFlowerIndexList() {
		return iMapper.selectTodayFlowerIndexList();
	}
	
	// 농가맛집
	public List<FrmhsTasteHosIndex> getFrmhsTasteHosIndexList() {
		return iMapper.selectFrmhsTasteHosIndexList();
	}
	
	// 농업기술 동영상
	public List<TechVideoIndex> getTechVideoIndexList() {
		return iMapper.selectTechVideoIndexList();
	}
	

}
