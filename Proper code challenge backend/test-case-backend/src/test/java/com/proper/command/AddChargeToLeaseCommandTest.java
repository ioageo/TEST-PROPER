package com.proper.command;



import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.time.LocalDate;

import java.util.Map;

import com.proper.command.AddChargeToLeaseCommand.Response;
import com.proper.domain.Charge;
import com.proper.domain.Lease;
import com.proper.transport.LeaseRepositoryClass;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.time.LocalDate;

import java.util.Map;
import java.util.Optional;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddChargeToLeaseCommandTest {
//  TODO: Fetch the lease from the repository
//  TODO: Add the charge
//  TODO: Return response

	LeaseRepositoryClass LeaseRepositoryClass = new LeaseRepositoryClass();
	URL url = null;
	HttpURLConnection httpURLConnection = null;
	InputStreamReader inputStreamReader = null;
	Charge charge = null;
	Optional<Lease> lease;

	@Before
	public void setup() {
		try {
			url = new URL("https://postman-echo.com/post");

			httpURLConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		charge = Charge.builder().chargeDate(LocalDate.now()).amount(Money.of(CurrencyUnit.USD, 10)).count(1)
				.description("description").type(Charge.Type.AdHoc).build();

		lease = Optional.of(new Lease("first"));
		LeaseRepositoryClass.addProperty(lease);
	}

	@Test
	public void Test1() {
		

		AddChargeToLeaseCommand.Request.POSTRequest("LeaseID=first&" + charge.toString(), httpURLConnection);


		Map<String, String> RequestMap = AddChargeToLeaseCommand.ReadRequest(httpURLConnection );
		Charge charge2 = AddChargeToLeaseCommand.createChargeFromString(RequestMap.get("Charge").toString());
		
		
		//Test if all correct
		assertTrue(LeaseRepositoryClass.fetchaddChargeAll(RequestMap.get("LeaseID").toString(), charge2));
		
		// Lease ID do not exist or misspeld
		assertFalse(LeaseRepositoryClass.fetchaddChargeAll(RequestMap.get("LeaseID").toString().toUpperCase(), charge));
		try {
		System.out.println(	AddChargeToLeaseCommand.Response.getFullResponse(httpURLConnection, "OK"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

}