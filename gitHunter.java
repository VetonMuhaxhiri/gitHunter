import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.ProtocolException;

//Find the .git of given urls.
public class gitHunter {
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static void main(String[] args){
		String path = args[0];
		
		ArrayList urls = new ArrayList();
		try {
			
			FileReader r = new FileReader(path);
			BufferedReader br = new BufferedReader(r);
			String line;

			while((line = br.readLine()) != null) {
				String urlLink = "http://"+line+"/.git";
				urls.add(urlLink);		
			}
			br.close();
		}catch(ConnectException e) {
			e.getMessage();
		}catch(FileNotFoundException e) {
			System.out.println("filenotfound");
		}catch(IOException c) {
			System.out.println("ioexception");
		}
		
			Map responses = new HashMap();
			for(int i=0; i<urls.size();i++) {
				try {
					URL url = new URL(urls.get(i).toString());
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					conn.setConnectTimeout(3000);
					conn.setRequestMethod("GET");
					if (conn.getResponseCode() > 0) {
						responses.put(urls.get(i), " responded with "+conn.getResponseCode());
					}else {
						continue;
					}
					if(200<=conn.getResponseCode() && conn.getResponseCode()<=404) {
						BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
						StringBuilder sb = new StringBuilder();
						String output;
						while ((output = br.readLine()) != null) {
						  sb.append(output);
						}
						if (sb.toString().contains("Index of /.git")) {
							System.out.println(ANSI_GREEN+urls.get(i)+"    GIT FOUND !!!!");
						}
						if(conn.getResponseCode()==200) {
							System.out.println(ANSI_GREEN+urls.get(i)+" "+responses.get(urls.get(i)));
						}else if(conn.getResponseCode()==301) {
							System.out.println(ANSI_BLUE+urls.get(i)+" "+responses.get(urls.get(i)));
						}
						if(conn.getResponseCode()!=200 && conn.getResponseCode()!= 301) {
							System.out.println(ANSI_RED+urls.get(i)+" "+responses.get(urls.get(i)));
						}
					}
					else {
						System.out.println(ANSI_RED+urls.get(i)+" "+responses.get(urls.get(i)));
					}
					
				
				}catch(MalformedURLException e) {
					System.out.println("malformed url");
				}catch(ProtocolException e) {
					System.out.println("protocol exception");
				}catch(IOException e) {

				}
			}

	}

}
