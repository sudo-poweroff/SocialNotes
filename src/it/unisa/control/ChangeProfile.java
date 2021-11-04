package it.unisa.control;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

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


import it.unisa.model.PaymentMethodBean;
import it.unisa.model.PaymentMethodModelDS;
import it.unisa.model.UserBean;
import it.unisa.model.UserModelDS;
import it.unisa.utils.Validation;

/**
 * Servlet implementation class ChangeProfile
 */
@WebServlet("/ChangeProfile")
@MultipartConfig(maxFileSize = 1024*1024*5)
public class ChangeProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ChangeProfile() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//da fare controlli javascript anche con ajax
		
		String success =""; //I messaggi da mettere negli alert
		String error ="";
		
		
		HttpSession session = request.getSession(false);
		if(session==null) {
			 String linkLogin = "login.jsp";
			 String encodeURL = response.encodeRedirectURL(linkLogin);
			 response.sendRedirect(encodeURL);
		}
		
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		UserModelDS model_utente= new UserModelDS(ds);
		
		String username = (String)session.getAttribute("username");
		
//CAMBIO IMMAGINE PROFILO
		Part image=request.getPart("picture");
		InputStream is=null;
		if(image.getSize()>0) {
			//System.out.println("sto nell if ma non dovrei esserci");
			is=image.getInputStream();
			try {
				model_utente.doUpdateImage(username, is);
				success+=" Immagine di profilo aggiornata-";
				request.setAttribute("success", success);
				}
			catch(SQLException e) {
				System.out.println("Errore: Immagine di profilo non aggiornata");
				error+=" Errore immagine di profilo non aggiornata";
				request.setAttribute("error",error);	
				e.printStackTrace();
			}
			
			//aggiorno le varabili di sessione
			UserModelDS user=new UserModelDS(ds);
			UserBean bean;
			try {
				bean = user.doRetrieveByUsername(username);
				session.setAttribute("img",bean.getImg());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
//CAMBIO MAIL
		String mail = request.getParameter("mail");
		
		if(mail!=null && !mail.trim().equals("") && Validation.validateEmail(mail)) {
			try {
				model_utente.doUpdateEmail(username, mail);
				success+=" Email aggiornata-";
				request.setAttribute("success", success);
				}
			catch(SQLException e) {
				System.out.println("Errore: Email non aggiornata");
				error+=" Errore email non aggiornata";
				request.setAttribute("error",error);	
				e.printStackTrace();
			}
			//aggiorno le varabili di sessione
			UserModelDS user=new UserModelDS(ds);
			UserBean bean;
			try {
				bean = user.doRetrieveByUsername(username);
				session.setAttribute("email",bean.getEmail());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//CAMBIO UNI/DIPATRIMENTO
		String nomeuni = request.getParameter("nomeuni");
		//String indirizzo = request.getParameter("indirizzo");
		String dipartimento = request.getParameter("dipartimento");
		if(nomeuni!=null&&!nomeuni.equals(""))
			if(dipartimento!=null&&!dipartimento.equals("")) {
				try {
					model_utente.doUpdateDepartment(username, dipartimento,nomeuni);
					success+="Dipartimento aggiornato-";
					request.setAttribute("success", success);
					}
				catch(SQLException e) {
					System.out.println("Errore: Dipartimento non aggiornato");
					error+=" Errore dipartimento non aggiornato";
					request.setAttribute("error",error);	
					
					e.printStackTrace();
				}
				//aggiorno le varabili di sessione
				UserModelDS user=new UserModelDS(ds);
				UserBean bean;
				try {
					bean = user.doRetrieveByUsername(username);
					session.setAttribute("denominazione",bean.getDenominazione());
					session.setAttribute("dipName",bean.getDipName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
		
		
		
		
//CAMBIO PASSWORD
		String current_password = request.getParameter("current_password");
		String password = request.getParameter("password");
		String confirm_password = request.getParameter("confirm_password");
		
		if(current_password !=null && !current_password.trim().equals("")) {
			UserBean bean = new UserBean();
			//Vedo se la password � corretta
			try {
				bean = model_utente.checkLogin(username,current_password);
			}catch(SQLException e) {
				System.out.println("Errore checkLogin in ChangeProfile");
			}
			
			if(password!=null && !password.trim().equals("") && Validation.validatePassword(password)) {
				if(confirm_password!=null && !confirm_password.trim().equals("") && Validation.validatePassword(confirm_password)) {
					if(password.compareTo(confirm_password)==0){
						try {
							model_utente.doUpdatePassword(username, confirm_password);
							success+=" Password aggiornata-";
							request.setAttribute("success", success);
							}
						catch(SQLException e) {
							System.out.println("Errore: Password non aggiornata");
							error+=" Errore password non aggiornata";
							request.setAttribute("error",error);	
							
							e.printStackTrace();
						}
					}
				}
			}
			//aggiorno le varabili di sessione
			UserModelDS user=new UserModelDS(ds);
			UserBean b;
			try {
				b = user.doRetrieveByUsername(username);
				session.setAttribute("password",b.getPass());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
//AGGIUNTI NUOVA CARTA
		PaymentMethodModelDS model_carta= new PaymentMethodModelDS(ds);
		
		String nomecarta = request.getParameter("nomecarta");
		String cognomecarta = request.getParameter("cognomecarta");
		String numerocarta = request.getParameter("numcarta");
		String mesecarta = request.getParameter("month");
		String annocarta = request.getParameter("year");
		/*
		 * 
		 * 
		 * CVC non lo prendo e non lo controllo
		 * 
		 * 
		 * 
		 * 
		 * */
		if(nomecarta!=null && !nomecarta.trim().equals("")) {
			if(cognomecarta!=null && !cognomecarta.trim().equals("")) {
				if(numerocarta!=null && !numerocarta.trim().equals("") && numerocarta.length()==16) { //=16 vincolo DB
					if(mesecarta!=null && !mesecarta.trim().equals("")) {
						if(annocarta!=null && !annocarta.trim().equals("")) {
							Calendar calendar = Calendar.getInstance();
							calendar.set(Integer.parseInt(annocarta),Integer.parseInt(mesecarta), 1);
							Date datacarta = new Date(calendar.getTimeInMillis());
							PaymentMethodBean pbean = new PaymentMethodBean();
							pbean.setNomeIntestatario(nomecarta);
							pbean.setCognomeIntestatario(cognomecarta);
							pbean.setNumeroCarta(numerocarta);
							pbean.setDataScadenza(datacarta);
							pbean.setUsername(username);
							try {
								model_carta.doSave(pbean);
								success+=" Metodo di pagamento aggiunto-";
								request.setAttribute("success", success);
								
							}catch(SQLException e) {
								System.out.println("Errore: Metodo di pagamento non inserito\n");
								error+=" Errore: metodo di pagamento non aggiornato-";
								request.setAttribute("error",error);							
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		
		
//ELIMINA CARTA
		String eliminaCarta=request.getParameter("numcartaDelete");
		if(eliminaCarta!=null && !eliminaCarta.trim().equals("") && eliminaCarta.length()==16) {
			PaymentMethodModelDS pay=new PaymentMethodModelDS(ds);
			try {
				pay.doDeleteByNumber(eliminaCarta);
				success+=" Metodo di pagamento eliminato-";
				request.setAttribute("success", success);
			} catch (SQLException e) {
				System.out.println("Metodo di pagamento non eliminato");
				error+=" Errore: metodo di pagamento non eliminato-";
				request.setAttribute("error",error);
				e.printStackTrace();
			}
		}
		
		
		
		
		/*System.out.println("mail:"+mail+
				"\n nomeuni:"+nomeuni+
				"\n dipartimento:"+dipartimento+
				"\n current_password:"+current_password+
				"\n password:"+password+
				"\n confirm_password:"+confirm_password+
				"\n nomecarta:"+nomecarta+
				"\n cognomecarta:"+cognomecarta+
				"\n numerocarta:"+numerocarta+
				"\n mesecarta:"+mesecarta+
				"\n annocarta:"+annocarta+
				"\n username:"+username);*/
		
		
		doGet(request, response);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/change.jsp");
		dispatcher.forward(request, response);
		
	}

}
