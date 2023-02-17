package com.multi.bbs.agriculture.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.agriculture.model.vo.Variety;

@Mapper
public interface VarietyMapper {
	List<Variety> selectVarietyList(Map<String, String> map);
	int selectVarietyCount(Map<String, String> map);
	Variety selectVarietyByNo(String cntntsNo);
}
