package com.multi.bbs.blog.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.multi.bbs.blog.model.vo.Blog;
import com.multi.bbs.blog.model.vo.Comment;

@Mapper
public interface BlogMapper {

	List<Blog> selectBlogList(Map<String, String> map);
	int selectBlogCount();
	Blog selectBlogByNo(Map<String, String> map);
	int insertBlog(Blog board);
	int insertComment(Comment reply);
	int updateBlog(Blog board);
	int updateReadCount(Blog board);
	int deleteBlog(int no);
	int deleteComment(int no);
	int likeBlog(Map<String, String> map);
	int unLikeBlog(Map<String, String> map);
}
