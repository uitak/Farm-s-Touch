package com.multi.bbs.agriTechVideo.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.agriTechVideo.model.vo.AgriTechVideo;

@Mapper
public interface AgriTechVideoMapper {
	List<AgriTechVideo> selectVideoList(Map<String, String> map);
	int selectVideoCount(Map<String, String> map);
//	AgriTechVideo selectVideoByNo(int vidNo);

}



