package com.online.college.portal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.util.EncryptUtil;
import com.online.college.common.web.JsonView;
import com.online.college.common.web.SessionContext;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.domain.Userpingfen;
import com.online.college.core.auth.service.IUserpingfensService;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.user.domain.UserCollections;
import com.online.college.core.user.domain.Userpingfens;
import com.online.college.core.user.service.IUserCollectionsService;


/**
 * 用户收藏
 */
@Controller
@RequestMapping("/auth")
public class PingfenController{

	@Autowired
	private IUserpingfensService userpingfensService;

	/*@RequestMapping(value = "/dopingfens")
	@ResponseBody
	public void dopingfen(Long courseId,Integer index){
		//获取当前用户
		Long curUserId = SessionContext.getUserId();
	 
		Userpingfens userCollections = new Userpingfens();
		
		userCollections.setUserId(curUserId);
		userCollections.setClassify(index);//课程收藏
		userCollections.setObjectId(courseId);
		
		//userCollections.setCreateTime(new Date());
		userCollectionsService.createSelectivity(userCollections);
		
	}*/
	
	/*
	 * 
	 * 实现评分
	 */
	@RequestMapping(value = "/dopingfen")
	@ResponseBody
	public String dopingfen(Userpingfen authUser) {
		
		
		//Integer i=Integer.valueOf(((Long)Courseid).toString());
		//Integer j=Integer.valueOf(((Long)Userid).toString());
		//Long curUserId = SessionContext.getUserId(); 
		//Userpingfen authUser = new Userpingfen();
		//System.out.println(Courseid);
		//System.out.println(Userid);
	//authUser.setCourseid(Courseid);
		//authUser.setPingfenid(request.getParameter("pingfenid"));
		//authUser.setUserid(Userid);
		//authUser.setUsername(Username);
		//System.out.println(curUserId);
		userpingfensService.createSelectivity(authUser);
		//return JsonView.render(0);
		return new JsonView(1).toString();
	}
	

	
}

