package com.online.college.core.auth.dao;

import java.util.List;

import com.online.college.common.page.TailPage;
import com.online.college.core.auth.domain.Userpingfen;
import com.online.college.core.user.domain.Userpingfens;
import com.online.college.core.user.domain.Userpingfens;


public interface UserpingfensDao {

	



	
	/**
	*创建新记录
	**/
	public void createSelectivity(Userpingfen entity);
	
	public List<Userpingfen> getallpingfen();


}

