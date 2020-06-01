package com.online.college.opt.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.util.CalendarUtil;
import com.online.college.common.util.JsonUtil;
import com.online.college.common.web.JsonView;
import com.online.college.common.web.SessionContext;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.domain.Userpingfen;
import com.online.college.core.auth.service.IAuthUserService;
import com.online.college.core.auth.service.IUserpingfensService;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.service.ICourseService;
import com.online.college.core.statics.domain.CourseStudyStaticsDto;
import com.online.college.core.statics.domain.StaticsVO;
import com.online.college.core.statics.service.IStaticsService;
import com.online.college.opt.business.IPortalBusiness;
import com.online.college.opt.controller1.Clusterer;
import com.online.college.opt.controller1.Dealmarting;
import com.online.college.opt.controller1.pso;
import com.online.college.opt.vo.ConstsClassifyVO;
import com.online.college.opt.vo.CourseSectionVO;

/**
 * 课程管理
 */
@Controller
@RequestMapping("/course")
public class CourseController {
	static String [][]arrs=null;
	 static String []s11=null;
	@Resource
	private ICourseService courseService;
	
	@Autowired
	private IPortalBusiness portalBusiness;
	
	@Autowired
	private IConstsClassifyService constsClassifyService;
	
	@Autowired
	private IAuthUserService authUserService;
	
	@Autowired
	private IStaticsService staticsService;
	
	@Autowired
	private IUserpingfensService UserpingfensService;
	/**
	 * 课程管理
	 */
	@RequestMapping("/pagelist")
	public ModelAndView list(Course queryEntity,TailPage<Course> page){
		ModelAndView mv = new ModelAndView("cms/course/pagelist");
		
		if(StringUtils.isNotEmpty(queryEntity.getName())){
			queryEntity.setName(queryEntity.getName().trim());
		}else{
			queryEntity.setName(null);
		}
		
		page.setPageSize(5);
		page = courseService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		mv.addObject("queryEntity", queryEntity);
		mv.addObject("curNav", "course");
		return mv;
	}
	
	/**
	 * 课程上下架
	 */
	@RequestMapping("/doSale")
	@ResponseBody
	public String doSale(Course entity){
		courseService.updateSelectivity(entity);
		//更新课程总时长
		
		return new JsonView().toString();
	}
	
	/**
	 * 课程删除
	 */
	@RequestMapping("/doDelete")
	@ResponseBody
	public String doDelete(Course entity){
		courseService.delete(entity);
		return new JsonView().toString();
	}
	
