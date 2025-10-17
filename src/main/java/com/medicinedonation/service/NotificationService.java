package com.medicinedonation.service;

import com.medicinedonation.model.Trackable;

public class NotificationService {

    public void notifyUser(Object obj, String message) {
        if (obj instanceof Trackable) {
            System.out.println("Notification sent to " + obj.getClass().getSimpleName() + ": " + message);
            // Optional: integrate email, SMS, or in-app notifications
        } else {
            System.out.println("Object not trackable, skipping notification.");
        }
    }
}
