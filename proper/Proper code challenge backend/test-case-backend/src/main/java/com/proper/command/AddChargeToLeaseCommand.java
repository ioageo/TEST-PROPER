package com.proper.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.proper.domain.Charge;
import com.proper.transport.LeaseRepositoryClass;

public class AddChargeToLeaseCommand {


	public static Response execute(Request request) {

		return null;

	}

	public static class Request {

		public static void POSTRequest(String post_string, HttpURLConnection httpURLConnection) {

			try {

				httpURLConnection.setRequestMethod("POST");

//				httpURLConnection.setRequestProperty("Auth", "Token");
//				httpURLConnection.setRequestProperty("Data", "Value");

				httpURLConnection.setDoOutput(true);

				OutputStream outputStream = httpURLConnection.getOutputStream();
				outputStream.write(post_string.getBytes());
				outputStream.flush();
				outputStream.close();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
		public static Map<String, String> ReadRequest(HttpURLConnection httpURLConnection) {
			InputStreamReader inputStreamReader = null;
			try {
				inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
			} catch (IOException e) {

				e.printStackTrace();
			}
			String line = "";
			BufferedReader bf = new BufferedReader(inputStreamReader);
			StringBuilder response = new StringBuilder();
			try {
				while ((line = bf.readLine()) != null) {
					response.append(line);
				}
			} catch (IOException e) {

				e.printStackTrace();
			}


			String str=response.subSequence(response.indexOf("LeaseID"),response.indexOf(",", response.indexOf("LeaseID"))).toString();
			String str3 = str.substring(str.indexOf('"', str.indexOf('"')+1)+1,str.length()-1 );
			String str2=response.subSequence(response.indexOf("Charge"),response.indexOf("}", response.indexOf("Charge"))).toString();
			String str4 =str2.substring(str2.indexOf('"', str2.indexOf('"')+1)+1,str2.length()-1 );
			Map<String, String> map = new HashMap<String, String>();
			map.put("LeaseID", str3);
			map.put("Charge",str4);
			return map;

		}

	

	public static class Response {
		public static String getFullResponse(HttpURLConnection con, String str) throws IOException {
			StringBuilder fullResponseBuilder = new StringBuilder();

			fullResponseBuilder.append(con.getResponseCode()).append(" ").append(str).append("\n");

			con.getHeaderFields().entrySet().stream().filter(entry -> entry.getKey() != null).forEach(entry -> {

				fullResponseBuilder.append(entry.getKey()).append(": ");

				List<String> headerValues = entry.getValue();
				Iterator<String> it = headerValues.iterator();
				if (it.hasNext()) {
					fullResponseBuilder.append(it.next());

					while (it.hasNext()) {
						fullResponseBuilder.append(", ").append(it.next());
					}
				}

				fullResponseBuilder.append("\n");
			});

			Reader streamReader = null;

			if (con.getResponseCode() > 299) {
				streamReader = new InputStreamReader(con.getErrorStream());
			} else {
				streamReader = new InputStreamReader(con.getInputStream());
			}

			BufferedReader in = new BufferedReader(streamReader);
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}

			in.close();

			fullResponseBuilder.append("Response: ").append(content);

			return fullResponseBuilder.toString();
		}

	}
	public static Charge createChargeFromString(String string) {
		  
		
		String Current = string.substring(0,string.indexOf(" "));		
		String amount = string.substring(string.indexOf(" ")+1, string.indexOf(','));
		String date = string.substring(string.indexOf("chargeDate")+11, string.indexOf(',', string.indexOf("chargeDate")));
		String year=date.substring(0,4);
		String month=date.substring(5,7);
		String day=date.substring(8,10);
		String description = string.substring(string.indexOf("description")+12, string.indexOf(',', string.indexOf("description")+11));
		String typ = string.substring(string.indexOf("type")+5, string.indexOf(',', string.indexOf("type")));
		String count = string.substring(string.indexOf("count")+6, string.indexOf(',', string.indexOf("count")));
		String until = string.substring(string.indexOf("until")+6, string.indexOf(')', string.indexOf("until")));
		

		return Charge.builder().
				chargeDate(LocalDate.of(Integer.valueOf(year),Integer.valueOf(month),Integer.valueOf(day)))
				.amount(Money.of(CurrencyUnit.of(Current),Double.valueOf(amount)))
				.count(Integer.valueOf(count))
				.description(description)
				.build();
	  
  }

}
