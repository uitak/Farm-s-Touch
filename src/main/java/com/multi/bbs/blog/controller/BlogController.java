package com.multi.bbs.blog.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.multi.bbs.blog.model.service.BlogService;
import com.multi.bbs.blog.model.vo.Blog;
import com.multi.bbs.blog.model.vo.Comment;
import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/blog")
@Controller
public class BlogController {

	@Autowired
	private BlogService service;
	
	final static private String serverPath = System.getProperty("user.dir") + "/src/main/resources/static/";
	final static private String savePath = "c:/multicampus/";
	
	@GetMapping("/list")
	public String list(
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam Map<String, String> paramMap, Model model) {

		if (loginMember != null) {
			paramMap.put("mNo", ""+loginMember.getMNo());
		}
		if (paramMap.get("sorted") == null) {
			paramMap.put("sorted", "recent");
		}

		PageInfo pageInfo = new PageInfo(1, 12);
		List<Blog> blogList = service.getBlogList(pageInfo, paramMap);
		for (Blog blog : blogList) {
			if (blog.getThumbnailImgUrl() == null) {
				blog.setThumbnailImgUrl("http://localhost/image/no-image.png");
			}
		}
		model.addAttribute("list", blogList);
		model.addAttribute("paramMap", paramMap);
		
		return "blog/plantBlogList";
	}
	
	@GetMapping("/paging")
	@ResponseBody
	public List<Blog> blogListPaging(@RequestParam Map<String, String> paramMap,
									 HttpSession session) {

		if (session.getAttribute("loginMember") != null) {
			paramMap.put("mNo", ""+((Member)session.getAttribute("loginMember")).getMNo());
		}
		int page = Integer.parseInt(paramMap.get("page"));
		PageInfo pageInfo = new PageInfo(page, 12);

		List<Blog> blogList = service.getBlogList(pageInfo, paramMap);
		for (Blog blog : blogList) {
			if (blog.getThumbnailImgUrl() == null) {
				blog.setThumbnailImgUrl("http://localhost/image/no-image.png");
			}
		}		
		return blogList;
	}
	
	@GetMapping("/view")
	public String view(
			@RequestParam("no") int no,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			Model model) {
		
		int mNo = 0;
		if(loginMember != null) {
			mNo = loginMember.getMNo();
		}
		
		Blog blog = service.findByNo(no, mNo);
		if (blog == null) {
			return "redirect:/error";
		}
		
		model.addAttribute("blog", blog);
		model.addAttribute("commentList", blog.getCommentList());
		return "/blog/plantBlogDetails";
	}
	
	@GetMapping("/error")
	public String error() {
		return "/common/error";
	}
	
	@GetMapping("/write")
	public String writeView() {
		return "/blog/plantBlogWrite";
	}
	
	
	@PostMapping("/uploadImage")
	@ResponseBody
	public List<String> uploadImage(MultipartHttpServletRequest request) throws Exception{
		
		List<String> ret = new ArrayList<String>();
		String repoPath = "http://localhost/blog/images/";
		
		File folder = new File(serverPath, "blog/images/");
		if(folder.exists() == false) {
			folder.mkdirs();
		}
		
		MultipartFile mFile = request.getFile("upload");
		
		String originalFileName = mFile.getOriginalFilename();
		String reNameFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
		reNameFileName += originalFileName.substring(originalFileName.lastIndexOf("."));
		String reNamePath = serverPath + "blog/images/" + reNameFileName;
		
		try {
			mFile.transferTo(new File(reNamePath));
		} catch (Exception e) {
			return null;
		}

		ret.add(""+repoPath + reNameFileName);
		return ret;
	}
	
