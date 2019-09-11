package com.BookStore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookEntry")
public class BookEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;

	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/booksdata","root","root");

			String sql = "insert into books values(?,?,?,?,?)";
			ps = con.prepareStatement(sql);
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		
		
		
		// reads-request
		String BookCode = request.getParameter("bcode");
		String BookName = request.getParameter("bname");
		String BookTitle = request.getParameter("btitle");
		String BookAuthor = request.getParameter("bauthor");
		String BookSubject = request.getParameter("bsubject");
		// process
		try {
			ps.setString(1, BookCode);
			ps.setString(2, BookName);
			ps.setString(3, BookTitle);
			ps.setString(4, BookAuthor);
			ps.setString(5, BookSubject);
			
			ps.executeUpdate();
			out.println("Registration Completed");

		} catch (Exception e) {
			out.println(e);
		}

	}

}
