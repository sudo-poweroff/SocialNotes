package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.model.MaterialBean;
import it.unisa.model.MaterialModelDS;
import it.unisa.model.UserBean;
import it.unisa.model.UserModelDS;

/**
 * Servlet implementation class SearchFriend
 */
@WebServlet("/SearchFriend")
public class SearchFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFriend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		
		String url = "errorSearch.jsp";
		String homeLink = "homepage.jsp";
		//System.out.println("ID "+session.getAttribute("username"));
		if (session!=null) {
		if (session.getAttribute("username")!=null) {
			
			url = response.encodeRedirectURL(url);
			homeLink = response.encodeRedirectURL(homeLink);
		}
		}else {
			response.sendRedirect(homeLink);
			return;
		}

		String str = (String)request.getParameter("search");

	
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		UserModelDS userDS = new UserModelDS(ds);
		
		
try {
			
			String ratOrder="novalue";
			int rating = 0;
			if (request.getParameter("stars")!=null)
			rating= Integer.parseInt(request.getParameter("stars"));
		
			
			if(request.getParameter("ratingOrder")!=null)
				ratOrder=(String)request.getParameter("ratingOrder");
			
		/*	if ((date.compareTo("novalue")==0)&&(date.compareTo("novalue")==0)&&(rating==0)) {
				date = "ASC";
				ratOrder = "ASC";
				rating = 0;
			}else if ((date.compareTo("novalue")==0)&&(ratOrder.compareTo("novalue")==0)&&(rating!=0)) {
				date = "ASC";
				ratOrder = "ASC";
				rating = 0;
			}else if ((date.compareTo("novalue")==0)&&(ratOrder.compareTo("novalue")!=0)&&(rating==0)) {
				date = "ASC";
				rating = 0;
			}else if ((date.compareTo("novalue")==0)&&(ratOrder.compareTo("novalue")!=0)&&(rating!=0)) {
				// CIAAO;
				date = "ASC";
			}else if ((date.compareTo("novalue")!=0)&&(ratOrder.compareTo("novalue")==0)&&(rating==0)) {
				ratOrder = "ASC";
				rating = 0;
			}else if ((date.compareTo("novalue")!=0)&&(ratOrder.compareTo("novalue")==0)&&(rating!=0)) {
				ratOrder = "ASC";
			}else if ((date.compareTo("novalue")!=0)&&(ratOrder.compareTo("novalue")!=0)&&(rating==0)) {
				rating = 0;
			} */
			
			System.out.println("VALORI :"+ str +" "+ratOrder+" "+rating);
			
			Collection<UserBean> bean= userDS.doRetrieveByParametersUser(str, ratOrder, rating);
			
			request.setAttribute("utente", bean);
			String urlSearch = response.encodeRedirectURL("/searchFriend.jsp");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlSearch);
			dispatcher.forward(request, response);
	
			
		}catch(SQLException e) {
			e.printStackTrace();
		
		
	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
/*	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
   */
}
