package com.proper.events;
import com.proper.domain.Charge;

public class ChargeAddedEvent extends Event {
	private Charge charge;
	
	public ChargeAddedEvent(Charge charge) {
		this.put(charge);
	}

	public Charge getCharge() {
		return charge;
	}

	public void put(Charge charge) {
		this.charge = charge;
	}

}
