package it.unisa.control;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;


import it.unisa.model.CourseBean;
import it.unisa.model.CourseModelDS;
import it.unisa.model.FileBean;
import it.unisa.model.FileModelDS;
import it.unisa.model.MaterialBean;
import it.unisa.model.MaterialModelDS;

@WebServlet("/FileUploadServlet")
@MultipartConfig(maxFileSize = 1024*1024*50)  
public class FileUploadServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4001419296020204828L;

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// gets values of text fields
		//String description = request.getParameter("Descrizione");
		HttpSession session=request.getSession(true);
		if(session.getAttribute("username")==null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}
		InputStream is = null; // input stream of the upload file

		// obtains the upload file part in this multipart request
		FileBean file=new FileBean();
		MaterialBean material=new MaterialBean();
		Part filePart = request.getPart("Contenuto");
		Part materialPart=request.getPart("Anteprima");
		if (filePart != null) {
			// prints out some information for debugging
			//System.out.println(filePart.getName());
			//System.out.println(filePart.getSize());
			//System.out.println(filePart.getContentType());
			//inserimento informazioni nel filebean
			file.setFilename(filePart.getSubmittedFileName());
			file.setFormato(filePart.getContentType());
			file.setDimensione((int)filePart.getSize());
			is=filePart.getInputStream();
			file.setContenuto(is);
			
			//inserimento informazioni nel materialBean
			if(materialPart!=null) {
				Date dataCaricamento = new Date(System.currentTimeMillis());
				material.setDataCaricamento(dataCaricamento);
				String descrizione=(String)request.getParameter("Descrizione");
				System.out.println("Descrizione "+descrizione);
				material.setDescrizione(descrizione);
				material.setHidden(true);
				//ottengo la chiave esterna codiceCorso
				DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
				CourseModelDS course=new CourseModelDS(ds);
				String nome=(String) request.getParameter("Corso");
				System.out.println("nome corso in fileupserv: "+nome);
				int codiceCorso=course.doRetrieveByName(nome);
				//continuo inserimento dati
				material.setCodiceCorso(codiceCorso);
				material.setUsername((String)session.getAttribute("username"));
				material.setFileName(filePart.getSubmittedFileName());
				is=materialPart.getInputStream();
				material.setAnteprima(is);	
				
			}
		}



		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		FileModelDS fileModel= new FileModelDS(ds);
		MaterialModelDS materialModel=new MaterialModelDS(ds);
		try {
			fileModel.doSave(file);
			materialModel.doSave(material);
			String success = "Il materiale è stato caricato correttamente!";
			request.setAttribute("success", success);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homepage_user.jsp");
			dispatcher.forward(request, response);
		} 
		catch (SQLException e) {
			String error = "Spiacenti, la registrazione delle informazioni nel database non è andata a buon fine.";
			request.setAttribute("error", error);
			//Mando una alert 
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homepage_user.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		} 
	}
	
	
}