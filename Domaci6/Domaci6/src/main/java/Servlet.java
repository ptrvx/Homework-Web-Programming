
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.Asistent;


public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ArrayList<Asistent> asistenti = new ArrayList<Asistent>();
	private Date currentDate = new Date();
	
    public Servlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setAttribute("asistenti", asistenti);
		request.setAttribute("currentDate", currentDate);
		RequestDispatcher disp = request.getRequestDispatcher("/index.jsp");
		disp.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ime = request.getParameter("ime");
		String ocena = request.getParameter("ocena");
		
		add(ime, Integer.parseInt(ocena));
		
		doGet(request, response);
	}
	
	
	private synchronized void add(String ime, int ocena) {
		for (Asistent a : asistenti) {
			if (a.getName().equalsIgnoreCase(ime)) {
				a.add(ocena);
				return;
			}
		}

		asistenti.add(new Asistent(ime, ocena));
	}

}
