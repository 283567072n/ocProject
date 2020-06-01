package com.online.college.core.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.college.common.page.TailPage;
import com.online.college.core.auth.dao.UserpingfensDao;
import com.online.college.core.auth.domain.Userpingfen;
import com.online.college.core.auth.service.IUserpingfensService;
import com.online.college.core.user.domain.Userpingfens;
import com.online.college.core.user.domain.Userpingfens;



@Service
public class UserpingfensServiceImpl implements IUserpingfensService{

	@Autowired
	private UserpingfensDao entityDao;


	public void createSelectivity(Userpingfen entity){
		entityDao.createSelectivity(entity);
	}


	@Override
	public List<Userpingfen> getallpingfen() {
		// TODO Auto-generated method stub
		return entityDao.getallpingfen();
	}


	






	/*@Override
	public void createSelectivity(Userpingfen entity) {
		// TODO Auto-generated method stub
		entityDao.createSelectivity(entity);
	}*/


	
	
	


}

