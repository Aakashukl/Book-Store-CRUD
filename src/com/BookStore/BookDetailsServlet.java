package com.BookStore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BookDetailsServlet
 */
@WebServlet("/BookDetailsServlet")
public class BookDetailsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * private int count; public void init() { count=0;}
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// count++;

		String code = request.getParameter("code");
		PrintWriter out = response.getWriter();

		try {
			/*
			 * HttpSession session = request.getSession(); session.invalidate();
			 */

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/booksdata", "root", "root");
			String sql = "SELECT * from books where bcode=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(code));
			ResultSet rs = ps.executeQuery();
			out.println("<html>");
			out.println("<html><body>");
			out.println("<h3>Book-Details</h3>");
			out.println("<hr>");
			while (rs.next()) {
				String bcode = rs.getString(1);
				String title = rs.getString(2);
				String author = rs.getString(3);
				String subject = rs.getString(4);
				int price = rs.getInt(5);

				Cookie[] ck = request.getCookies();

				for (Cookie c : ck) {
					if (c.getName().equals("counts")) {
						Integer count = Integer.parseInt(c.getValue());
						count = count +1;
						c.setValue(count.toString());
						response.addCookie(c);
						out.println("count is :" + count);
						out.println("<br/>");
						
						double newprice = 0;
						/*if (count < 10) {
							out.println("<td>" + price + "</td>");
						}*/
						
						if(count >=10 && count <20) {
							newprice = price * (0.1)+price;
							price = (int) newprice;
						}
						else if(count>=20) {
							newprice = price *(0.2) + price;
							price = (int) newprice;
						}
					}
					else {
						Cookie visit=new Cookie("counts", "0");
						visit.setMaxAge(60*60*24*7);
						response.addCookie(visit);
					}
				}

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

				/*
				 * By HTTP SESSION HttpSession countSession = request.getSession(); if
				 * (countSession.getAttribute("count") != null) { String countStr = (String)
				 * countSession.getAttribute("count"); int count = Integer.parseInt(countStr) +
				 * 1; countSession.setAttribute("count", count + ""); } else {
				 * countSession.setAttribute("count", "1"); }
				 * 
				 * double newprice = 0; if (Integer.parseInt((String)
				 * countSession.getAttribute("count")) < 10) { out.println("<td>" + price +
				 * "</td>"); out.println("5 : price : " + Integer.parseInt((String)
				 * countSession.getAttribute("count")));
				 * 
				 * } else if (Integer.parseInt((String) countSession.getAttribute("count")) >=
				 * 10 && Integer.parseInt((String) countSession.getAttribute("count")) < 20) {
				 * newprice = price * (0.1) + price; out.println("<td>" + newprice + "</td>");
				 * out.println("10 : price : " + Integer.parseInt((String)
				 * countSession.getAttribute("count")));
				 * 
				 * } else if (Integer.parseInt((String) countSession.getAttribute("count")) >=
				 * 20) { newprice = price * (0.2) + price; out.println("<td>" + newprice +
				 * "</td>"); out.println("20 : price : " + Integer.parseInt((String)
				 * countSession.getAttribute("count"))); }
				 */
				// By Cookies;
				
				out.println("<td>" + price + "</td>");
				out.println("</tr>");
				out.println("</table>");
			}
			out.println("<hr>");
			out.println("<a href=CartManager?code=" + code + ">Add-To-Cart</a><br>");
			out.println("<a href=SubjectPageServlet>Subject-Page</a><br>");
			out.println("<a href=buyerpage.jsp>Buyer-Page</a><br>");
			out.println("</body></html>");

		} catch (Exception e) {
			out.println(e);
		}
	}
}
