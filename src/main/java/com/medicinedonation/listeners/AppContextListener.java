package com.medicinedonation.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.medicinedonation.db.DatabaseSetup;
import com.medicinedonation.model.Donation;
import com.medicinedonation.model.Donor;
import com.medicinedonation.model.Medicine;
import com.medicinedonation.model.Recipient;
import com.medicinedonation.model.Request;
import com.medicinedonation.model.User;

/**
 * Application lifecycle listener.
 * Initializes database tables at startup.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("==== Application Starting ====");

        // Create tables if they do not exist
        DatabaseSetup.setupDatabase(
            User.class,
            Donor.class,
            Recipient.class,
            Medicine.class,
            Donation.class,
            Request.class
        );

        // Optional: store global attributes in servlet context
        sce.getServletContext().setAttribute("appName", "MediDonate");

        System.out.println("==== Tables Created & App Initialized ====");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("==== Application Stopping ====");
        // Optional cleanup if needed
    }
}
