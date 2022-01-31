package acquisto;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import materiale.MaterialBean;
import profilo.UserModelDS;

/**
 * Servlet implementation class buyMaterial
 */
@WebServlet("/buyMaterial")
public class buyMaterial extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public buyMaterial() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null) {
			response.sendRedirect("homepage.jsp");
		}
		DataSource ds=(DataSource)getServletContext().getAttribute("DataSource");
		
		Collection<MaterialBean>cart=(Collection<MaterialBean>)session.getAttribute("cart");
		String username = (String)session.getAttribute("username"); 
		int coin=(int)session.getAttribute("coin");
		String tot=request.getParameter("tot");
		int totale=Integer.parseInt(tot);
		
		
		PurchaseModelDS purchaseModel = new PurchaseModelDS(ds);
		
		if(totale==0) {
			request.setAttribute("emptyCart", "cartello vuoto");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/purchasedMaterial.jsp");
			dispatcher.forward(request, response);
		}else if(totale>coin) {
			request.setAttribute("notEnoughCoins", "coins non sufficienti");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/purchasedMaterial.jsp");
			dispatcher.forward(request, response);
		}
		else {
			if(cart!=null&&cart.size()>0){
				Iterator<?> it=cart.iterator();
				//Salvo l'acquisto di tutto il materiale presente nel carrello
				while(it.hasNext()) {
					PurchaseBean purchaseBean = new PurchaseBean();
				
					MaterialBean material=(MaterialBean)it.next();
					purchaseBean.setCodiceMateriale(material.getCodiceMateriale());
					purchaseBean.setUsername(username);						
					purchaseBean.setDataAcquisto(new Date(System.currentTimeMillis()));
					try {
						purchaseModel.doSave(purchaseBean);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				//svuoto il carrello
				cart.clear();
				UserModelDS userModel=new UserModelDS(ds);
				//aggiorno i coins all'utente
				try {
					userModel.doUpdateCoin(username, coin-totale);
				}catch(SQLException e) {
					e.printStackTrace();
				}
				session.setAttribute("coin", coin-totale);
				session.setAttribute("cart", cart);
				request.setAttribute("success", "Acquisto effettuato");
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/purchasedMaterial.jsp");
				dispatcher.forward(request, response);
							
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

}
