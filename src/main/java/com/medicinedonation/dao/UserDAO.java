package com.medicinedonation.dao;

import com.medicinedonation.model.User;
import java.util.List;

public interface UserDAO {
    void insertUser(User user) throws Exception;
    void updateUser(User user) throws Exception;
    void deleteUser(int userId) throws Exception;
    User getUserById(int userId) throws Exception;
    User getUserByUsername(String username) throws Exception;
    List<User> getAllUsers() throws Exception;
	User loginUser(String email, String password);
	User getUserByEmail(String email);
}
