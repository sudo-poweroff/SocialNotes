package it.unisa.control;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
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
import javax.swing.text.html.parser.ContentModel;

import it.unisa.model.ContentBean;
import it.unisa.model.ContentModelDS;
import it.unisa.model.FileBean;
import it.unisa.model.FileModelDS;
import it.unisa.model.NewsBean;
import it.unisa.model.NewsModelDS;

@WebServlet("/NewsUploadServlet")
@MultipartConfig(maxFileSize = 1024*1024*50) 
public class NewsUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewsUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("username")==null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}
		InputStream is = null;
		FileBean file=new FileBean();
		Part filePart = request.getPart("img");
		if (filePart != null) {
			file.setFilename(filePart.getSubmittedFileName());
			file.setFormato(filePart.getContentType());
			file.setDimensione((int)filePart.getSize());
			is=filePart.getInputStream();
			file.setContenuto(is);
		}
		String titolo=request.getParameter("titolo");
		String argomento=request.getParameter("argomento");
		String contenuto=request.getParameter("contenuto");
		Date dataCaricamento = new Date(System.currentTimeMillis());
		NewsBean news=new NewsBean();
		news.setTitolo(titolo);
		news.setArgomento(argomento);
		news.setContenuto(contenuto);
		news.setDataCaricamento(dataCaricamento);
		news.setUsername((String)session.getAttribute("username"));
		

		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		FileModelDS fileModel= new FileModelDS(ds);
		NewsModelDS newsModel=new NewsModelDS(ds);
		ContentModelDS contentModel=new ContentModelDS(ds);
		try {
			ContentBean content=new ContentBean();
			if(!fileModel.isPresent(file.getFilename()))
				fileModel.doSave(file);
			//System.out.println("Ho salvato il file");
			newsModel.doSave(news);
			//System.out.println("Ho salvato la news");
			content.setFileName(file.getFilename());
			int codiceNews=newsModel.doRetrieveKey();
			//System.out.println(codiceNews);
			content.setCodiceNews(codiceNews);
			contentModel.doSave(content);
			//System.out.println("Ho salvato la riga in Contenuto");
			String success = "Caricamento news effettuato";
			request.setAttribute("success", success);
		} 
		catch (SQLException e) {
			String error = "Spiacenti, la registrazione delle informazioni nel database non è andata a buon fine.";
			request.setAttribute("error", error);
			//Mando una alert 
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/notizie.jsp");
		dispatcher.forward(request, response);
	}

}
