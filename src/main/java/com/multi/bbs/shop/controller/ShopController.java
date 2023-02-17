package com.multi.bbs.shop.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.multi.bbs.api.naver.NaverSearchAPI;
import com.multi.bbs.common.util.PageInfo;
import com.multi.bbs.member.model.vo.Member;
import com.multi.bbs.shop.model.service.ShopService;
import com.multi.bbs.shop.model.vo.Cart;
import com.multi.bbs.shop.model.vo.Product;
import com.multi.bbs.shop.model.vo.ProductReply;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ShopController {
	
	@Autowired
	public ShopService shopService;

	@GetMapping("/shop/shoppingList")
	public String mainPage(Model model,
			@RequestParam Map<String, Object> paramMap, 
			@RequestParam(required = false) List<String> kinds,
			@RequestParam(required = false) List<String> brands
			) {
		
		if(paramMap.get("init") != null) {
			int result = initDB();
			model.addAttribute("msg", "초기화 결과 : " + result);
			model.addAttribute("location", "/");
			return "/common/msg";
		}
		
		int page = 1;
		
		System.out.println(page);
		System.out.println(paramMap);
		System.out.println(kinds);
		System.out.println(brands);
		
		if(paramMap.get("page") != null) {
			page = Integer.parseInt((String) paramMap.get("page"));
		}
		paramMap.put("kinds", kinds);
		paramMap.put("brands", brands);
		
		int count = shopService.getProductCount(paramMap);
		PageInfo pageInfo = new PageInfo(page, 10, count, 9);
		List<Product> list = shopService.getProductList(pageInfo, paramMap);
		
		if(kinds == null) {
			kinds = new ArrayList<>();
		}
		if(brands == null ) {
			brands = new ArrayList<>();
		}
		model.addAttribute("kinds", kinds);
		model.addAttribute("brands", brands);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("paramMap", paramMap);
		
//		System.out.println(list);
		System.out.println(paramMap);
		
		return "/shop/shoppingList";
	}
	
	
	static int pageCount=2;
	
	@GetMapping("/shop/shoppingDetails")
	public String productView(Model model, int pno) {
		Product product = shopService.getProductById(pno);
		List<ProductReply> replyList = shopService.getProductReplyList(pno);
		
		// 대표상품 4개, - 농업, 식물
		Map<String, Object> map = new HashMap<>();
		map.put("title", "농업");
		int count = shopService.getProductCount(map);
		PageInfo pageInfo = new PageInfo(pageCount, 10, count, 1);
		List<Product> plist1 = shopService.getProductList(pageInfo, map);
		
		map.put("title", "식물");
		count = shopService.getProductCount(map);
//		System.out.println("count : " + count);
		pageInfo = new PageInfo(pageCount, 10, count, 10);
		List<Product> plist2 = shopService.getProductList(pageInfo, map);
		plist1.addAll(plist2);
		Collections.shuffle(plist1);
		model.addAttribute("plist1", plist1);
		
		
		model.addAttribute("product", product);
		model.addAttribute("plist1", plist1);
		model.addAttribute("replyList", replyList);
		return "/shop/shoppingDetails";
	}
	
	
	@PostMapping("/shop/writeReply")
	public String writeReply(Model model, ProductReply reply, HttpSession session) {
		Member member = (Member) session.getAttribute("loginMember");
		if(member == null) {
			model.addAttribute("msg", "로그인이 필요합니다.");
			model.addAttribute("location", "/shop/shoppingDetails?pno=" + reply.getPno());
			return "common/msg";
		}
		
		reply.setMno(member.getMNo());
		System.out.println(reply);
		int result = shopService.insertProductReply(reply);
		shopService.updateProductAVG(reply.getPno());
		
		if(result > 0) {
			model.addAttribute("msg", "댓글이 성공적으로 작성되었습니다.");
			model.addAttribute("location", "/shop/shoppingDetails?pno=" + reply.getPno());
		}else {
			model.addAttribute("msg", "댓글 작성에 실패하였습니다.");
			model.addAttribute("location", "/shop/shoppingDetails?pno=" + reply.getPno());
		}
		return "common/msg";
	}
	
	@PostMapping("/shop/deleteReply")
	public String writeReply(Model model, int rno, int pno, HttpSession session) {
		Member member = (Member) session.getAttribute("loginMember");
		if(member == null) {
			model.addAttribute("msg", "로그인이 필요합니다.");
			model.addAttribute("location", "/shop/shoppingDetails?pno=" + pno);
			return "common/msg";
		}
		int result = shopService.deleteProductReply(rno);
		if(result > 0) {
			model.addAttribute("msg", "댓글이 삭제 되었습니다.");
			model.addAttribute("location", "/shop/shoppingDetails?pno=" + pno);
		}else {
			model.addAttribute("msg", "댓글 삭제에 실패하였습니다.");
			model.addAttribute("location", "/shop/shoppingDetails?pno=" + pno);
		}
		return "common/msg";
	}
	
	
	private int initDB() {
		int result = 0;
		List<Product> plist = new ArrayList<>();
		plist.addAll(NaverSearchAPI.getProductList("농업", 50, 1));
		plist.addAll(NaverSearchAPI.getProductList("식물", 50, 1));
		plist.addAll(NaverSearchAPI.getProductList("농사", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("농기계", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("농기구", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("비료", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("농약", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("트랙터", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("화분", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("플랜테리어", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("씨앗", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("식물 영양제", 20, 1));
		plist.addAll(NaverSearchAPI.getProductList("식물 살충제", 20, 1));
		Collections.shuffle(plist);
		
		for(Product p : plist) {
			int result2 = shopService.insertProduct(p);
			result += result2;
		}
		
		return result;
	}
	
	@GetMapping("/shop/shoppingBasket")
	public String cartView(Model model, HttpSession session) {
		Member member = (Member) session.getAttribute("loginMember");
		if(member == null) {
			model.addAttribute("msg", "로그인이 필요합니다.");
			model.addAttribute("location", "/");
			return "common/msg";
		}
		
		List<Product> list = shopService.getCartProductList(member.getMNo());
		model.addAttribute("list", list);
		return "shop/shoppingBasket";
	}
	
	// 장바구니 기능은 AJAX로 구현할것!! 아래 임시 코드나 급할때만 사용하세요.
	@RequestMapping("/shop/addCart")
	String addCart(Model model, HttpSession session, int pno) {
		Member member = (Member) session.getAttribute("loginMember");
		if(member == null) {
			model.addAttribute("msg", "로그인이 필요합니다.");
			model.addAttribute("location", "/");
			return "common/msg";
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("pno", ""+pno);
		map.put("mno", ""+member.getMNo());
		
		int result = 0;
		List<Cart> list = shopService.getCart(map);
		if(list == null || list.size() == 0) {
			Cart cart = new Cart(member.getMNo(), pno, 1);
			result = shopService.insertCart(cart);
		}else {
			Cart cart = list.get(0);
			cart.setAmount(cart.getAmount() + 1);
			result = shopService.updateCart(cart);
		}
		
		if(result >0) {
			model.addAttribute("msg", "장바구니에 담겼습니다.");
			model.addAttribute("location", "/shop/shoppingList");
		} else {
			model.addAttribute("msg", "장바구니 담기에 실패하였습니다.");
			model.addAttribute("location", "/shop/shoppingList");
		}
		return "common/msg";
	}
	 

}