	/**
	 * 根据id获取
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public String getById(Long id){
		return JsonView.render(courseService.getById(id));
	}
	
	/**
	 * 课程详情
	 */
	@RequestMapping("/read")
	public ModelAndView courseRead(Long id){
		Course course = courseService.getById(id);
		if(null == course){
			return new ModelAndView("error/404"); 
		} 
		
		ModelAndView mv = new ModelAndView("cms/course/read");
		mv.addObject("curNav", "course");
		mv.addObject("course", course);
		
		//课程章节
		List<CourseSectionVO> chaptSections = this.portalBusiness.queryCourseSection(id);
		mv.addObject("chaptSections", chaptSections);
		
		//课程分类
		Map<String,ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();
		//所有一级分类
		List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
		for(ConstsClassifyVO vo : classifyMap.values()){
			classifysList.add(vo);
		}
		mv.addObject("classifys", classifysList);
		
		List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
		for(ConstsClassifyVO vo : classifyMap.values()){
			subClassifys.addAll(vo.getSubClassifyList());
		}
		mv.addObject("subClassifys", subClassifys);//所有二级分类
		
		//获取报表统计信息
		CourseStudyStaticsDto staticsDto = new CourseStudyStaticsDto();
		staticsDto.setCourseId(course.getId());
		staticsDto.setEndDate(new Date());
		staticsDto.setStartDate(CalendarUtil.getPreNDay(new Date(), 7));
		
		StaticsVO staticsVo = staticsService.queryCourseStudyStatistics(staticsDto);
		if(null != staticsVo){
			try {
				mv.addObject("staticsVo", JsonUtil.toJson(staticsVo));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mv;
	}
	
	/**
	 * 添加、修改课程
	 */
	@RequestMapping("/doMerge")
	@ResponseBody
	public String doMerge(Course entity,@RequestParam MultipartFile pictureImg){
		String key = null;
		try {
			if (null != pictureImg && pictureImg.getBytes().length > 0) {
				key = QiniuStorage.uploadImage(pictureImg.getBytes());//七牛上传图片
				entity.setPicture(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//判断教师
		if(StringUtils.isNotEmpty(entity.getUsername())){
			AuthUser user = authUserService.getByUsername(entity.getUsername());
			if(null == user){
				return JsonView.render(1).toString();
			}
		}else{
			return JsonView.render(1).toString();
		}

		if(null != entity.getId()){
			courseService.updateSelectivity(entity);
		}else{
			//判断获取分类
			if(StringUtils.isNotEmpty(entity.getClassify())){
				ConstsClassify classify = this.constsClassifyService.getByCode(entity.getClassify());
				if(null != classify){
					entity.setClassifyName(classify.getName());
				}
			}
			if(StringUtils.isNotEmpty(entity.getSubClassify())){
				ConstsClassify subClassify = this.constsClassifyService.getByCode(entity.getSubClassify());
				if(null != subClassify){
					entity.setSubClassifyName(subClassify.getName());
				}
			}
			courseService.createSelectivity(entity);
		}
		return JsonView.render(entity).toString();
	}
	
	
	/**
	 * 添加课程
	 */
	@RequestMapping("/add")
	public ModelAndView add(){
		ModelAndView mv = new ModelAndView("cms/course/add");
		mv.addObject("curNav", "course");
		Map<String,ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();
		//所有一级分类
		List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
		for(ConstsClassifyVO vo : classifyMap.values()){
			classifysList.add(vo);
		}
		mv.addObject("classifys", classifysList);
		
		List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
		for(ConstsClassifyVO vo : classifyMap.values()){
			subClassifys.addAll(vo.getSubClassifyList());
		}
		mv.addObject("subClassifys", subClassifys);//所有二级分类
		return mv; 
	}
	
	//继续添加章节
	@RequestMapping("/append")
	public ModelAndView appendSection(Long courseId){
		Course course = courseService.getById(courseId);
		if(null == course)
			return new ModelAndView("error/404"); 
		
		ModelAndView mv = new ModelAndView("cms/course/append");
		mv.addObject("curNav", "course");
		mv.addObject("course", course);
		
		List<CourseSectionVO> chaptSections = this.portalBusiness.queryCourseSection(courseId);
		mv.addObject("chaptSections", chaptSections);
		
		return mv;
	}
	
	public static void write(String filepath,int [][]value)throws Exception{
		 /*try {
	         FileWriter fw = new FileWriter(filepath);
	         BufferedWriter bw = new BufferedWriter(fw);
	         for (int[] v : value) {
	             for (int d : v) {
	                 bw.write(String.valueOf(d));
	             }
	             bw.newLine();
	         }
	         bw.close();
	         fw.close();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	*/
		 File file = new File(filepath);  //存放数组数据的文件

	     FileWriter out = new FileWriter(file);  //文件写入流

	     //将数组中的数据写入到文件中。每行各数据之间TAB间隔
	     for(int i=0;i<value.length;i++){
	         for(int j=0;j<value[i].length;j++){
	             out.write(value[i][j]+"\t");
	         }
	         out.write("\r\n");
	     }
	     out.close();

		   }
	
	/**
	 * 根据用户评分推荐
	 * @throws Exception 
	 */
	@RequestMapping("/dopingfen")
	@ResponseBody
	public String dopingfen() throws Exception{
		//courseService.updateSelectivity(entity);
		//更新课程总时长
		
		List<Userpingfen> userpingfen= UserpingfensService.getallpingfen();
		//把评分矩阵放在了marting二维数组里
		int[][] marting = new int[userpingfen.size()][3];
		for (int i = 0; i < userpingfen.size(); i++) {
			 Userpingfen upingfen = userpingfen.get(i);
			 marting[i][0]=upingfen.getUserid();
			 marting[i][1]=upingfen.getCourseid(); 
			 marting[i][2]=upingfen.getPingfenid();
			 
		}
		System.out.println("a:"+Arrays.deepToString(marting));
		
		  int t=marting[marting.length-1][0];
		    System.out.println("c"+t);
		
		    
		Dealmarting deals=new Dealmarting();
		int[][] Martings=deals.deal(marting);
		
		int[][]ss4=new int[Martings.length][Martings[0].length+1];
	       for(int i=0;i<ss4.length;i++)
	       {
	    	   for(int j=0;j<ss4[i].length-1;j++)
	    	   {
	    		   ss4[i][j]=  Martings[i][j];
	    		   
	    	   }
	    	   
	       }
	       System.out.println("ss4:"+Arrays.deepToString(ss4));
		
	       
	       int[][] Martingss = { { 0, 1, 4, 6, 8, 9 }, { 1, 0, 3, 5, 7, 8 }, 
	               { 4, 3, 0, 2, 4, 5 }, { 6, 5, 2, 0, 2, 3 }, 
	               { 8, 7, 4, 2, 0, 1 }, { 9, 8, 5, 3, 1, 0 } };
	       
	       
		Clusterer cluster1 = new Clusterer(Martingss.length);
		cluster1.cluster(Martingss,2);
		List ss2=cluster1.output(); 
		 int [] ss3=new int[Martingss.length];
	      for(int i=0;i<Martingss.length;i++)
	      {
	    	 ss3[i]=1;
	    	  
	      }
	      System.out.println("ss3:"+Arrays.toString(ss3));
	      for(int j=0;j<ss2.size();j++)
	       {int s =(int) ss2.get(j);
	         ss3[s]=2;
	       }
	      System.out.println("ss32:"+Arrays.toString(ss3));
	     //ss4为聚类后的结果，最后一列为分类结果
	      for(int i=0;i<ss4.length;i++)
	       {
	    	   
	    		ss4[i][ss4[0].length-1]=ss3[i];     
	    	   
	       }
	      //保存数据到1.txt
	      String filepath="d:\\1.txt";
	         write(filepath,ss4);
	      //调用matlab运行pso算法，然后把数据存入到1.excel
	  
	         pso pso1 = new pso();
	         pso1.getpso();
	       //  读取1.excel
	         String[][]ss5=readfileexcel("d:\\1.xls");
	         //ss7为最终数据，为每个用户推荐的编号
	         int[][]ss7=new int[ss5.length][ss5[0].length];
	         for(int i=0;i<ss5.length;i++)
	         {
	      	   for(int j=0;j<ss5[0].length;j++)
	      	   {
	      		   ss7[i][j]=Integer.parseInt(ss5[i][j]);
	      		   
	      	   }
	         }
	         //commlist存放要推荐的内容
	         String[][]commlist=new String[ss7.length][5];
	         for(int i=0;i<ss7.length;i++)
	         {
	        	 for(int j=0;j<ss7[i].length;j++)
	        	 {
	        		 int s = ss7[i][j];
	        		 String s1=courseService.getnamebyid(s);
	        		
	        		 s11[j]=s1;
	        	 }
	        	 AuthUser entity=new AuthUser();
	        	 entity.setId((long) i);
	        	 entity.setCourse1(s11[0]);
	        	 entity.setCourse2(s11[1]);
	        	 entity.setCourse3(s11[2]);
	        	 entity.setCourse4(s11[3]);
	        	 entity.setCourse5(s11[4]);
	        	 authUserService.update(entity);;
	        	 
	         }
	         
	         
		//System.out.println("b:"+Arrays.deepToString(Martings));
		System.out.println("hahahahahahahhahahahahahahahahahahaah");
		return new JsonView().toString();
	}

	private static String[][] readfileexcel(String string) {
		// TODO Auto-generated method stub
		
		   jxl.Workbook readwb = null;  
	       // List<String> list = new ArrayList<String>();  
	       try {  
	           // 构建Workbook对象, 只读Workbook对象 直接从本地文件创建Workbook  
	           readwb = Workbook.getWorkbook(new FileInputStream(new File("D:\\1.xls")));  
	           // Sheet的下标是从0开始 获取第一张Sheet表  
	           Sheet readsheet = readwb.getSheet(0);  
	           // 获取Sheet表中所包含的总列数  
	           int rsColumns = readsheet.getColumns();  
	           // 获取Sheet表中所包含的总行数  
	           int rsRows = readsheet.getRows();  
	           // 获取指定单元格的对象引用  
	           String[][] arr = (new String[rsColumns][rsRows]);  
	           for (int i = 0; i < rsColumns; i++) {  
	               for (int j = 0; j < rsRows; j++) {  
	                   Cell cell = readsheet.getCell(i, j);  
	                   // System.out.print(cell.getContents() + " ");  
	                   // list.add(cell.getContents());  
	                   arr[i][j] = cell.getContents();  
	                  
	               }  
	           }  
	            arrs=arr;
	           for (int i = 0; i < rsColumns; i++) {  
	               for (int j = 0; j < rsRows; j++) {  
	                   System.out.print(arr[i][j] + " ");  
	               }  
	               System.out.println("------------数据分割线-----------------");  
	           }  
	       } catch (Exception e) {  
	           e.printStackTrace();  
	       } finally {  
	           readwb.close();  
	       }
		return arrs; 
	}

	

} 
	
	
	

