package com.proper.transport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.proper.domain.Charge;
import com.proper.domain.Lease;
import com.proper.domain.Tenant;

public class LeaseRepositoryClass implements LeaseRepository {

	List<Optional<Lease>> LeaseList;

	public LeaseRepositoryClass() {
		LeaseList = new ArrayList<Optional<Lease>>();
	}

	@Override
	public Optional<Lease> fetch(String id)  {
		for(int i=0;i<LeaseList.size();i++) {
			if(LeaseList.get(i).get().getId().equals(id)) {
				return LeaseList.get(i);
			}		
		}
		return null ;
	}

	/**
	 * 
	 * @param lease
	 * @param charge
	 * @return true if Charge to all tenant of the lease is succesfull
	 */
	public Boolean addChargeTo(Lease lease, Charge charge) {
		try {
			lease.registerCharge(charge);
			return true;

		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param id     Lease
	 * @param charge
	 * @return true if Charge to all tenant of the lease with id is succesfull
	 */
	public Boolean fetchaddChargeAll(String id, Charge charge) {
		
			if(fetch(id)!=null) {
				try {
				fetch(id).get().registerCharge(charge);
				return true;
				}catch (RuntimeException run) {
				
					run.printStackTrace();
					return false;
				}
		}
			return false;
			
		
		
	}

	/**
	 * 
	 * @param id     Lease
	 * @param charge
	 * @return true if Charge to only one tenant of the lease with id is succesfull
	 */
	public Boolean fetchaddChargeOne(String id, Charge charge, Tenant tenant) {

		try {
			fetch(id).get().addChargeToOneTenant(tenant, charge);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addProperty(Optional<Lease> Property) {
		for (Optional<Lease> t : LeaseList) {
			if (t.get().getId().toString().equals(Property.get().getId().toString())) {
				return false;
			}
		}
		return LeaseList.add(Property);

	}

	public boolean removeProperty(String PropertyID) {
		for (int i = 0; i < LeaseList.size(); i++) {
			if (LeaseList.get(i).get().getId().toString().equals(PropertyID)) {
				return true;
			}
		}
		return false;

	}

}
