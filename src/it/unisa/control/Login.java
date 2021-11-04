package it.unisa.control;

import java.io.IOException;
import java.io.PrintWriter;
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
import it.unisa.model.UserBean;
import it.unisa.model.UserModelDS;
import it.unisa.model.UserRoleModelDS;
import it.unisa.utils.Validation;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		HttpSession session = request.getSession(true);
		System.out.println("SSSSSS:"+session.getId());
		if(session.getAttribute("username")!=null){
			String username=(String)session.getAttribute("username");
			UserRoleModelDS role=new UserRoleModelDS(ds);
			try {
				int userRole=role.doRetrieveByUsername(username);
				if(userRole==1) {
					String link = "admin.jsp";
					String encodedURL = response.encodeRedirectURL(link);
					response.sendRedirect(encodedURL);
				}
				else{
					String link = "homepage_user.jsp";
					String encodedURL = response.encodeRedirectURL(link);
					response.sendRedirect(encodedURL);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		else {
		
		String login = request.getParameter("login");
		String pwd = request.getParameter("password");
		//System.out.println(login);
		//System.out.println(pwd);

		//validazione
		
		if(login==null || login.trim().equals("") || pwd==null || pwd.trim().equals("") || !Validation.validatePassword(pwd)) {
			String error="Accesso negato";
			request.setAttribute("error",error);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
				UserModelDS model= new UserModelDS(ds);
				try {
					UserBean bean = model.checkLogin(login, pwd);
					if (bean==null) {
						String error="Login e/o password non corretti.";
						request.setAttribute("error",error);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
						dispatcher.forward(request, response);
						return;
						
					}
					else if(bean.getBan()) {
						System.out.println("Sei bannato");
						PrintWriter out = response.getWriter();	
						out.write("Spiacente, sei stato bannato");
						
					}
					else {
				   // System.out.println("USERNAME: "+bean.getUsername());		
					session.setAttribute("username",bean.getUsername());
					session.setAttribute("nome",bean.getNome());
					session.setAttribute("cognome",bean.getCognome());
					session.setAttribute("img",bean.getImg());
					session.setAttribute("email",bean.getEmail());
					session.setAttribute("password",bean.getPass());
					session.setAttribute("dataNascita",bean.getDataNascita());
					session.setAttribute("matricola",bean.getMatricola());
					session.setAttribute("ultimoAccesso",bean.getUltimoAccesso());
					session.setAttribute("coin",bean.getCoin());
					session.setAttribute("ban",bean.getBan());
					session.setAttribute("denominazione",bean.getDenominazione());
					session.setAttribute("dipName",bean.getDipName());
					UserRoleModelDS role=new UserRoleModelDS(ds);
					int userRole=role.doRetrieveByUsername(bean.getUsername());
					
					session.setAttribute("role", userRole);
					Collection<MaterialBean>cart=new LinkedList<MaterialBean>();
					session.setAttribute("cart", cart);
					//System.out.println("user role in login.java"+userRole);
					String adminURL=response.encodeURL("admin.jsp");
					String homeURL = response.encodeURL("homepage_user.jsp");
					if(userRole==1)
						response.sendRedirect(adminURL);
					else
						response.sendRedirect(homeURL);
					}
				}catch(SQLException e) {
					e.printStackTrace();
					String error="Problema con la query";
					request.setAttribute("error",error);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
				}
				

		doGet(request, response);
	}


}
}