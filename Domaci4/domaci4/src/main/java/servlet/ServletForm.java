package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet("/ServletForm")	// definisano je u web.xml
public class ServletForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AsistentData data = new AsistentData();
	
    public ServletForm() {
    	super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ArrayList<Asistent> asistenti = data.getAsistenti();
		
		out.println("<html><head><title>Domaci4</title></head><body>");
		
		out.println("<table><tr><th>Ime</th><th>Ocena</th></tr>");
		for (Asistent a : asistenti) {
			out.println("<tr><td>" + a.getIme() + "</td><td>" + String.format("%.2f",a.getProsek()) + "</td></tr>");
		}
	
		out.println("</table>");
		
		out.println("<h2>Oceni:</h2>");
		out.println("<form action='" + request.getContextPath() + request.getServletPath() + "' method='POST'>");
		out.println("Ime:  <input type='text' name='ime' required><br><br>");
		out.println("Ocena:  <input type='number' name='ocena' required><br><br>");
		out.println("<input type='submit' value='Submit'><br></form>");
		
		out.println("</body></html>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ime = request.getParameter("ime");
		String ocena = request.getParameter("ocena");
		
		System.out.println(ime + " " + ocena);
		if (ime != null && ocena != null) {
			data.oceni(ime, Double.parseDouble(ocena));
		}
		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}

}
