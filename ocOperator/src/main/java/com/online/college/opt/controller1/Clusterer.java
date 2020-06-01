package com.online.college.opt.controller1;

import java.util.*; 

 
public class Clusterer { 
	   private static List[] clusterList; 
	   private static List ss2; 
	   static DisjointSets ds; 
	   private static final int MAX = Integer.MAX_VALUE; 
	   private static int n; 
	   private int cc; 

	   // private int ori[] = {1,2,5,7,9,10}; 

	   public Clusterer(int num) { 
	       ds = new DisjointSets(num); 
	       n = num; 
	       cc = n; 
	       clusterList = new ArrayList[num]; 
	       for (int i = 0; i < n; i++) 
	           clusterList[i] = new ArrayList(); 
	   } 

	   public List[] getClusterList() { 
	       return clusterList; 
	   } 

	   public void setClusterList(List[] clusterList) { 
	       this.clusterList = clusterList; 
	   } 

	   public List output() throws Exception { 
	       int ind = 1; int t=0;
	       int[][] xxxx=new int[2][5];
	     
	       int[] y=new int[4];
	       for (int i = 0; i < n; i++) { 
	           clusterList[ds.find(i)].add(i); 
	       } 
	       for (int i = 0; i < n; i++) { 
	           if (clusterList[i].size() != 0) { 
	               System.out.print("cluster " + ind + " :"); 
	               
	             
	               for (int j = 0; j < clusterList[i].size(); j++) { 
	                   System.out.print(clusterList[i].get(j) + " "); 
	                   List[]ss=new List[clusterList[i].size()];
	                  ss[1]=  clusterList[ind];
	                  System.out.println(ss[1].toString());
	                  ss2= clusterList[ind];
	                 
	               } 
	               System.out.println(); 
	               ind++;  
	           } 
	       } 
	      //System.out.println("dasdas"+xxxx.toString());
		return ss2;
	     
	     
	      
	   } 

	   /** *//** 
	49     * this method provides a hierachical way for clustering data. 
	50     * 
	51     * @param r 
	52     *            denote the distance matrix 
	53     * @param n 
	54     *            denote the sample num(distance matrix's row number) 
	55     * @param dis 
	56     *            denote the threshold to stop clustering 
	57     
	 * @throws Exception */ 
	   public void cluster(int[][] r, int n, int dis) throws Exception { 
	       int mx = 0, my = 0; 
	       int vmin = MAX; 
	       for (int i = 0; i < n; i++) { // 寻找最小距离所在的行列 
	           for (int j = 0; j < n; j++) { 
	               if (j > i) { 
	                   if (vmin > r[i][j]) { 
	                       vmin = r[i][j]; 
	                       mx = i; 
	                       my = j; 
	                   } 
	               } 
	           } 
	       } 
	       if (vmin > dis) { 
	           return; 
	       } 
	       ds.union(ds.find(mx), ds.find(my)); // 将最小距离所在的行列实例聚类合并 
	       int o1[] = r[mx]; 
	       int o2[] = r[my]; 
	       int v[] = new int[n]; 
	       int vv[] = new int[n]; 
	       for (int i = 0; i < n; i++) { 
	           int tm = Math.min(o1[i], o2[i]); 
	           if (tm != 0) 
	               v[i] = tm; 
	           else 
	               v[i] = MAX; 
	           vv[i] = MAX; 
	       } 
	       r[mx] = v; 
	       r[my] = vv; 
	       for (int i = 0; i < n; i++) { // 更新距离矩阵 
	           r[i][mx] = v[i]; 
	           r[i][my] = vv[i]; 
	       } 
	       cluster(r, n, dis); // 继续聚类，递归直至所有簇之间距离小于dis值 
	   } 

	   /** *//** 
	    * 
	    * @param r 
	    * @param cnum 
	    *            denote the number of final clusters 
	 * @throws Exception 
	    */ 
	   public void cluster(int[][] r, int cnum) throws Exception { 
	       /**//*if(cc< cnum) 
	           System.err.println("聚类数大于实例数");*/ 
	       while (cc > cnum) {// 继续聚类，循环直至聚类个数等于cnum 
	           int mx = 0, my = 0; 
	           int vmin = MAX; 
	           for (int i = 0; i < n; i++) { // 寻找最小距离所在的行列 
	               for (int j = 0; j < n; j++) { 
	                   if (j > i) { 
	                       if (vmin > r[i][j]) { 
	                           vmin = r[i][j]; 
	                           mx = i; 
	                           my = j; 
	                       } 
	                   } 
	               } 
	           } 
	           ds.union(ds.find(mx), ds.find(my)); // 将最小距离所在的行列实例聚类合并 
	           int o1[] = r[mx]; 
	           int o2[] = r[my]; 
	           int v[] = new int[n]; 
	           int vv[] = new int[n]; 
	           for (int i = 0; i < n; i++) { 
	               int tm = Math.min(o1[i], o2[i]); 
	               if (tm != 0) 
	                   v[i] = tm; 
	               else 
	                   v[i] = MAX; 
	               vv[i] = MAX; 
	           } 
	           r[mx] = v; 
	           r[my] = vv; 
	           for (int i = 0; i < n; i++) { // 更新距离矩阵 
	               r[i][mx] = v[i]; 
	               r[i][my] = vv[i]; 
	           } 
	           cc--; 
	       } 
	   } 

	   /* public static void main(String args[]) throws Exception { 
	      int[][] r = { { 0, 1, 4, 6, 8, 9 }, { 1, 0, 3, 5, 7, 8 }, 
	               { 4, 3, 0, 2, 4, 5 }, { 6, 5, 2, 0, 2, 3 }, 
	               { 8, 7, 4, 2, 0, 1 }, { 9, 8, 5, 3, 1, 0 } }; 
	       Clusterer cl = new Clusterer(6); 
	       //cl.cluster(r, 6, 1); 
	       cl.cluster(r, 2); 
	     //方法4
	     

	      
	       ss2=cl.output();
	       int [] ss3=new int[r.length];
	      for(int i=0;i<r.length;i++)
	      {
	    	 ss3[i]=1;
	    	  
	      }
	      System.out.println("ss3:"+Arrays.toString(ss3));
	      for(int j=0;j<ss2.size();j++)
	       {int s =(int) ss2.get(j);
	         ss3[s]=2;
	       }
	      System.out.println("ss3:"+Arrays.toString(ss3));
	       System.out.println(ss2.toString());
	  } 
*/
	} 