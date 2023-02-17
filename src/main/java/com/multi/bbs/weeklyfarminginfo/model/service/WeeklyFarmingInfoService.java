package com.multi.bbs.weeklyfarminginfo.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.weeklyfarminginfo.model.mapper.WeeklyFarmingInfoMapper;
import com.multi.bbs.weeklyfarminginfo.model.vo.WeeklyFarmingInfo;

@Service
public class WeeklyFarmingInfoService {

	@Autowired
	private WeeklyFarmingInfoMapper wMapper;
	
	// 게시글 갯수 	
	public int getWeeklyFarmingInfoCount(Map<String, String> param) {	// Controller.java와 일치
		return wMapper.selectWeeklyFarmingInfoCount(param);	// mapper.xml에 선언했던 id와 mapper.java와 일치 
	}

	// 게시글 목록 출력
	public List<WeeklyFarmingInfo> getWeeklyFarimgInfoList (PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return wMapper.selectWeeklyFarimgInfoList(param);
	}

}



//@Service
//public class BoardService {
//	@Autowired
//	private BoardMapper mapper;
//	
//	@Transactional(rollbackFor = Exception.class)
//	public int saveBoard(Board board) {
//		int result = 0;
//		if(board.getBNo() == 0) {
//			result = mapper.insertBoard(board);
//		}else {
//			result = mapper.updateBoard(board);
//		}
//		return result;
//	}
//	
//	@Transactional(rollbackFor = Exception.class)
//	public int saveReply(Reply reply) {
//		return mapper.insertReply(reply);
//	}
//	
//	
//	public String saveFile(MultipartFile upFile, String savePath) {
//		File folder = new File(savePath);
//		
//		// 폴더 없으면 만드는 코드
//		if(folder.exists() == false) {
//			folder.mkdir();
//		}
//		System.out.println("savePath : " + savePath);
//		
//		// 파일이름을 랜덤하게 바꾸는 코드, test.txt -> 20221213_1728291212.txt
//		String originalFileName = upFile.getOriginalFilename();
//		String reNameFileName = 
//					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
//		reNameFileName += originalFileName.substring(originalFileName.lastIndexOf("."));
//		String reNamePath = savePath + "/" + reNameFileName;
//		
//		try {
//			// 실제 파일이 저장되는 코드
//			upFile.transferTo(new File(reNamePath));
//		} catch (Exception e) {
//			return null;
//		}
//		return reNameFileName;
//	}
//	
//	public int getBoardCount(Map<String, String> param) {
//		return mapper.selectBoardCount(param);
//	}
//	
//	public List<Board> getBoardList(PageInfo pageInfo, Map<String, String> param){
//		param.put("limit", "" + pageInfo.getListLimit());
//		param.put("offset", "" + (pageInfo.getStartList() - 1));
//		return mapper.selectBoardList(param);
//	}
//	
//	@Transactional(rollbackFor = Exception.class)
//	public Board findByNo(int boardNo) {
//		Board board = mapper.selectBoardByNo(boardNo); 
//		board.setReadCount(board.getReadCount() + 1);  
//		mapper.updateReadCount(board);
//		return board; 
//	}
//	
//	public void deleteFile(String filePath) {
//		File file = new File(filePath);
//		if(file.exists()) {
//			file.delete();
//		}
//	}
//	
//	@Transactional(rollbackFor = Exception.class)
//	public int deleteBoard(int no, String rootPath) {
//		Board board = mapper.selectBoardByNo(no);
//		deleteFile(rootPath + "\\" + board.getRenamedFileName());
//		return mapper.deleteBoard(no);
//	}
//	
//	@Transactional(rollbackFor = Exception.class)
//	public int deleteReply(int no) {
//		return mapper.deleteReply(no);
//	}
//	
//}