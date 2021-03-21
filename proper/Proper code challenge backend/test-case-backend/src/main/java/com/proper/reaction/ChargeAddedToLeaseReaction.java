package com.proper.reaction;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import com.proper.domain.ChargeInstance;
import com.proper.domain.Lease;
import com.proper.events.Event;
import com.proper.lang.DateInterval;
import com.proper.transport.EventPublisherClass;

public class ChargeAddedToLeaseReaction {
	private Lease lease;

	ChargeAddedToLeaseReaction(Lease lease) {
		this.lease = lease;
	}

	// TODO: Poll 1 event
	Event PollOneEvent() {
		return lease.Pollevent();
	}

	// TODO: Fetch all charges instances from the Lease for the next 30 days
	public List<ChargeInstance> Fetch() {
		try {
			return lease.getCharges(DateInterval.from(LocalDate.now(), Duration.ofDays(30)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// TODO: Notify the tenants with an email
	public void Publish() {
		EventPublisherClass<Event> pl = new EventPublisherClass();
		pl.put(Fetch());
		pl.publish();
	}
}
