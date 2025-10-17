package com.medicinedonation.ejb;

import javax.ejb.Remote;

@Remote
public interface RewardServiceEJBRemote {
	void addRewardPoints(int userId, String medicineType, int quantity) throws Exception;
}
