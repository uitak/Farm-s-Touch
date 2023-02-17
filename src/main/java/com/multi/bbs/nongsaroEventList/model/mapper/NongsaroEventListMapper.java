package com.multi.bbs.nongsaroEventList.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.nongsaroEventList.model.vo.NongsaroEventList;

@Mapper
public interface NongsaroEventListMapper {
	int insertNongsaroEventList(NongsaroEventList e);
	int selectNongsaroEventListCount(Map<String, String> param);
	List<NongsaroEventList> selectNongsaroEventListList(Map<String, String> map);
}
