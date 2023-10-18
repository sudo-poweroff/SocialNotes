<%@page import="java.nio.Buffer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" import="javax.sql.DataSource,materiale.*"%>
<%@ page import="org.apache.pdfbox.pdmodel.PDDocument" %>
<%@ page import="org.apache.pdfbox.text.PDFTextStripper" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="img/favicon.ico">
<title></title>
</head>
<body>
	<%
	String fileName=request.getParameter("fileName");
	response.setHeader("Content-Disposition", "filename="+fileName);//attachment dopo la virgola per scaricare direttamente
	response.setContentType("application/pdf");
	System.out.println(fileName);
		String relativePath="WebContent\\material\\";
		ServletContext context = request.getServletContext();
		String absolutePath = context.getRealPath("");
		String[] path=absolutePath.split("\\\\");
		String effectivePath="";
		for(int i=0;i<path.length-3;i++)
			effectivePath+=path[i]+"\\";
		effectivePath+=relativePath+fileName;

		try{
			File pdfFile = new File(effectivePath);
			InputStream is = new FileInputStream(pdfFile);

			OutputStream outStream = response.getOutputStream();
			byte[] buf = new byte[4096];
			int len = -1;

			//Write the file contents to the servlet response
			//Using a buffer of 4kb (configurable). This can be
			//optimized based on web server and app server
			//properties
			while ((len = is.read(buf)) != -1) {
				outStream.write(buf, 0, len);
			}

			outStream.flush();
			outStream.close();
		}catch (Exception e){}
	%>
</body>
</html>