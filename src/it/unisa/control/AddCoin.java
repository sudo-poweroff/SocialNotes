package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.model.UserModelDS;

@WebServlet("/AddCoin")
public class AddCoin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddCoin() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect("homepage.jsp");
		}
		int coin=(int)session.getAttribute("coin");
		int newCoin=Integer.parseInt(request.getParameter("coin"))+coin;
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		UserModelDS userModel=new UserModelDS(ds);
		String username=(String)session.getAttribute("username");
		try {
			userModel.doUpdateCoin(username, newCoin);
			session.setAttribute("coin",newCoin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String link = "paymentSuccess.jsp";
		 String encodedURL = response.encodeRedirectURL(link);
		 response.sendRedirect(encodedURL);
		/*RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homepage_user.jsp");
		dispatcher.forward(request, response);*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
