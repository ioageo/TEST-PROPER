package com.proper.domain;

import com.proper.events.ChargeAddedEvent;
import com.proper.events.Event;
import com.proper.lang.DateInterval;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import org.joda.money.Money;

public class Lease {
	private String id;
	private List<Tenant> tenants;
	private List<Event> events;

	public Lease(String id) {
		this.id = id;
		tenants = new ArrayList<>();
		events = new ArrayList<>();

	}

	public void registerCharge(Charge charge) throws RuntimeException {
		// TODO: register the charge
		for (Tenant t : tenants) {

			if (t.addcharge(charge)) {
				// TODO: Broadcast the charge has been registered (EventPublisher)
				ChargeAddedEvent event = new ChargeAddedEvent(charge);
				event.setName(Clock.systemDefaultZone().toString());
				events.add(event);
				try {
					System.out.println("5");
					Sendbroadcast bc = new Sendbroadcast();
					System.out.println("5,5");
					bc.broadcast(event.toString());
					System.out.println("6");
					bc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				// TODO: Make sure the charge has not been added
				Event event = new ChargeAddedEvent(charge);
				event.setName("Charge exist " + Clock.systemDefaultZone().toString());
				events.add(event);
				throw new RuntimeException("getCharges Not implemented");
			}
		}

	}

	public List<ChargeInstance> getCharges(DateInterval interval) throws Exception {
		// TODO: return 1 ChargeInstance for each charge falling within the interval
		// throw new RuntimeException("getCharges Not implemented");

		List<ChargeInstance> ret = new ArrayList<>();
		List<Charge> ch = new ArrayList<>();

		for (int i = 0; i < tenants.size(); i++) {
			try {
				ch.addAll(tenants.get(i).getChargesFromTo(interval.getStart(), interval.getLastDay()));
			} catch (NullPointerException e) {

				System.err.println("No charges");
			}
		}

		for (Charge a : ch) {
			ret.add(ChargeToChargeInstance(a));
		}
		return ret;
	}

	private ChargeInstance ChargeToChargeInstance(Charge charge) {
		ChargeInstance ret = ChargeInstance.builder().chargeDate(charge.getChargeDate())
				.amount(Money.of(charge.getAmount())).description(charge.getDescription()).type(charge.getType())
				.build();
		return ret;

	}

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public List<Tenant> getTenants() {
		return tenants;
	}

	public void setTenants(List<Tenant> tenants) {
		this.tenants = tenants;
	}

	public void setTenant(Tenant tenant) {
		tenants.add(tenant);
	}

	public void addChargeToOneTenant(Tenant tenant, Charge charge) throws Exception {
		tenants.get(tenants.indexOf(tenant)).addcharge(charge);
		if (tenants.get(tenants.indexOf(tenant)).addcharge(charge)) {
			// TODO: Broadcast the charge has been registered (EventPublisher)
			ChargeAddedEvent event = new ChargeAddedEvent(charge);
			event.setName(Clock.systemDefaultZone().toString());
			events.add(event);
			Sendbroadcast bc = new Sendbroadcast();
			bc.broadcast(event.toString());

		} else {
			// TODO: Make sure the charge has not been added
			Event event = new ChargeAddedEvent(charge);
			event.setName("Charge exist " + Clock.systemDefaultZone().toString());
			events.add(event);
			throw new RuntimeException("getCharges Not implemented");
		}
	}

	public Event LastEvent() {
		return events.get(events.size() - 1);
	}

	public Event Pollevent() {
		return events.remove(events.size() - 1);
	}

}
