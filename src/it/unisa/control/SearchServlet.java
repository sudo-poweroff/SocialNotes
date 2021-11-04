package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

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
import it.unisa.model.UserModelDS;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
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
		String searchPage = "/Search.jsp";
		if (session!=null) {
			searchPage = response.encodeURL(searchPage);
			url = response.encodeRedirectURL(url);
			
		}
		
		if((request.getParameter("ricerca").isEmpty())||request.getParameter("ricerca").isBlank()) {
			response.sendRedirect(url);
			return;
		}
		String str = (String)request.getParameter("ricerca");

	
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		MaterialModelDS model= new MaterialModelDS(ds);
		
		
		

		try {
			
			String date="novalue";
			String ratOrder="novalue";
			int rating = 0;
			if (request.getParameter("stars")!=null)
			rating= Integer.parseInt(request.getParameter("stars"));
		
			if(request.getParameter("date")!=null)
				date=(String)request.getParameter("date");
			
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
			
			System.out.println("VALORI :"+ date +" "+ratOrder+" "+rating);
			
			Collection<MaterialBean> material= model.doRetrieveByParameters(str, date, ratOrder, rating);
			
			request.setAttribute("ricercaNew", str);
			request.setAttribute("materiale", material);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(searchPage);
			dispatcher.forward(request, response);
			
		}catch(SQLException e) {
			e.printStackTrace();
		
		
	}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/* protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	} */
}
