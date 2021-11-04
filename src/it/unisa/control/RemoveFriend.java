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


import it.unisa.model.FriendsModelDS;

@WebServlet("/RemoveFriend")
public class RemoveFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveFriend() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect("homepage.jsp");
		}
		
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		String friendname=request.getParameter("friendname");
		String username=(String)session.getAttribute("username");
		//System.out.println(username);
		FriendsModelDS friend=new FriendsModelDS(ds);
		try {
			friend.doDeleteFriend(friendname,username );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("friendname", friendname);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(response.encodeURL("/visitUser.jsp"));
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
