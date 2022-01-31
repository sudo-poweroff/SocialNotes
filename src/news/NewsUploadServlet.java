package news;

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

import materiale.FileBean;
import materiale.FileModelDS;

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
				fileModel.doSave(file);
			newsModel.doSave(news);
			int idFile=fileModel.doRetrieveKey();
			int codiceNews=newsModel.doRetrieveKey();
			content.setIdFile(idFile);
			content.setCodiceNews(codiceNews);
			contentModel.doSave(content);
			String success = "Caricamento news effettuato";
			request.setAttribute("success", success);
		} 
		catch (SQLException e) {
			String error = "Spiacenti, la registrazione delle informazioni nel database non è andata a buon fine.";
			request.setAttribute("error", error);
			//Mando una alert 
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homepageNewsManager.jsp;jsessionid="+session.getId());
		dispatcher.forward(request, response);
	}

}
