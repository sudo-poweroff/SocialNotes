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
		String username = request.getParameter("inputUser");
		
		 DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		UserModelDS uModel = new UserModelDS(ds);
		System.out.println("USERNAME REQUEST: "+username);
		UserBean bean = new UserBean();
		try {
			bean = uModel.doRetrieveByUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
		//Controllo che un utente con quell'username esista
		if (bean!=null) {
			
			if(session.getAttribute("pin")==null) {
				Random r = new Random();
				int pin = r.nextInt(900000)+100000;
				session.setAttribute("pin", pin);
				System.out.println("Genero pin, salvo pin in sessione:"+pin);
				
			    String from = "socialnotes2021@gmail.com";
		        String pass = "Despacito21";
		        String[] to = { bean.getEmail() }; // list of recipient email addresses
		        String subject = "Pin di Social Notes";
		        String body = "Ciao "+bean.getUsername()+" il pin e' : "+pin+" ";
			   
			    SendEmail sendEmail = new SendEmail(from,pass,to,subject,body);
			  
			    sendEmail.SendMail();
			    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecoveryPassword.jsp");
				dispatcher.forward(request, response);
			}
			else {//il pin è in sessione
				System.out.println("Il pin è stato inviato");
				int pin = (int)session.getAttribute("pin");
				int pinInserito = Integer.parseInt(request.getParameter("inputPin"));
				System.out.println("PIN:"+pin+" Pin inserito dall'utente: "+pinInserito);
				if(pin==pinInserito) {
					RandomString gen = new RandomString(12, ThreadLocalRandom.current());
					String password = gen.nextString();
					System.out.println("NUOVA PASSWORD : "+password);
					
				    try {
						uModel.doUpdatePassword(username, password);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				    String from = "socialnotes2021@gmail.com";
			        String pass = "Despacito21";
			        String[] to = { bean.getEmail() }; // list of recipient email addresses
			        String subject = "CONFERMA DI CAMBIAMENTO PASSWORD SU Social Notes";
			        String body = "Ciao "+bean.getUsername()+" La tua nuova password e' : "+password+" ";
				    
				    SendEmail sendEmail = new SendEmail(from,pass,to,subject,body);
				    
				    sendEmail.SendMail();
				    session.removeAttribute("pin");
				    response.sendRedirect("login.jsp");
				}
				else {
					//Pin inserito scorretto
					request.setAttribute("errorPin", "Pin inserito non corretto");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecoveryPassword.jsp");
					dispatcher.forward(request, response);
				}
			}
		}
		else {//Se l'username non è presente nel DB
			request.setAttribute("errorUsername", "Username non trovato");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RecoveryPassword.jsp");
			dispatcher.forward(request, response);
		}
	}

}
