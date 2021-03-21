package com.proper.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tenant {
	private String email;
	private String name;
	private List<Charge> charges;

	public Tenant() {
		email = "";
		name = "";
		charges = new ArrayList<Charge>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {

		return "Name :" + name + ", email :" + email;

	}

	public boolean addcharge(Charge charge) {

		for (int i = 0; i < charges.size(); i++) {
			if (charges.get(i).toString().equals(charge.toString())) {
				return false;
			}
		}
		return charges.add(charge);
	}

	public boolean removecharge(Charge c) {
		return charges.remove(c);
	}

	public List<Charge> getChargesFromTo(LocalDate From, LocalDate To) {
		List<Charge> ret = new ArrayList<Charge>();
		if (charges != null) {
			for (int i = 0; i < charges.size(); i++) {
				if ((charges.get(i).getChargeDate().isEqual(From) || charges.get(i).getChargeDate().isAfter(From))
						&& charges.get(i).getChargeDate().isBefore(To)) {
					ret.add(charges.get(i));
				}
			}
			return ret;
		}
		return null;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	public void deleteAllCharges() {
		charges.clear();
	}
}
