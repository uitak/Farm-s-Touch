package com.multi.bbs.index.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.index.model.vo.FrmhsTasteHosIndex;
import com.multi.bbs.index.model.vo.PrvateTherpyIndex;
import com.multi.bbs.index.model.vo.TechVideoIndex;
import com.multi.bbs.index.model.vo.TodayFlowerIndex;

@Mapper
public interface IndexMapper {
	
	// 오늘의 꽃
	List<TodayFlowerIndex> selectTodayFlowerIndexList();
	
	// 농가맛집
	List<FrmhsTasteHosIndex> selectFrmhsTasteHosIndexList();
	
	// 농업기술 동영상
	List<TechVideoIndex> selectTechVideoIndexList();
	

	
	

}
