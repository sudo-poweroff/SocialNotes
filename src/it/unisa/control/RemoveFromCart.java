package it.unisa.control;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unisa.model.MaterialBean;

@WebServlet("/RemoveFromCart")
public class RemoveFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RemoveFromCart() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect("homepage.jsp");
		}
		Collection<MaterialBean>cart=(Collection<MaterialBean>)session.getAttribute("cart");
		String codice=request.getParameter("codice");
		int codiceMateriale=Integer.parseInt(codice);
		System.out.println("codice materiale in servlet: "+codiceMateriale);
		if(cart!=null&&cart.size()>0){
			Iterator<?> it=cart.iterator();
			while(it.hasNext()) {
				MaterialBean material=(MaterialBean)it.next();
				if(material.getCodiceMateriale()==codiceMateriale)
					//cart.remove(material);
					it.remove();
			}
			session.setAttribute("cart",cart);
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cart.jsp");
		dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
