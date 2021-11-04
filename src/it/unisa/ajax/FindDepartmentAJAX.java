package it.unisa.ajax;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;  

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import it.unisa.model.DepartmentModelDS;
import it.unisa.model.UserModelDS;
import it.unisa.model.*;

/**
 * Servlet implementation class FindDepartmentAJAX
 */
@WebServlet("/FindDepartmentAJAX")
public class FindDepartmentAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindDepartmentAJAX() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		PrintWriter out = response.getWriter();
		String denominazione=request.getParameter("denominazione");
		if(denominazione!=null) {
			DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
			DepartmentModelDS model= new DepartmentModelDS(ds);
			try {
				Collection <DepartmentBean> departments = model.doRetrieveByDenominazione(denominazione);
				
				if(departments!=null&&departments.size()>0){
					Gson gson = new Gson();
			        String nameDepartments = gson.toJson(departments);
			        
			        System.out.println("PROVA: "+nameDepartments);
			        
			        out.write(nameDepartments);
				}
				
				
				
			}catch(SQLException e) {
				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
