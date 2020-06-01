<%@ page language="java" import="java.sql.*,java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       
    </head>
    <body>
        <%
            //连接数据库
            String driverName="com.mysql.jdbc.Driver";
            String dbURL="jdbc:mysql://localhost:3306/ocdb";
            String userName="root";
            String pwd="123456";
            Class.forName(driverName);
            Connection conn=DriverManager.getConnection(dbURL,userName,pwd);
            String sql="insert into t_user_pingfens (Userid,Username,Courseid,pingfenid) values(?,?,?,?)";
            PreparedStatement stmp=conn.prepareStatement(sql);
            request.setCharacterEncoding("UTF-8");
            int id=Integer.parseInt(request.getParameter("Userid"));
          
            String Username=request.getParameter("Username");
            int Courseid=Integer.parseInt(request.getParameter("Courseid"));
          
            int pingfenid=Integer.parseInt(request.getParameter("id"));

            stmp.setInt(1,Userid);
            stmp.setString(2,Username);
            stmp.setInt(3,Courseid);
            
            stmp.setInt(4,pingfenid);
          
            int n=stmp.executeUpdate();
            %>
            				请填写评分的数据:
        <form action="*" method="post">
            评分:<input type="text" name="id" value="评分为1-5"><br><br>
            <input  name="Courseid" value="${(course.id)!}"  >
           
          <input  name="Userid" value='<@shiro.principal property="id"/>' >
          <input  name="Username" value='<@shiro.principal property="username"/>' >
           
            <input type="submit" value="提交">
            
        </form>
    </body>
</html>
	
	