	@PostMapping("/thumbnail")
	@ResponseBody
	public String uploadThumbnail(@RequestPart("thumbImg") MultipartFile thumbImg, Model model) 
			throws IOException {
		
		File folder = new File(serverPath, "blog/thumbnail/");
		if(folder.exists() == false) {
			folder.mkdirs();
		}
		
		String originalFileName = thumbImg.getOriginalFilename();
		String reNameFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
		reNameFileName += originalFileName.substring(originalFileName.lastIndexOf("."));
		String reNamePath = serverPath + "blog/thumbnail/" + reNameFileName;
		
		try {
			thumbImg.transferTo(new File(reNamePath));
		}
		catch (Exception e) {
			return null;
		}
		
		return "http://localhost/blog/thumbnail/" + reNameFileName;
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public Map<String, Object> uploadBlog(@RequestBody Blog blog, HttpSession session) throws IOException {
		
		Member member = (Member)session.getAttribute("loginMember");
		//String writer = member.getId();
		
		blog.setMNo(member.getMNo());
		int result = service.saveBlog(blog);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		if(result > 0) {
			ret.put("msg", "블로그가 등록 되었습니다.");
			ret.put("location", "/blog/list");
		}else {
			ret.put("msg", "블로그 작성 오류.");
			ret.put("location", "/blog/list");
		}
		
		return ret;
	}
	
	@GetMapping("/update")
	public String updateView(Model model,
			@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			@RequestParam("no") int no
			) {
		Blog blog = service.findByNo(no, loginMember.getMNo());
		model.addAttribute("blog",blog);
		return "/blog/plantBlogUpdate";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Map<String, Object> updateBoard(Model model,
			@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			@RequestBody Blog blog
			) {
		blog.setMNo(loginMember.getMNo());
		
		int result = service.saveBlog(blog);

		Map<String, Object> ret = new HashMap<String, Object>();
		if(result > 0) {
			ret.put("msg", "블로그가 수정 되었습니다.");
			ret.put("location", "/blog/list");
		}else {
			ret.put("msg", "블로그 수정 오류.");
			ret.put("location", "/blog/list");
		}		
		return ret;
	}
	
	@GetMapping("/delete")
	public String deleteBoard(Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			int blogNo
			) {
		int result = service.deleteBlog(blogNo, serverPath);		
		if(result > 0) {
			model.addAttribute("msg", "게시글 삭제가 정상적으로 완료되었습니다.");
		}else {
			model.addAttribute("msg", "게시글 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/blog/list");
		return "/common/msg";
	}
	
	@GetMapping("/like") 
	public ResponseEntity<Integer> BlogLike(
			@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			int bNo, int isLike)
	{
		int result = 0;
		if(isLike == 1) {
			result = service.likeBlog(loginMember.getMNo(), bNo);	
		}else {
			result = service.unLikeBlog(loginMember.getMNo(), bNo);	
		}
		
		if(result > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(isLike);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();	
	}
	
	@PostMapping("/comment")
	public String writeReply(Model model, 
			@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			@ModelAttribute Comment comment
			) {
		comment.setMNo(loginMember.getMNo());
		
		int result = service.saveComment(comment);
		
		if(result > 0) {
			model.addAttribute("msg", "리플이 등록되었습니다.");
		}else {
			model.addAttribute("msg", "리플 등록에 실패하였습니다.");
		}
		model.addAttribute("location", "/blog/view?no="+comment.getBNo());
		return "/common/msg";
	}
	
	@RequestMapping("/commentDel")
	public String deleteReply(Model model, 
			@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			int commentNo, int blogNo
			){
		log.info("리플 삭제 요청");
		int result = service.deleteComment(commentNo);
		
		if(result > 0) {
			model.addAttribute("msg", "리플 삭제가 정상적으로 완료되었습니다.");
		}else {
			model.addAttribute("msg", "리플 삭제에 실패하였습니다.");
		}
		model.addAttribute("location", "/blog/view?no=" + blogNo);
		return "/common/msg";
	}
	
	@GetMapping("/file/{memberId}/{fileName}")
	@ResponseBody
	public Resource memberProfileImage(@PathVariable("memberId") String memberId,
								  	   @PathVariable("fileName") String fileName, 
								  	   Model model) throws IOException {

		return new UrlResource("file:" + savePath + memberId + "/" + fileName);
	}
	
}
