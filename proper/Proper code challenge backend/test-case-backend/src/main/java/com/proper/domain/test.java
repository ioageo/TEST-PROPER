package com.proper.domain;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.proper.domain.Charge.ChargeBuilder;

public class test {

	public static void main(String[] args) {

		Charge charge = Charge.builder().chargeDate(LocalDate.now()).amount(Money.of(CurrencyUnit.USD, 10)).count(1)
				.description("description").type(Charge.Type.AdHoc).build();

		String str = charge.toString();
		System.out.println(str);
		String Current = str.substring(str.indexOf("amount")+7,str.indexOf(" ", str.indexOf("amount") ));
		System.out.println(Current);
		//String amount = str.substring(18, str.indexOf(','));
		String amount = str.substring(str.indexOf((" "),str.indexOf("amount"))+1, str.indexOf(',', str.indexOf("amount")));
		System.out.println(amount);
		String date = str.substring(str.indexOf("chargeDate")+11, str.indexOf(',', str.indexOf("chargeDate")));
		System.out.println(date);
		String year=date.substring(0,4);
		String month=date.substring(5,7);
		String day=date.substring(8,10);
		System.out.println(year+ " " +month+" "+day);
		String description = str.substring(str.indexOf("description")+12, str.indexOf(',', str.indexOf("description")+11));
		System.out.println(description);
		String typ = str.substring(str.indexOf("type")+5, str.indexOf(',', str.indexOf("type")));
		System.out.println(typ);
		String count = str.substring(str.indexOf("count")+6, str.indexOf(',', str.indexOf("count")));
		System.out.println(count);
		
		String until = str.substring(str.indexOf("until")+6, str.indexOf(')', str.indexOf("until")));
		System.out.println(until);

		Charge charge2 = Charge.builder().
				chargeDate(LocalDate.of(Integer.valueOf(year),Integer.valueOf(month),Integer.valueOf(day)))
				.amount(Money.of(CurrencyUnit.of(Current),Double.valueOf(amount)))
				.count(Integer.valueOf(count))
				.description(description)
				.build();

				
		System.out.println(charge2);

	}

	

}
