<%@page import="it.unisa.model.RoleBean"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,it.unisa.model.*"%>
<% 
	Collection<?> roles=(Collection<?>)request.getAttribute("roles");
	String error=(String)request.getAttribute("error");
	if(roles==null&&error==null){
		response.sendRedirect(response.encodeRedirectURL("./RoleControl"));
		return;
	}

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SocialNotes</title>
</head>
<body>
	<h2>Roles</h2>
<table>
	<tr>
		<th>IDRuolo</th>
		<th>Ruolo</th>
	</tr>
	<%
		if(roles!=null&&roles.size()>0){
			Iterator<?> it=roles.iterator();
			RoleBean bean= new RoleBean();
			while(it.hasNext()){
				bean=(RoleBean)it.next();
					
	%>
				<tr>
					<td><%=bean.getIdRuolo() %></td>
					<td><%=bean.getRuolo() %></td>
					<td></td>
				</tr>

	<%
			}
		}
		else{
	%>
			<tr>
				<td colspan="4">No roles available</td>
			</tr>
	
	<%
		}
	%>
</table>

	
</body>
</html>