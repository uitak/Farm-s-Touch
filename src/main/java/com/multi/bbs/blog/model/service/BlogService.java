package com.multi.bbs.blog.model.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.multi.bbs.blog.model.mapper.BlogMapper;
import com.multi.bbs.blog.model.vo.Blog;
import com.multi.bbs.blog.model.vo.Comment;
import com.multi.bbs.common.util.PageInfo;

@Service
public class BlogService {

	@Autowired
	private BlogMapper mapper;
	
	@Transactional(rollbackFor = Exception.class)
	public int saveBlog(Blog blog) {
		int result = 0;
		if(blog.getBNo() == 0) {
			result = mapper.insertBlog(blog);
		}else {
			result = mapper.updateBlog(blog);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int saveComment(Comment comment) {
		return mapper.insertComment(comment);
	}
	
	
	public String saveFile(MultipartFile upFile, String savePath) {
		File folder = new File(savePath);
		
		// 폴더 없으면 생성
		if(folder.exists() == false) {
			folder.mkdir();
		}
		
		// 파일이름을 랜덤하게 바꾸는 코드, test.txt -> 20221213_1728291212.txt
		String originalFileName = upFile.getOriginalFilename();
		String reNameFileName = 
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
		reNameFileName += originalFileName.substring(originalFileName.lastIndexOf("."));
		String reNamePath = savePath + "/" + reNameFileName;
		
		try {
			// 실제 파일이 저장되는 코드
			upFile.transferTo(new File(reNamePath));
		} catch (Exception e) {
			return null;
		}
		return reNameFileName;
	}
	
	public int getBlogCount() {
		return mapper.selectBlogCount();
	}
	
	public List<Blog> getBlogList(PageInfo pageInfo, Map<String, String> param){
		param.put("limit", "" + pageInfo.getListLimit());
		param.put("offset", "" + (pageInfo.getStartList() - 1));
		return mapper.selectBlogList(param);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Blog findByNo(int no, int mNo) {
		Map<String, String> map = new HashMap<>();
		map.put("bNo", ""+no);
		map.put("mNo", ""+mNo);
		Blog blog = mapper.selectBlogByNo(map); 
		blog.setReadCount(blog.getReadCount() + 1);  
		mapper.updateReadCount(blog);
		return blog; 
	}
	
	public void deleteFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteBlog(int no, String rootPath) {
		Map<String, String> map = new HashMap<>();
		map.put("bNo", ""+no);
		Blog blog = mapper.selectBlogByNo(map);
		if (blog.getThumbnailImgUrl() != null) {
			String fileUrl = blog.getThumbnailImgUrl();
			String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
			deleteFile(rootPath + "blog/thumbnail/" + fileName);
		}
		return mapper.deleteBlog(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteComment(int no) {
		return mapper.deleteComment(no);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int likeBlog(int mNo, int bNo) {
		Map<String, String> map = new HashMap<>();
		map.put("mNo", ""+mNo);
		map.put("bNo", ""+bNo);
		return mapper.likeBlog(map);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int unLikeBlog(int mNo, int bNo) {
		Map<String, String> map = new HashMap<>();
		map.put("mNo", ""+mNo);
		map.put("bNo", ""+bNo);
		return mapper.unLikeBlog(map);
	}
	
}
