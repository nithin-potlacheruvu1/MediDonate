package com.medicinedonation.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.medicinedonation.model.User;
import com.medicinedonation.service.UserManager;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private UserManager userManager = new UserManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            
            // Check if user is logged in
            if (currentUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            int userId = currentUser.getUserId();
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // Validate inputs
            if (currentPassword == null || currentPassword.trim().isEmpty() ||
                newPassword == null || newPassword.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
                
                response.sendRedirect("changePassword.jsp?error=All fields are required!");
                return;
            }

            // Check if new password matches confirmation
            if (!newPassword.equals(confirmPassword)) {
                response.sendRedirect("changePassword.jsp?error=New password and confirmation password do not match!");
                return;
            }

            // Check password length
            if (newPassword.length() < 6) {
                response.sendRedirect("changePassword.jsp?error=New password must be at least 6 characters long!");
                return;
            }

            // Call UserManager to change password
            boolean passwordChanged = userManager.changePassword(userId, currentPassword, newPassword);

            if (passwordChanged) {
                response.sendRedirect("changePassword.jsp?success=Password changed successfully!");
            } else {
                response.sendRedirect("changePassword.jsp?error=Current password is incorrect!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("changePassword.jsp?error=Error changing password: " + e.getMessage());
        }
    }
}