package com.multi.bbs.safety.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.agriTechVideo.model.vo.AgriTechVideo;
import com.multi.bbs.safety.model.vo.Safety;

@Mapper
public interface SafetyMapper {
	List<Safety> selectSafetyList(Map<String, String> map);
	int selectSafetyCount(Map<String, String> map);
	

}



