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
 * Servlet implementation class CheckEmailAvailableAJAX
 */
@WebServlet("/CheckEmailAvailableAJAX")
public class CheckEmailAvailableAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CheckEmailAvailableAJAX() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String mail=request.getParameter("param");
		if(mail!=null) {
			DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
			UserModelDS model= new UserModelDS(ds);
			try {
				if(model.doRetrieveByEmail(mail)==null) {
					out.write("true");
				}
				else {
					out.write("false");
					// non disponibile
				}
			}catch(SQLException e) {
				
			}
		}
		
	}

}
