package pages;

import static pages.GlobalConstants.apiEndpointForImageUploading;
import static pages.GlobalConstants.requiredIp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;

@SuppressWarnings("deprecation")
public class ApiResponse {
	//private final String USER_AGENT="Mozilla/5.0";
	
	public static String GetResponse() throws ClientProtocolException, IOException{
			StringBuffer result=new StringBuffer();
			HttpClient client=new DefaultHttpClient();
			HttpGet	request = new HttpGet("http://web.panamera.local/api/v1/categories/suggest/?title=Lumia 650 white");			

	/*		HttpGet request=new HttpGet(url); //for get request		
			HttpPost req = new HttpPost("");// for post request
			request.addHeader("x-origin-olx","{{xoriginolx}}");
			request.addHeader("Authorization", "Basic d2ViOndlYg==");*/
			
			HttpResponse response=client.execute(request);
			int responseCode=response.getStatusLine().getStatusCode();
			System.out.println("Response Code: " + responseCode);				
		try{
		    if(responseCode==200){
		    	System.out.println("Get Response is Successfull");
		    	BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    	String line="";
		    	while((line=reader.readLine())!=null){
		    		result.append(line);
		    		System.out.println(result.toString());
		    	}
		    }
		    return result.toString();		
	    }catch(Exception ex){
	    	result.append("Get Response Failed");
	    	return result.toString();
	    }
	}
	public static String ResponseForGetRequest() throws ClientProtocolException, IOException{
		StringBuffer result=new StringBuffer();
		String requestUrl = "web.panamera.local/api/v1/categories/suggest/?title=Lumia 650 white";
		String url = "http://www.letgo.com.ar/api/v1/categories/suggest?title=iphone";
		try{			
			HttpClient client=new DefaultHttpClient();
			HttpGet	request = new HttpGet(url);			

	/*		HttpGet request=new HttpGet(url); //for get request		
			HttpPost req = new HttpPost("");// for post request
			request.addHeader("x-origin-olx","{{xoriginolx}}");
			request.addHeader("Authorization", "Basic d2ViOndlYg==");*/
			
			HttpResponse response=client.execute(request);
			int responseCode=response.getStatusLine().getStatusCode();
			System.out.println("Response Code: " + responseCode);						
			if(responseCode==200){
				System.out.println("Get Response is Successfull");
				BufferedReader reader=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line="";
				while((line=reader.readLine())!=null){
				/*	result.append(line);					
					System.out.println(result.toString());		*/
					System.out.println(line);
				}
			}
	    return result.toString();		
    }catch(Exception ex){
    	result.append("Get Response Failed");
    	return result.toString();
    }
  }
	
	public static void main (String args[]) throws Exception{
		String hostip = requiredIp;
		String requestEndpoint = apiEndpointForImageUploading;		
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
		builder.setScheme("http").setHost(hostip).setPath(requestEndpoint);
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("Host", "www.olx.hn");
		httppost.setHeader("Authorization", "Bearer bf7281a8b1dc043d26e4516a01e79f689fcca4c4");
		httppost.setHeader("x-origin-olx", "Testing");
		File file = new File("C:\\Users\\Sagar\\Desktop\\300-300\\2.jpg");
		MultipartEntity mpentity = new MultipartEntity();
		ContentBody cbfile = new FileBody(file, "image/jpeg");
		mpentity.addPart("file", cbfile);
		httppost.setEntity(mpentity);
		HttpResponse response = httpclient.execute(httppost);
		String statuscode = response.getStatusLine().toString();
		if(statuscode.contains("201 Created")){
			System.out.println("The status for the response is: "+statuscode);
			String responseString = EntityUtils.toString(response.getEntity());
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(responseString);
			JSONObject jobj1 = (JSONObject) obj;		
			JSONObject jobj2 = (JSONObject) jobj1.get("data");
			if(jobj2.containsKey("id")){
				System.out.println("data key is present in the api response which is in json format");
			}else{
				System.out.println("data key is not present in the api response which is in json format");
			}
			String id = jobj2.get("id").toString();
			System.out.println("uploaded image id is: "+id);			
			System.out.println(responseString);			
			File schemafile = new File("C:\\Users\\Sagar\\Documents\\schema.json");
			File datafile = new File("C:\\Users\\Sagar\\Documents\\data1.json");
			if(JsonValidationUtils.isJsonValid(schemafile, datafile)){
				System.out.println("Valid response");
			}
		}
	}
}