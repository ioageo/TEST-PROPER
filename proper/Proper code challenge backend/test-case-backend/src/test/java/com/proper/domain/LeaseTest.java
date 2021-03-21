package com.proper.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.proper.events.ChargeAddedEvent;
import com.proper.events.Event;
import com.proper.lang.DateInterval;
import com.proper.transport.EventPublisher;

public class LeaseTest {
	@Mock
	private EventPublisher<Event> eventEventPublisher;

	private Lease lease = null;
	private Tenant t1 = null;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		lease = new Lease("first");
//		lease.getTenants().forEach(Tenant -> {
//			Tenant.deleteAllCharges();
//		});
//		lease.getTenants().clear();
		t1 = new Tenant();
		t1.setEmail("email1");
		t1.setName("name1");
		lease.setTenant(t1);
	}

	@Test(expected = Exception.class)
	public void registerCharge_ifChargeHasBeenAddedBefore_fails() throws RuntimeException {
		System.out.println("in");
		Charge charge = Charge.builder().chargeDate(LocalDate.now()).amount(Money.of(CurrencyUnit.USD, 10)).count(1)
				.description("description").type(Charge.Type.AdHoc).build();
	System.out.println(charge.toString());
		lease.registerCharge(charge);
		System.out.println("end of register");
		lease.registerCharge(charge);
		System.out.println("never to been seen");
		
	}

	@Test
	public void getCharges_returnOneInstance_forEachCharge() throws Exception {
		Charge charge1 = Charge.builder().chargeDate(LocalDate.now()).amount(Money.of(CurrencyUnit.USD, 10)).count(3)
				.description("charge1").type(Charge.Type.AdHoc).build();

		Charge charge2 = Charge.builder().chargeDate(LocalDate.now()).amount(Money.of(CurrencyUnit.USD, 10)).count(3)
				.description("charge2").type(Charge.Type.AdHoc).build();

		Charge charge3 = Charge.builder().chargeDate(LocalDate.now().plusMonths(2))
				.amount(Money.of(CurrencyUnit.USD, 10)).count(3).description("charge3").type(Charge.Type.AdHoc).build();
		lease.registerCharge(charge1);
		lease.registerCharge(charge2);
		lease.registerCharge(charge3);
		List<ChargeInstance> instances = lease.getCharges(DateInterval.from(LocalDate.now(), Duration.ofDays(30)));
		assertThat(instances).hasSize(2);
		assertThat(instances).anyMatch(elem -> elem.getDescription().equals("charge1"));
		assertThat(instances).anyMatch(elem -> elem.getDescription().equals("charge2"));
	}

	@Test
	public void registerCharge_ifChargeHasBeenAdded_fireEvent() throws IOException {
		Charge charge = Charge.builder().chargeDate(LocalDate.now()).amount(Money.of(CurrencyUnit.USD, 10)).count(1)
				.description("description").type(Charge.Type.AdHoc).build();
		lease.registerCharge(charge);
		verify(eventEventPublisher).put(any(ChargeAddedEvent.class));

	}

}
