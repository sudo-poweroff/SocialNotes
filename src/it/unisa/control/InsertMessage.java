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
import javax.sql.DataSource;

import it.unisa.model.MessageBean;
import it.unisa.model.MessageModelDS;

@WebServlet("/InsertMessage")
public class InsertMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message=request.getParameter("mex");
		String chatID = (String)request.getParameter("chatID");
		System.out.println(message+"  "+request.getParameter("username")+"  "+chatID);
		Timestamp invioMex = new Timestamp(System.currentTimeMillis());
		MessageBean mex=new MessageBean();
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		mex.setTesto(message);
		mex.setDataInvio(invioMex);
		mex.setUsername(request.getParameter("username"));
		mex.setChatID(Integer.parseInt(request.getParameter("chatID")));
		MessageModelDS messageModel=new MessageModelDS(ds);
		try {
			messageModel.doSave(mex);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/chat.jsp");
		dispatcher.forward(request, response);
	}

}
