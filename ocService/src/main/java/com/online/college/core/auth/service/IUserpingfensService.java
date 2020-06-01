package com.online.college.core.auth.service;

import java.util.List;

import com.online.college.common.page.TailPage;
import com.online.college.core.auth.domain.Userpingfen;
import com.online.college.core.user.domain.Userpingfens;
import com.online.college.core.user.domain.Userpingfens;


public interface IUserpingfensService {

	
	/**
	*创建
	**/
	public void createSelectivity(Userpingfen entity);

	public List<Userpingfen> getallpingfen();

	

}

