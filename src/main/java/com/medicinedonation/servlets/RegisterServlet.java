package com.medicinedonation.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicinedonation.dao.UserDAO;
import com.medicinedonation.daoImpl.UserDAOImpl;
import com.medicinedonation.model.User;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String password = request.getParameter("password").trim();
        String email = request.getParameter("email").trim();
        String phone = request.getParameter("phone").trim();
        String address = request.getParameter("address").trim();

        UserDAO userDAO = new UserDAOImpl();

        try {
            if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                request.setAttribute("errorMessage", "Invalid email format.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?#&])[A-Za-z\\d@$!%*?#&]{8,20}$")) {
                request.setAttribute("errorMessage",
                        "Password must be 8-20 chars, with uppercase, lowercase, number, and special character.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            if (userDAO.getUserByEmail(email) != null) {
                request.setAttribute("errorMessage", "Email already exists!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            if (!phone.matches("^[6-9]\\d{9}$")) { 
                request.setAttribute("errorMessage", "Invalid phone number. It must be a 10-digit number starting with 6-9.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }


            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setAddress(address);
            userDAO.insertUser(user);

            request.setAttribute("message", "Registration successful! You can now login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
