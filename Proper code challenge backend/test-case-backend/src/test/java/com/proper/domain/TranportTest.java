package com.proper.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.proper.events.EmailSended;
import com.proper.transport.EventPublisherClass;

public class TranportTest {

	EventPublisherClass publisher;
	Charge charge;
	ChargeInstance chargeInstance;
	List <Object> list ;
	
	@Before
	public void setup() {
		publisher = new EventPublisherClass<>();
		charge = Charge.builder().chargeDate(LocalDate.now()).amount(Money.of(CurrencyUnit.USD, 30)).count(1)
				.description("description").type(Charge.Type.AdHoc).build();
		chargeInstance =ChargeInstance.builder().chargeDate(LocalDate.now()).amount(Money.of(CurrencyUnit.USD, 100))
				.description("description").type(Charge.Type.AdHoc).build();
		list = new ArrayList<>();

	}
	
	@Test
	public void TestEmtyList() {
		publisher.put(list);
		publisher.publish();
		assertTrue(publisher.getBeenPublished().size()==0); 
		
	}
	@Test
	public void TestOneObjektList() {
		publisher.put(charge);
		publisher.publish();
		assertTrue(publisher.getBeenPublished().size()==1);
		List<EmailSended>es=publisher.getBeenPublished();
		assertEquals(charge.toString(),es.get(0).getText().toString());
		
	}
	@Test
	public void TestChargeAndChargeInstanceList() {
		list.add(charge);
		list.add(chargeInstance);
		publisher.put(list);
		publisher.publish();
	//	System.out.println("Size :"+publisher.getBeenPublished().size());
		assertTrue(publisher.getBeenPublished().size()==2);
		List<EmailSended>es=publisher.getBeenPublished();
		for(int i=0;i<list.size();i++) {
			assertEquals(es.get(i).getText(),(list.get(i).toString()));
		}
		
	}

	@After
	public void tearDown() {
		list=null;
		publisher=null;
	}

}
