package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

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

@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public AddToCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect("homepage.jsp");
		}
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		Collection<MaterialBean>cart=(Collection<MaterialBean>)session.getAttribute("cart");
		String codice=request.getParameter("codice");
		System.out.println("codice materiale in servlet: "+codice);
		MaterialModelDS materialModel=new MaterialModelDS(ds);
		try {
			MaterialBean material=materialModel.doRetrieveByKey(codice);
			cart.add(material);
			session.setAttribute("cart",cart);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String url=(String)request.getParameter("url");
		System.out.println(url);
		if(request.getParameter("friendname")!=null) {
			System.out.println(request.getParameter("friendname"));
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+response.encodeURL(url)+"?friendname="+request.getParameter("friendname"));
			dispatcher.forward(request, response);
		}
		else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+response.encodeURL(url));
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
