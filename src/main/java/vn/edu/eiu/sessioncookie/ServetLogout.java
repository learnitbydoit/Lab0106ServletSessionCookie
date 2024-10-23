package vn.edu.eiu.sessioncookie;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/servlet-logout" })
public class ServetLogout extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// hủy session
		HttpSession session = req.getSession(false);
		if (session != null)
			session.invalidate();

		// Xóa cookie "username"
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("username".equals(cookie.getName())) {
					cookie.setMaxAge(0); // Xóa cookie bằng cách đặt thời gian sống là 0
					resp.addCookie(cookie);
				}
			}
		}
		resp.setContentType("text/html");
		resp.getWriter().println("You have logged out successfully!");
		resp.getWriter().println("<br><a href='servlet-login'>Go to login</a>");
	}

}
