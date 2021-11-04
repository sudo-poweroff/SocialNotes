package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.RoleModelDS;
import it.unisa.utils.Utility;


@WebServlet("/RoleControl")
public class RoleControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		RoleModelDS model= new RoleModelDS(ds);
		try {
			request.setAttribute("roles",model.doRetrieveAll());
		}
		catch(SQLException e) {
			Utility.print(e);
			request.setAttribute("error", e.getMessage());
		}
		RequestDispatcher dispatcher=this.getServletContext().getRequestDispatcher("/Index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
