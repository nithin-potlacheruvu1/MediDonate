package com.medicinedonation.servlets;

import com.medicinedonation.model.Medicine;
import com.medicinedonation.service.MedicineManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/MedicineServlet")
public class MedicineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MedicineManager medicineManager = new MedicineManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("mname");
            String type = request.getParameter("mtype");
            String qtyParam = request.getParameter("mqty");

            // ✅ Validate medicine name (only alphabets)
            if (name == null || !name.matches("^[a-zA-Z]+$")) {
                response.sendRedirect("admin/medicine.jsp?error=invalidName");
                return;
            }

            // ✅ Validate quantity
            int quantity = Integer.parseInt(qtyParam);
            if (quantity <= 0) {
                response.sendRedirect("admin/medicine.jsp?error=invalidQty");
                return;
            }

            String status = "Available";

            MedicineManager mm = new MedicineManager();
            Medicine existingMed = mm.getMedicineByNameAndType(name.trim(), type.trim());

            if (existingMed != null) {
                // ✅ Medicine exists → increase quantity
                int newQty = existingMed.getQuantity() + quantity;
                existingMed.setQuantity(newQty);
                mm.updateMedicine(existingMed);
                response.sendRedirect("admin/medicine.jsp?updated=1");
            } else {
                // ✅ New medicine → insert
                Medicine medicine = new Medicine(name.trim(), type.trim(), quantity, status);
                mm.addMedicine(medicine);
                response.sendRedirect("admin/medicine.jsp?success=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin/medicine.jsp?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("admin/medicine.jsp");
    }
}
