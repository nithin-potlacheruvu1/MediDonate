package com.medicinedonation.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.medicinedonation.dao.UserDAO;
import com.medicinedonation.daoImpl.UserDAOImpl;
import com.medicinedonation.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        HttpSession session = request.getSession();

        // ✅ Admin login
        if ("admin@gmail.com".equalsIgnoreCase(email) && "Admin@123".equals(password)) {
            session.setAttribute("isAdmin", true);
            session.setAttribute("user", null); // make sure normal user session is cleared
            response.sendRedirect(request.getContextPath() + "/admin/admin.jsp");
            return;
        }

        // ✅ Normal user login
        UserDAO userDAO = new UserDAOImpl();
        User user = userDAO.loginUser(email, password);

        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", false);

            // Optional: add donor info if available
            try {
                com.medicinedonation.service.DonorManager donorManager = new com.medicinedonation.service.DonorManager();
                com.medicinedonation.model.Donor donor = donorManager.getDonorByUserId(user.getUserId());
                session.setAttribute("donor", donor);
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("error", "Invalid email or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
