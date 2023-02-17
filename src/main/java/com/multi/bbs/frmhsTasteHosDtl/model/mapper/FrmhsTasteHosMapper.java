package com.multi.bbs.frmhsTasteHosDtl.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.frmhsTasteHosDtl.model.vo.FrmhsTasteHos;

@Mapper
public interface FrmhsTasteHosMapper {
	
	// 조회 페이지
	List<FrmhsTasteHos> selectFrmhsTasteHosList(Map<String, String> map);
	int selectFrmhsTasteHosCount(Map<String, String> map);
	
	// 상세 페이지
	FrmhsTasteHos selectFrmhsTasteHosByNo(String cntntsNo);
	
	// 상세 페이지 다른 맛집 4개 자신 제외 랜덤 추출
	List<FrmhsTasteHos> selectFrmhsTasteHosOtherList(String cntntsNo);
	
	// 상세 페이지 이미지 리스트 추출
	List<FrmhsTasteHos> selectFrmhsTasteHosImgList(String cntntsNo);
	

}
