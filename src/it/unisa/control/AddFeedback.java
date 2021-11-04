package it.unisa.control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.model.FeedbackBean;
import it.unisa.model.FeedbackModelDS;

/**
 * Servlet implementation class AddFeedback
 */
@WebServlet("/AddFeedback")
public class AddFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String urlHome = "login.jsp";
		if(session==null) {
			response.sendRedirect(urlHome);
		}
		String urlDocument = "/documentPreview.jsp";
		urlDocument = response.encodeURL(urlDocument);
        int rating = 0;
        if (request.getParameter("stars")!=null)
		rating = Integer.parseInt(request.getParameter("stars"));
        
		String commento = request.getParameter("commento");
		int codMateriale = Integer.parseInt(request.getParameter("codmateriale"));
		Timestamp dataFeed = new Timestamp(System.currentTimeMillis());
		String username = request.getParameter("username");
		
		System.out.println("STAMPA DATA: "+dataFeed);
		  DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		
		FeedbackModelDS fModel = new FeedbackModelDS(ds);
		FeedbackBean fBean = new FeedbackBean();
		
		System.out.println("Commento: "+commento);
	    fBean.setCodiceMateriale(codMateriale);
	    fBean.setCommento(commento);
	    fBean.setDataFeed(dataFeed);
	    fBean.setUsername(username);
	    fBean.setValutazione(rating);
	    
	    try {
			fModel.doSave(fBean);
			request.removeAttribute("stars");
			request.removeAttribute("codice");
			request.removeAttribute("username");
			request.removeAttribute("commento");
			request.setAttribute("codice", codMateriale);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(urlDocument);
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
