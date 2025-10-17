package com.medicinedonation.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.medicinedonation.model.Recipient;
import com.medicinedonation.model.User;
import com.medicinedonation.service.RecipientManager;

@WebServlet("/RecipientServlet")
public class RecipientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RecipientManager recipientManager = new RecipientManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Check if recipient already exists
        Recipient existingRecipient = null;
		try {
			existingRecipient = recipientManager.getRecipientByUserId(user.getUserId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (existingRecipient != null) {
            request.setAttribute("message", "You are already registered as a Recipient. Your ID: " + existingRecipient.getUserId());
            request.getRequestDispatcher("recipient.jsp").forward(request, response);
            return;
        }

        Recipient recipient = new Recipient();
        recipient.setUserId(user.getUserId()); 
        try {
			recipientManager.addRecipient(recipient);
		} catch (Exception e) {
			e.printStackTrace();
		}


        try {
        	
            recipientManager.addRecipient(recipient);
            int id = recipient.getUserId();
            request.setAttribute("message", "Recipient record created! Your Recipient ID: " + id);
            request.getRequestDispatcher("recipient.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to create Recipient record.");
            request.getRequestDispatcher("recipient.jsp").forward(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("recipient.jsp");
    }
}
