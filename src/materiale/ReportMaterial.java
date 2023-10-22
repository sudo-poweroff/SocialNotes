package materiale;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import profilo.ReportBean;
import profilo.ReportModelDS;

/**
 * Servlet implementation class ReportMaterial
 */
@WebServlet("/ReportMaterial")
public class ReportMaterial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportMaterial() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String urlHome = "login.jsp";
		if(session==null) {
			response.sendRedirect(urlHome);
		}
		
		String segnalazione = request.getParameter("segnalazione");
		String username = request.getParameter("username");
		int codiceMateriale = Integer.parseInt(request.getParameter("codmateriale"));
		
		if ((segnalazione.length()!=0)&&(segnalazione!=null)) {
			ReportBean rBean = new ReportBean();
			
			rBean.setMotivo(segnalazione);
			rBean.setStato(0);
			rBean.setUsername(username);
			
			DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
			ReportModelDS rModel = new ReportModelDS(ds);
			MaterialModelDS mModel = new MaterialModelDS(ds);
			
			try {
				MaterialBean bean=mModel.doRetrieveByKey(String.valueOf(codiceMateriale));
				String relativePath="WebContent\\material\\";
				ServletContext context = request.getServletContext();
				String absolutePath = context.getRealPath("");
				String[] path=absolutePath.split("\\\\");
				String effectivePath="";
				for(int i=0;i<path.length-3;i++)
					effectivePath+=path[i]+"\\";
				effectivePath+=relativePath+bean.getNomeFile();
				File fileToDelete = new File(effectivePath);
				fileToDelete.delete();
				mModel.doDelete(codiceMateriale);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("Il materiale non è stato cancellato");
			}
			
			try {
				rModel.doSave(rBean);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Errore nel caricamento della segnalazione");
			}
			
			request.setAttribute("successReport", "Il materiale è stato segnalato correttamente");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homepageNotesManager.jsp");
			dispatcher.forward(request, response);
			
		}
		
		
		//response.sendRedirect(response.encodeRedirectURL("homepage.jsp"));
		
		
	}

}
