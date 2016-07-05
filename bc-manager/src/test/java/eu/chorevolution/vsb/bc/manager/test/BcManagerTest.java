package eu.chorevolution.vsb.bc.manager.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class BcManagerTest {
	public static void main(String[] args) {
		postRequest();
//		getRequest();
	}

//	private static void getRequest() {
//		String string = "";
//		try {
//			try {
//				URL url = new URL("http://localhost:1111/ref/efe");
//				URLConnection connection = url.openConnection();
//				connection.setDoOutput(true);
//				connection.setRequestProperty("Content-Type", "application/json");
//				connection.setConnectTimeout(5000);
//				connection.setReadTimeout(5000);
//				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//
//				out.write("/Users/Siddhartha/Documents/Academics/8thSem/inria/git/evolution-service-bus/bc-manager/src/main/resources/config.json,soap");
//
//				//        out.write(jsonObject.toString());
//
//				out.close();
//
//				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//				String res = "";
//				while ((res=in.readLine()) != null) {
//					System.out.println(res);
//				}
//				in.close();
//			} catch (Exception e) {
//				System.out.println(e);
//			}
//			//      br.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private static void postRequest() {
		String string = "";
		try {
			//      InputStream crunchifyInputStream = new FileInputStream("/home/siddhartha/Documents/CrunchifyTutorials/WebContent/WEB-INF/lib/CrunchifyJSON.txt");
			//      InputStreamReader crunchifyReader = new InputStreamReader(crunchifyInputStream);
			//      BufferedReader br = new BufferedReader(crunchifyReader);
			//      String line;
			//      while ((line = br.readLine()) != null) {
			//        string += line + "\n";
			//      }

			//      JSONObject jsonObject = new JSONObject(string);
			//      System.out.println(jsonObject);

			try {
				URL url = new URL("http://localhost:1111/interface");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

				out.write("/Users/Siddhartha/Documents/Academics/8thSem/inria/git/evolution-service-bus/bc-manager/src/main/resources/config.json,soap");

				//        out.write(jsonObject.toString());

				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String res = "";
				while ((res=in.readLine()) != null) {
					System.out.println(res);
				}
				in.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			//      br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}