package com.multi.bbs.agriTechVideo.model.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.multi.bbs.agriTechVideo.model.mapper.AgriTechVideoMapper;
import com.multi.bbs.agriTechVideo.model.vo.AgriTechVideo;
import com.multi.bbs.common.util.PageInfo;

@Service
public class AgriTechVideoService {
	@Autowired
	private AgriTechVideoMapper mapper;
	
	// 검색 결과 나오는 갯수
	public int getVideoCount(Map<String, String> param) {
		return mapper.selectVideoCount(param);
	}
	
	
//	@Transactional(rollbackFor = Exception.class)
	public List<AgriTechVideo> getVideoList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", ""+pageInfo.getListLimit());	
		param.put("offset", ""+ (pageInfo.getStartList() -1));
		return mapper.selectVideoList(param); 
	}
	
//	@Transactional(rollbackFor = Exception.class)
//	public AgriTechVideo getVideoByNo(int vidNo) {
//		return mapper.selectVideoByNo(vidNo);
//	}
	
	
}




