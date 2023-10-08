package profilo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import it.unisa.utils.RandomString;
import it.unisa.utils.SendEmail;

/**
 * Servlet implementation class RecoveryPass
 */
@WebServlet("/RecoveryPass")
public class RecoveryPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecoveryPass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		UserModelDS uModel = new UserModelDS(ds);
		UserBean bean = null;
		try {
			bean = uModel.doRetrieveByUsername(username);
		} catch (SQLException | NullPointerException e) {
			//CR4 -> s el'utente non esiste
			request.setAttribute("errorUsername", "Username errato");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecoveryPassword.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//Controllo che un utente con quell'username esista
		if (bean!=null) {
			
			if(session.getAttribute("pin")==null) {
				Random r = new Random();
				int pin = r.nextInt(900000)+100000;
				session.setAttribute("pin", pin);
				
			    String from = "socialnotes2021@gmail.com";
		        String pass = "fxyffsvvabkrvqrj"; //CR4 -> token per inviare la mail
		        String[] to = { bean.getEmail() }; // list of recipient email addresses
		        String subject = "Pin recupero password Social Notes";
		        String body = "Ciao "+bean.getUsername()+", il pin per poter recuperare la password e' : "+pin+" ";
			    SendEmail sendEmail = new SendEmail(from,pass,to,subject,body);
			    sendEmail.SendMail();

			    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecoveryPassword.jsp");
				dispatcher.forward(request, response);
			}
			else {//il pin e' in sessione
				int pin = (int)session.getAttribute("pin");
				int pinInserito = 0;
				try {
					pinInserito = Integer.parseInt(request.getParameter("inputPin").trim());
				}
				catch(NumberFormatException e){
					//Pin inserito scorretto
					request.setAttribute("errorPin", "Pin inserito errato");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecoveryPassword.jsp");
					dispatcher.forward(request, response);
					return;
				}
				if(pin==pinInserito) {
				    session.removeAttribute("pin");
					session.setAttribute("usernameRecupero", username);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/setNewPassword.jsp");
					dispatcher.forward(request, response);
				}
				else {
					//Pin inserito scorretto
					request.setAttribute("errorPin", "Pin inserito errato");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecoveryPassword.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
		}
		else {//Se l'username non e' presente nel DB
			request.setAttribute("errorUsername", "Username errato");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecoveryPassword.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

}
