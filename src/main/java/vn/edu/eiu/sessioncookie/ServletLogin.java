package vn.edu.eiu.sessioncookie;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/servlet-login"})
public class ServletLogin extends HttpServlet{
	/*
	 * Xử lý trang yêu cầu (client):
	 * 	browser nhập url --> kích hoạt doGet --> render giao diện đăng nhập
	 * */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		PrintWriter writer = resp.getWriter();
		
		writer.println("<form action='/Lab0106ServletSessionCookie/servlet-login' method='post'>");
		writer.println("<labe for='username'>Username</label>");
		writer.println("<input type='text' id='username' name='username'><br>");
		
		writer.println("<labe for='password'>Password:</label>");
		writer.println("<input type='password' id='password' name='password'><br>");
		
		writer.println("<input type='checkbox' id ='remember' name='remember'>");
		writer.println("<labe for='remember'>Remember me</label> <br>");
		
		writer.println("<input type='submit' value='login'>");
	}
	
	//Xử lý trang kiểm tra đăng nhập (server)
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Lưu thông tin client gửi tới
		String userName = req.getParameter("username");
		String passWD = req.getParameter("password");
		String remember =req.getParameter("remember");

		//test in console
		System.out.println(remember);
		
		//Kiểm tra đăng nhập
		if(userName.equalsIgnoreCase("thao") && passWD.equals("123456")) {
			
			//Tạo session lưu thông tin đăng nhập
			HttpSession session = req.getSession();
			session.setAttribute("username", userName);
			
			//set thời gian cho session là 10s
			session.setMaxInactiveInterval(10);
			
			//Nếu có đánh dấu nhớ thì add vào cookie
			if(remember != null && remember.equalsIgnoreCase("on")) {
				
				Cookie userCookie = new Cookie("username", userName);
				
				userCookie.setMaxAge(60*5);
				
				resp.addCookie(userCookie);
				
				//test 
				System.out.println("usercookie: " + userCookie.getValue());
			}
			
			//Chuyển đến trang welcome
			resp.sendRedirect("/Lab0106ServletSessionCookie/servlet-welcome");
			
		}
		else resp.sendRedirect("/Lab0106ServletSessionCookie/servlet-login");
	}
	
	
}
