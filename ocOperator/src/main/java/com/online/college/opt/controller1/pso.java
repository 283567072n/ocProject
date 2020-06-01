package com.online.college.opt.controller1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clusterCon.Class1;

import com.mathworks.toolbox.javabuilder.MWException;

public class pso {

	public void getpso() {
		// TODO Auto-generated method stub
		
		try {
    		Class1 pureHearRate = new Class1();
         
            
          
            //pureHeartRate.purehr(),第一个参数是有几个输出结果，在这里我们只有一个：HearRate，第二个参数是文件路径,第三个是总时间
           Object []xx = pureHearRate.clusterCon(1);
           System.out.println("心率是： " + Arrays.deepToString( xx));//多个输出则：results[1].toString(),results[2].toString()...
               Object xx1= xx[0];
           // for(int i=0;i<xx.length;i++){
       System.out.println("sdfas"+xx[0].toString());
       List<Object> list = new ArrayList<Object>();//定义一个数组类型的list对象
    		 //创建一个要实体化的数组对象
    		   list.add(xx[0] );
       Object sss=list.get(0);
    		   System.out.println("knsacknsa"+sss);
    		  
    	} catch (MWException e) {
            e.printStackTrace();
        }
	}

	
	
	
	
}
