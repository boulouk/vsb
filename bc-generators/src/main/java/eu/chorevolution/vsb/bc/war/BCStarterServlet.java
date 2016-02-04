package eu.chorevolution.vsb.bc.war;

import eu.chorevolution.vsb.bcs.weather.BCStarter;
import eu.chorevolution.vsb.bcs.weather.bc.RestServer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 * 
 *         CallStarter.java Created: 27 janv. 2016 Description:
 */
public class BCStarterServlet extends HttpServlet {

	private BCStarter bc = null;
	private RestServer rs = null;

	public BCStarter getBc() {
		if (bc == null)
			return bc = new BCStarter();
		else
			return bc;
	}

	public RestServer getRs() {
		if (rs == null)
			return rs = new RestServer();
		else
			return rs;
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

		this.getBc();
		this.getRs();

		try (PrintWriter out = response.getWriter()) {

			// BCStarter starter = new BCStarter();
			if (request.getParameter("param").equals("start")) {
				String url = this.bc.start();
				this.rs.start();
				out.print("The BC is started. The SOAP endopoint is published on: <a href='" + url + "?wsdl" + "'>" + url + "</a>");
			} else if (request.getParameter("param").equals("stop")) {
				this.rs.stop();
				out.print(this.bc.stop());
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
