package it.unisa.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.mysql.cj.Session;

import it.unisa.model.MaterialBean;
import it.unisa.model.MaterialModelDS;
import it.unisa.model.UserBean;
import it.unisa.model.UserModelDS;

@WebServlet("/PrintAnteprima")
public class PrintAnteprima extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PrintAnteprima() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		String code=request.getParameter("codice");
		//System.out.println("Codice preso dalla request: "+code);
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		try{
			MaterialModelDS material=new MaterialModelDS(ds);
			MaterialBean bean=material.doRetrieveByKey(code);
			
			//Blob image=bean.getAnteprima();
			//InputStream is=image.getBinaryStream();
			//System.out.println("CodiceMateriale "+bean.getCodiceMateriale());
			InputStream is=bean.getAnteprima();
			OutputStream outStream = response.getOutputStream();
			byte[] buf = new byte[4096];
			int len = -1;

			//Write the file contents to the servlet response
			//Using a buffer of 4kb (configurable). This can be
			//optimized based on web server and app server
			//properties
			while ((len = is.read(buf)) != -1) {
				outStream.write(buf, 0, len);
			}

			outStream.flush();
			outStream.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
