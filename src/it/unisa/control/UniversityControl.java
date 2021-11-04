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

import it.unisa.model.UniversityModelDS;
import it.unisa.utils.Utility;

@WebServlet("/UniversityControl")
public class UniversityControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		UniversityModelDS model= new UniversityModelDS(ds);
		try {
			request.setAttribute("universities",model.doRetrieveAll());
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
