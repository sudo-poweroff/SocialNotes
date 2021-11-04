package it.unisa.control;

import java.io.IOException;
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


import it.unisa.model.ChatBean;
import it.unisa.model.ChatModelDS;
import it.unisa.model.MessageBean;
import it.unisa.model.MessageModelDS;
import it.unisa.model.ParticipationBean;
import it.unisa.model.ParticipationModelDS;

@WebServlet("/ChatCreateServlet")
public class ChatCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ChatCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		String loginURL = "login.jsp";
		 String adminURL = "admin.jsp";
		 String chat = "chat.jsp";
		
		if (session==null) {
			loginURL = response.encodeURL(loginURL);
			response.sendRedirect(loginURL);
			return;
		}if (session!=null) {
			if ((session.getAttribute("username"))!=null) {
				if (((int)session.getAttribute("role"))==1) {
					  adminURL = response.encodeURL(adminURL);
					  response.sendRedirect(adminURL);
					  return;
				}
					
			}
		}
		String titoloChat = null;
		String[] users = null;
		 titoloChat = request.getParameter("titolo");
		 //users = request.getParameterValues("amici");
		 if(request.getParameterValues("amici")==null) {
			 String error = "Errore nella creazione della chat";
			 request.setAttribute("error", error);
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(response.encodeURL("/creaChat.jsp"));
			 dispatcher.forward(request, response);
			 return;
		 }
		 users = request.getParameterValues("amici");
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		ChatModelDS chatModel = new ChatModelDS(ds);
		ParticipationModelDS pModel = new ParticipationModelDS(ds);
		
			ChatBean chatBean = new ChatBean();
		
		
		chatBean.setTitolo(titoloChat);
		try {
			chatModel.doSave(chatBean);
			ChatBean ChatBeanNew = new ChatBean();
			ChatBeanNew = chatModel.doRetrieveLast();
			int chatID = ChatBeanNew.getChatID();
			
			ParticipationBean pBean = new ParticipationBean();
			pBean.setUsername((String)session.getAttribute("username"));
			pBean.setChatID(chatID);
			
			try { 
				pModel.doSave(pBean);
			} catch (SQLException h) {
				h.printStackTrace();
			}
			
			for(String e: users) {
				ParticipationBean pBeanNew = new ParticipationBean();
				pBeanNew.setUsername(e);
				pBeanNew.setChatID(chatID);
				
				try { 
					pModel.doSave(pBeanNew);
				} catch (SQLException h) {
					h.printStackTrace();
				}
				
			}
			
			MessageModelDS messageModel=new MessageModelDS(ds);
			MessageBean firstMex=new MessageBean();
			Timestamp invioMex = new Timestamp(System.currentTimeMillis());
			String testo=""+(String)session.getAttribute("username")+" ha creato la chat";
			firstMex.setTesto(testo);
			firstMex.setDataInvio(invioMex);
			firstMex.setUsername((String)session.getAttribute("username"));
			firstMex.setChatID(chatID);
			try { 
				messageModel.doSave(firstMex);
			} catch (SQLException h) {
				h.printStackTrace();
			}
			
			chat = response.encodeURL(chat);
			response.sendRedirect(chat);
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


}
