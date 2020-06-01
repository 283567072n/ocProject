package com.online.college.core.auth.domain;

public class Userpingfen {
private static final long serialVersionUID = 94044276250229411L;
	
	/**
	*登录用户名
	**/
	private Integer Userid;

	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	*真实姓名
	**/
	private String Username;

	/**
	*密码
	**/
	private Integer Courseid;

	/**
	*性别
	**/

	private String Coursename;
	public String getCoursename() {
		return Coursename;
	}

	public void setCoursename(String coursename) {
		Coursename = coursename;
	}

	/**
	*头像
	**/
	private Integer pingfenid;

	public Integer getUserid() {
		return Userid;
	}

	public void setUserid(Integer curUserId) {
		Userid = curUserId;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public Integer getCourseid() {
		return Courseid;
	}

	public void setCourseid(Integer courseid) {
		Courseid = courseid;
	}

	public Integer getPingfenid() {
		return pingfenid;
	}

	public void setPingfenid(Integer pingfenid) {
		this.pingfenid = pingfenid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
