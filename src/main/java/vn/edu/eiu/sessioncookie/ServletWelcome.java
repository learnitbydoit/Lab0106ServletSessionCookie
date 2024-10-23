package vn.edu.eiu.sessioncookie;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/servlet-welcome"})
public class ServletWelcome extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//false: Chỉ lấy session nếu đã tồn tại, không tạo mới
		HttpSession session = req.getSession(false);
		String username = "";
		
		if(session != null) 
			username = (String) session.getAttribute("username");

		//test sesssion
		System.out.println("session username: " + username);
		
			
		if (username == null) {
            //Kiểm tra cookie
            Cookie[] cookies  = req.getCookies();
			if (cookies != null)
                for (Cookie cookie : cookies)  
                    if ("username".equals(cookie.getName())) 
                        username = cookie.getValue();
			//test
			System.out.println("cookie username: " + username);

		}
		
		resp.setContentType("text/html");
		
		if (username != null) { 
			resp.getWriter().println("<h4>Xin chào mừng bạn trở lại, " + username + "!</h4>");
			resp.getWriter().println("<br><a href='servlet-logout'>Logout</a>");
		}
		else {
			//Nếu không có session hoặc không có thuộc tính username trong session
			resp.getWriter().println("<h4>Please login first.</h4> <br>");
			resp.getWriter().println("<a href='servlet-login'>Please log in first.</a>");
			
		}
	}
}
