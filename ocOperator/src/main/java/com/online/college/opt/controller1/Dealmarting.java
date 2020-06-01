package com.online.college.opt.controller1;

public class Dealmarting {

	public int[][] deal(int[][] marting)
	{
	    int t=marting[marting.length-1][0]+1;
	  //  System.out.println(marting[t][0]);
		int Martings[][]=new int [t][31];
		for(int i = 0; i < marting.length; i++){ 
			//for(int j = 0; j < marting[i].length; j++){ 
		      
			
				Martings[marting[i][0]][marting[i][1]]=marting[i][2];
				
				
			//} 
			
		
		}
		
		return Martings;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    
	}

}
