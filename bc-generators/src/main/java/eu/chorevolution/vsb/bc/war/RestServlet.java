package eu.chorevolution.vsb.bc.war;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 * 
 *         CallStarter.java Created: 27 janv. 2016 Description:
 */
public class RestServlet extends HttpServlet {
	private Client client = null;

	public Client getClient() {
		if (client == null) {
			return client = new Client(Protocol.HTTP);
		} else
			return client;
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *          servlet request
	 * @param response
	 *          servlet response
	 * @throws ServletException
	 *           if a servlet-specific error occurs
	 * @throws IOException
	 *           if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			if (request.getParameter("param").equals("get")) {
				this.getClient();
				// client = new Client(Protocol.HTTP);
				Request request1 = new Request(Method.GET, "http://localhost:8585/getmessage");
				Response response1 = client.handle(request1);

				if (response1.getStatus().isSuccess()) {
					Representation result = response1.getEntity();
					String message = result.getText();
					if (!message.equals("empty")) {
						System.err.println("message: " + message);
						out.print(StringUtils.abbreviate(message, 480));
					}
				}
			} else
				out.print("Wrong parameter!");

		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *          servlet request
	 * @param response
	 *          servlet response
	 * @throws ServletException
	 *           if a servlet-specific error occurs
	 * @throws IOException
	 *           if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *          servlet request
	 * @param response
	 *          servlet response
	 * @throws ServletException
	 *           if a servlet-specific error occurs
	 * @throws IOException
	 *           if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
