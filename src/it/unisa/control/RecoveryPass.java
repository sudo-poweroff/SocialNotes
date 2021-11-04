package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.UserBean;
import it.unisa.model.UserModelDS;
import it.unisa.utils.RandomString;
import it.unisa.utils.SendEmail;

/**
 * Servlet implementation class RecoveryPass
 */
@WebServlet("/RecoveryPass")
public class RecoveryPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecoveryPass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("inputUser");
		
		 DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		UserModelDS uModel = new UserModelDS(ds);
		System.out.println("USERNAME REQUEST: "+username);
		UserBean bean = new UserBean();
		try {
			bean = uModel.doRetrieveByUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
		
		if (bean!=null) {
		RandomString gen = new RandomString(12, ThreadLocalRandom.current());
		String password = gen.nextString();
		System.out.println("NUOVA PASSWORD : "+password);
		
	    try {
			uModel.doUpdatePassword(username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    String from = "socialnotes2021@gmail.com";
        String pass = "Despacito21";
        String[] to = { bean.getEmail() }; // list of recipient email addresses
        String subject = "CONFERMA DI AVVENUTA REGISTRAZIONE SU Social Notes";
        String body = "Ciao "+bean.getUsername()+" La tua nuova passowrd e' : "+password+" ";
	    
	    SendEmail sendEmail = new SendEmail(from,pass,to,subject,body);
	    
	    sendEmail.SendMail();
	    response.sendRedirect("login.jsp");
		}
		else
			response.sendRedirect("login.jsp");
	}

}
