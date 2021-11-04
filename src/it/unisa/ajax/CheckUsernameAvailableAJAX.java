package it.unisa.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.unisa.model.UserModelDS;

/**
 * Servlet implementation class Registrazione
 */
@WebServlet("/CheckUsernameAvailableAJAX")
public class CheckUsernameAvailableAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckUsernameAvailableAJAX() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/strings");
		String name=request.getParameter("param");
		PrintWriter out = response.getWriter();
		if(name!=null) {
			//System.out.println(name);
			
			DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
			UserModelDS model= new UserModelDS(ds);
			try {
				if(model.doRetrieveByUsername(name)==null) {
					out.write("true");
					//disponibile
				}
				else {
					out.write("false");
				}
			}catch(SQLException e) {
				
			}
			/*if(name.equals("Cristiano")||name.equals("Ronaldo")) {
				out.write("false");
				System.out.println("non disponibile");
			}
			else {
				out.write("true");
				System.out.println("disponibile");
			}
			*/
			
		}
	}

}
