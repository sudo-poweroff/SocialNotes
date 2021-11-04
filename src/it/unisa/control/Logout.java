package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.model.UserModelDS;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
HttpSession session = request.getSession();
		
		if(session == null) {
			String signUpURL = "login.jsp";
			response.sendRedirect(signUpURL);
		}else {
			DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
			Timestamp ultimoAccesso = new Timestamp(System.currentTimeMillis());
			UserModelDS userModel=new UserModelDS(ds);
			try {
				userModel.doUpdateUltimoAccesso((String)session.getAttribute("username"), ultimoAccesso);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.invalidate();
			String homeURL = "homepage.jsp";
			response.sendRedirect(homeURL);
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		
	}

}
