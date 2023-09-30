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
	String filePath="C:\\Users\\sdell\\projects\\SocialNotes\\material\\"+fileName;

		try{
			File pdfFile = new File(filePath);
		/*	PDDocument document = PDDocument.load(pdfFile);

			// Crea un oggetto PDFTextStripper
			PDFTextStripper pdfTextStripper = new PDFTextStripper();

			// Estrai il testo dal PDF
			String text = pdfTextStripper.getText(document);

			// Chiudi il documento PDF
			document.close();*/

			// Converti il testo estratto in un InputStream
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