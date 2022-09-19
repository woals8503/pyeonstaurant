package com.kh.pyeonstaurant.myrecipe.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.pyeonstaurant.myrecipe.domain.MyRecipe;
import com.kh.pyeonstaurant.myrecipe.service.MyRecipeService;

@Controller
public class MyRecipeController {
	@Autowired
	private MyRecipeService mService;

	@RequestMapping(value="/myRecipe/remove", method=RequestMethod.POST)
	public String removeMyRecipe(Model model, HttpSession session) {

		try {
			int recipeNo = (int)session.getAttribute("recipeNo");
			int result = mService.removeMyRecipe(recipeNo);
			if(result > 0) {
				session.removeAttribute("recipeNo");
			}
			
		}catch(Exception e) {
			//			model.addAttribute("msg", e.toString());
			//			return "common/errorPage";

		}
		return "redirect:/myrecipe/list";
	}

	@RequestMapping(value="/myRecipe/add", method=RequestMethod.POST)
	public ModelAndView addMyRecipe(
			ModelAndView mv
			, HttpSession session
			,@ModelAttribute MyRecipe myRecipe
			, @RequestParam("memberEmail") String memberEmail
			, @RequestParam("recipeNo") int recipeNo
			,HttpServletRequest request) {
		try {
			myRecipe.setRecipeNo(recipeNo);
			int result = mService.addMyRecipe(myRecipe);
			if(result > 0) {
				mv.setViewName("redirect:/레시피 상세 페이지");	//이부분 추가해야함.
			}
		}catch(Exception e) {
		}
		return mv;	
	}
	
	@RequestMapping(value="/myRecipe/list", method=RequestMethod.POST)
	public ModelAndView selectAllMyRecipe(ModelAndView mv
			, HttpSession session
			,HttpServletRequest request
			,@RequestParam(value="page", required=false) Integer page ) {
		
		session = request.getSession();
		MyRecipe myRecipe = (MyRecipe)session.getAttribute("memberEmail");
		String memberEmail = myRecipe.getMemberEmail();
		
		int currentPage = (page != null) ? page : 1;
		int totalCount = mService.getTotalCount(memberEmail);
		int boardLimit = 10;
		int naviLimit = 5;
		int maxPage;
		int startNavi;
		int endNavi;
		// 23/5 = 4.8 + 0.9 = 5(.7)
		maxPage = (int)((double)totalCount/boardLimit + 0.9);
		startNavi = ((int)((double)currentPage/naviLimit+0.9)-1)*naviLimit+1;
		endNavi = startNavi + naviLimit - 1;
		if(maxPage < endNavi) {
			endNavi = maxPage;
		}
		List<MyRecipe> mList = mService.printMyRecipeList(currentPage, boardLimit, memberEmail);
		if(!mList.isEmpty()) {
			mv.addObject("maxPage", maxPage);
			mv.addObject("currentPage", currentPage);
			mv.addObject("startNavi", startNavi);
			mv.addObject("endNavi", endNavi);
			mv.addObject("mList", mList);
		}
		mv.setViewName("myrecipe/myRecipeList");
		return mv;
		
	}
	

	
}
