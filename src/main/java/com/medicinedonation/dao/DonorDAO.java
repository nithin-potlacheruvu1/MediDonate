package com.medicinedonation.dao;

import com.medicinedonation.model.Donor;

import java.sql.SQLException;
import java.util.List;

public interface DonorDAO {
    int insertDonor(Donor donor) throws SQLException, Exception;
    int updateDonor(Donor donor);
    int deleteDonor(int donorId);
    List<Donor> getAllDonors();
    Donor getDonorByUserId(int userId) throws Exception ;

}
