package com.BookStore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CartManager")
public class CartManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> cart = new ArrayList<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		if (session.getAttribute("book") != null) {
			cart = (List<String>) session.getAttribute("book");
			cart.add(code);
			session.setAttribute("book", cart);
		} else {

			cart.add(code);
			session.setAttribute("book", cart);
		}

		//out.print(cart);
	
		if(request.getParameter("remove")!=null) {
			String rcode=request.getParameter("remove");
			
			for(int i=0;i<cart.size();i++) {
				if(cart.get(i).equals(rcode)) {
					cart.remove(i);
					//response.sendRedirect("WPbookStore/CartManager");
					
				}
			}
		}
		try {
			out.println("<html>");
			out.println("<html><body>");
			out.println("<h3>Cart-Details</h3>");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booksdata", "root", "root");
		
			for(String bookcode:cart) {
			
			String sql = "SELECT * from books where bcode="+bookcode+"";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			out.println("<hr>");
			while (rs.next()) {
				String bcode = rs.getString(1);
				String title = rs.getString(2);
				String author = rs.getString(3);
				String subject = rs.getString(4);
				int price = rs.getInt(5);
				

				out.println("<table border=2>");
				out.println("<tr>");
				out.println("<td>BCode</td>");
				out.println("<td>" + bcode + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Title</td>");
				out.println("<td>" + title + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Author</td>");
				out.println("<td>" + author + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Subject</td>");
				out.println("<td>" + subject + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td>Price</td>");


				out.println("<td>" + price + "</td>");
				out.println("</tr>");
				
				out.println("<tr>");
				out.println("<td><a href=\"/WPbookStore/CartManager?remove="+bcode+"\">Remove<a></td>");
				out.println("</tr>");
				
				out.println("</table>");
			}
			out.println("<hr>");

			out.println("</body></html>");
			}
		} catch (Exception e) {
			out.println(e);
		}

	}
}
