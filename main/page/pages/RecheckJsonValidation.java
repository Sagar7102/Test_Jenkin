package pages;

import java.io.File;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;


public class RecheckJsonValidation extends GlobalConstants{
	
	public static void main1(String args[]) throws Exception{
	URIBuilder builder = new URIBuilder();
	HttpClient httpclient = HttpClients.createDefault();
	String requiredIp = "52.74.239.12:3005";
	String requestEndpoint = "/api/v1/categoryPredictionByImage";
	builder.setScheme("http").setHost(requiredIp).setPath(requestEndpoint);
	URI uri = builder.build();
	HttpPost httppost = new HttpPost(uri);
	httppost.setHeader("host", "www.olx.hn");
	httppost.containsHeader("host");
	File file = new File("C:\\Users\\Sagar\\Desktop\\123_images\\60W_bulb.jpg");
	MultipartEntity mpentity = new MultipartEntity();
	ContentBody cbfile = new FileBody(file);
	mpentity.addPart("image", cbfile);
	boolean status = mpentity.equals(cbfile);
	List<String> li = new LinkedList();
//	if(status){
//		System.out.println(".equals method is not working");
//	}else{
//		System.out.println(".equals method is working fine");
//	}
	if(status)System.out.println("method for validation is working fine");
	if(!status)System.out.println("method for validation is not working");
	httppost.setEntity(mpentity);
	HttpResponse response = httpclient.execute(httppost);
	String responsestr = EntityUtils.toString(response.getEntity());
	System.out.println(responsestr);
	Assert.assertTrue("Response is not correct", !(responsestr.contains("error")));
	JSONParser parser = new JSONParser();
	Object obj = parser.parse(responsestr);
	JSONObject jobj = (JSONObject) obj;
	JSONArray arr = new JSONArray();
	arr = (JSONArray) jobj.get("data");
	for(Object obj3:arr){
		JSONObject jobj2 = (JSONObject) obj3;
		String id = jobj2.get("id").toString();
		li.add(id);
	}
	for(int i=0; i<li.size();i++){
		System.out.println("The respective category id that got selected is "+li.get(i));
	}
	verifyApiResponse(responsestr);
  }
	
	public static void verifyApiResponse(String responsestring) throws Exception{
		JSONParser parser = new JSONParser();
		String requiredIp;
		Object obj = parser.parse(responsestring);
		JSONObject jobj = (JSONObject) obj;
		JSONArray arr =  (JSONArray) jobj.get("data");
		for(Object obj1:arr){
			JSONObject jobj1 = (JSONObject) obj1;
			String id = jobj1.get("id").toString();
			System.out.println(id);
			JSONArray arr1 = new JSONArray();
			arr1 = (JSONArray) jobj1.get("id");
		//	arr.writeJSONString("data", "123");
		//	this.requiredIp = id;
		}
	}
}
