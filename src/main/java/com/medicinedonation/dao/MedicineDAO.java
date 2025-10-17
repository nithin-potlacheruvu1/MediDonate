package com.medicinedonation.dao;

import com.medicinedonation.model.Medicine;
import java.util.List;

public interface MedicineDAO {
    int insertMedicine(Medicine medicine) throws Exception;
    int updateMedicine(Medicine medicine) throws Exception;
    int deleteMedicine(int medicineId) throws Exception;
    Medicine getMedicineById(int medicineId) throws Exception;
    List<Medicine> getAllMedicines() throws Exception;
    int getMedicineIdByName(String name) throws Exception ;
}
