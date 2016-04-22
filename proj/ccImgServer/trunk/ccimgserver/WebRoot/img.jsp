<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">



<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>download img</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
 

  
  <body>
  
     <script  ianguage="JavaScript">
    
  
		function check()
		{
			
		    var lmv = document.getElementById('lmv');
				if(isNaN(Number(lmv.value))){ 
					alert('只能输入数字');
					return false;
			}else if(lmv.value.length == 0){
				alert('请输入彩码宽度');
				return false;
			}
			
			 var dpi = document.getElementById('dpi');
					if(isNaN(Number(dpi.value))){ 
						alert('只能输入数字');
						return false;
				}else if(dpi.value.length == 0){
					alert('请输入彩码dpi');
					return false;
				}
				
		    var ccid=document.getElementById("ccid").value;
		    if(ccid.length != 11 && ccid.length !=18){
		    	alert("彩码ID不正确，只能输入11位或18位");
		    	return false;
		    }
		    
		return true;
		    
		
		}
			
		
</script>
    输入彩码ID 进行图片下载. <br><br>
    
    <form action="/img" method="post" enctype="application/x-www-form-urlencoded">
    	&nbsp;&nbsp;&nbsp;彩码ID：&nbsp;<input type="text" name="ccid"  id="ccid"><br><br/>
    	
    	&nbsp;&nbsp;彩码厘米：<input type="text" name="lmv" id="lmv" value="2"><br/><br/>
    
        &nbsp;&nbsp; 彩码dpi：<input type="text" name="dpi" id="dpi" value="300"><br/><br/>
    	
    	&nbsp;&nbsp;彩码边框：<select name="border" id="border">
			  <option value="0" >有</option>
			  <option value="1" selected="selected">无</option>
			  </select><br/><br/>
    	
    	彩码静止区：<select name="staticArea" id="staticArea">
			  <option value="0">有</option>
			  <option value="1" selected="selected">无</option>
			  </select><br/><br/>
			  
    	<input type="submit" value="下载" onclick="return check()">
    	
    </form>
  </body>
</html>
