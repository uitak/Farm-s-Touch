package com.multi.bbs.mlrdcuration.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.mlrdcuration.model.vo.MlrdcurationAnwrList;
import com.multi.bbs.mlrdcuration.model.vo.MlrdcurationList;

@Mapper
public interface MlrdcurationMapper {

	// 조회 페이지
	List<MlrdcurationList> selectMlrdcurationList(Map<String, String> map);
	int selectMlrdcurationCount(Map<String, String> map);
	
	// 상세 페이지
	MlrdcurationList selecMlrdcurationByNo(String dataNO);
	
	// 상세 댓글
	List<MlrdcurationAnwrList> selectMlrdcurationAnwrList(Map<String, String> map);
//	MlrdcurationAnwrList selectMlrdcurationAnwrByNo(String dataNO);
	
	// 상세 댓글 갯수
//	int selectMlrdcurationAnwrListCount(String dataNO);
}
