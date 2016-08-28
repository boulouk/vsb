package eu.chorevolution.vsb.artifact.war;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.chorevolution.vsb.bc.manager.BcManagerRestService;

public class StartBcManagerServlet extends HttpServlet {

	BcManagerRestService server = null;

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

		String op = request.getParameter("op");
		if(op.equals("start")) {
			System.out.println("StartBcManager Servlet start req");
			server.runBC();
			System.out.println("StartBcManager Servlet start req done");
			response.getWriter().println("BC started!");
		}
		else if(op.equals("stop")) {
			System.out.println("StartBcManager Servlet stop req");
			server.pauseBC();
			System.out.println("StartBcManager Servlet stop req done");
			response.getWriter().println("BC stopped!");
		} 
		else if(op.equals("startbcm")) {
			server = new BcManagerRestService(2232);
			response.getWriter().println("-> BC Manager started!");
		}
		else if(op.equals("stopbcm")) {
			if(server!=null) {
				server.stop();
				server = null;
				response.getWriter().println("stopped!");
			}
			else {
				response.getWriter().println("server is not running stopped!");
			}
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

	public static void main(String[] args) {
	}
	
}
