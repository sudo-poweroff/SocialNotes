package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.model.MaterialModelDS;
import it.unisa.model.UserBean;
import it.unisa.model.UserModelDS;

@WebServlet("/SetPrice")
@MultipartConfig(maxFileSize = 1024*1024*50) 
public class SetPrice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetPrice() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect("homepage.jsp");
		}
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		String pr=(String)request.getParameter("costo");
		String mat=(String)request.getParameter("codice");
		String username=(String)request.getParameter("username");
		//System.out.println(pr);
		//System.out.println(mat);
		int price=Integer.parseInt(pr);
		int codiceMateriale=Integer.parseInt(mat);
		MaterialModelDS material=new MaterialModelDS(ds);
		UserModelDS userModel=new UserModelDS(ds);
		try {
			UserBean user=userModel.doRetrieveByUsername(username);
			int coin=user.getCoin()+price;
			System.out.println(coin);
			userModel.doUpdateCoin(username, coin);
			material.setPrice(codiceMateriale, price);
		}catch (SQLException e) {
			String error = "Spiacenti, la registrazione delle informazioni nel database non è andata a buon fine.";
			request.setAttribute("error", error);
			//Mando una alert 
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/signup.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		} 
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/materiale.jsp");
		dispatcher.forward(request, response);
	}

}
