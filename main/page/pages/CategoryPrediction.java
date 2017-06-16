package pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

@SuppressWarnings("deprecation")
public class CategoryPrediction {
	public static void verifyCategoryprediction(String requestUrl)throws Exception{
		StringBuffer result = new StringBuffer();
		String title = getTitlefromJsonfile();	
		requestUrl = requestUrl+title ;
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(requestUrl);
		request.addHeader("Host","www.olx.hn");
		
		try{
			HttpResponse response = client.execute(request);
			int responseCode = response.getStatusLine().getStatusCode();
			if(responseCode == 200){
				System.out.println("Successfully get the response");
				BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line="";
				while((line=reader.readLine())!=null){
					System.out.println(line);
				}
			}
		}catch(Exception e){
			
		}		
	}
	
	
	public static String getTitlefromJsonfile() throws Exception{
		String tit = "";
		String pathforJsonFile = "";
		JSONParser parser = new JSONParser();
		try{
          Object object = parser.parse(new FileReader("c:\\sample.json"));            
          //convert Object to JSONObject
          JSONObject jsonObject = (JSONObject)object;           
         
          //Reading the String
          String name = (String) jsonObject.get("Name");
          Long age = (Long) jsonObject.get("Age");
            
          //Reading the array
          JSONArray countries = (JSONArray)jsonObject.get("Countries");
            
          //Printing all the values
          System.out.println("Name: " + name);
          System.out.println("Age: " + age);
          System.out.println("Countries:");
          for(Object country : countries){
              System.out.println("\t"+country.toString());
          }
      }
      catch(FileNotFoundException fe){
          fe.printStackTrace();
      }
      catch(Exception e){
          e.printStackTrace();
      }
      tit = "iphone 5s";
	  return tit ;
	}
	public static void secondMethod(String url) throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		//con.setRequestProperty("Host", "www.olx.hn");
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
	}
	
	
	public static void verifyResponseforTitleToCategoryPrediction(String tit) throws Exception{
		String hostip = "52.74.239.12:3005";
		String api = "/api/v2/categories/suggest/";
		String request = hostip+api;
		System.out.println("Api request is: "+request);
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
	//	String tit = getTitlefromJsonfile();
		//builder.setScheme("http").setHost("52.74.239.12:3005").setPath("/api/v1/categories/suggest/").setParameter("title", "iphone");
		builder.setScheme("http").setHost(hostip).setPath(api).setParameter("title", tit);
		URI uri = builder.build();
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("host", "www.olx.co.za");
		HttpResponse response = httpclient.execute(httpget);
		String responseStr = EntityUtils.toString(response.getEntity());
		System.out.println(responseStr);
	}
	
	public static void verifyResponseforImagetoCategoryPrediction(String path) throws Exception{
		StringBuffer buffer = new StringBuffer();
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
		String requiredIp = "52.74.239.12:3005";
		String requestEndpoint = "/api/v1/categoryPredictionByImage";
		builder.setScheme("http").setHost(requiredIp).setPath(requestEndpoint);
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("host", "www.olx.hn");
		File file = new File(path);
		MultipartEntity mpentity = new MultipartEntity();
		ContentBody cbfile = new FileBody(file);
		mpentity.addPart("image", cbfile);
		httppost.setEntity(mpentity);
		HttpResponse response = httpclient.execute(httppost);
		String responsestr = EntityUtils.toString(response.getEntity());
		System.out.println(responsestr);
		Assert.assertTrue("Error in response", !(responsestr.contains("error")));
	}
//	public static void main(String args[]) throws Exception{
//		URIBuilder builder = new URIBuilder();
//		HttpClient httpclient = HttpClients.createDefault();
//		String requiredIp = "52.74.239.12:3005";
//		String requestEndpoint = "/api/v1/categoryPredictionByImage";
//		builder.setScheme("http").setHost(requiredIp).setPath(requestEndpoint);
//		URI uri = builder.build();
//		HttpPost httppost = new HttpPost(uri);
//		httppost.setHeader("host", "www.olx.hn");
//		File file = new File("C:\\Users\\Sagar\\Desktop\\123_images\\60W_bulb.jpg");
//		MultipartEntity mpentity = new MultipartEntity();
//		ContentBody cbfile = new FileBody(file);
//		mpentity.addPart("image", cbfile);
//		httppost.setEntity(mpentity);
//		HttpResponse response = httpclient.execute(httppost);
//		String responsestr = EntityUtils.toString(response.getEntity());
//		System.out.println(responsestr);
//		Assert.assertTrue("Response is not correct", !(responsestr.contains("error")));
//	}
	
	@SuppressWarnings("unchecked")
//	public static void verifyPostApiRequestforLogin() throws Exception{
	public static void main(String args[]) throws Exception{
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
		String requiredip = "10.0.42.148";
		String requiredEndpoint = "/api/v1/account";
		builder.setScheme("http").setHost(requiredip).setPath(requiredEndpoint);
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("Host", "www.olx.hn");
		httppost.setHeader("xoriginolx", "Testing");
		httppost.setHeader("Authorization", "Basic d2ViOndlYg==");
		httppost.setHeader("Content-Type", "application/json");
		httppost.setHeader("X-OLX-Coming-From-Office", "1");
		/*
		 * This set of code will be used, when we provide content type as urlencoded format
		 * 
		 * ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("grant_type", "password"));
		parameters.add(new BasicNameValuePair("username", "mayank@abc.com"));
		parameters.add(new BasicNameValuePair("password", "1234"));
		httppost.setEntity(new UrlEncodedFormEntity(parameters));*/
		/*
		 * The below set of code is used to pass the parameter in json format
		 * This will be used when the content type of api is of application/json type
		 */
		JSONObject jobj = new JSONObject();
		jobj.put("grant_type", "password");
		jobj.put("username", "mayank@abc.com");
		jobj.put("password", "1234");
		StringEntity params = new StringEntity(jobj.toString());
		httppost.setEntity(params);
		HttpResponse response = httpclient.execute(httppost);
		String responsestr = EntityUtils.toString(response.getEntity());
		JSONObject responseJsonObj = (JSONObject) response;
		System.out.println(responseJsonObj);
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse(responsestr);
//		JSONObject object = (JSONObject) obj;
//		JSONObject datalist = (JSONObject) object.get("data");
//		String id = datalist.get("access_token").toString();
		System.out.println(responseJsonObj);
	}
}
