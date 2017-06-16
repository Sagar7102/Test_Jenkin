package pages;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import net.sourceforge.htmlunit.corejs.javascript.Parser;

import static pages.GlobalConstants.*;

public class MethodsForAPITests {
	
	public static String verifyResponseforTitleToCategoryPrediction(String tit) throws Exception{
		String hostip = requiredIp;
		String api = apiEndpointForTitleToCategoryPrediction;
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
		builder.setScheme("http").setHost(hostip).setPath(api).setParameter("title", tit);
		URI uri = builder.build();
		HttpGet httpget = new HttpGet(uri);
		httpget.setHeader("host", "www.olx.com.gh");
		HttpResponse response = httpclient.execute(httpget);
		String responseStr = EntityUtils.toString(response.getEntity());
		return responseStr;
	}
	
	public static void verifyResponseforImagetoCategoryPrediction(String path) throws Exception{
		String hostip = requiredIp;
		String requestEndpoint = apiEndpointForImageToCategoryPrediction;
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();		
		builder.setScheme("http").setHost(hostip).setPath(requestEndpoint);
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("host", "www.olx.hn");
		File file = new File(path);
		MultipartEntity mpentity = new MultipartEntity();
		ContentBody cbfile = new FileBody(file);
		mpentity.addPart("image", cbfile);
		httppost.setEntity(mpentity);
		HttpResponse response = httpclient.execute(httppost);
		String statuscode = response.getStatusLine().toString();
		System.out.println(statuscode);
		String responsestr = EntityUtils.toString(response.getEntity());
		System.out.println(responsestr);
		Assert.assertTrue("Error in response", !(responsestr.contains("error")));
	}

	public static String verifyUserTokenFromLoginApi(String UN, String password) throws Exception{
		String hostip = requiredIp;
		String requestEndpoint = apiEndpointForLogin;		
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
		builder.setScheme("http").setHost(hostip).setPath(requestEndpoint);
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("Host", "www.olx.hn");
		httppost.setHeader("xoriginolx", "Testing");
		httppost.setHeader("Authorization", "Basic d2ViOndlYg==");
		httppost.setHeader("Content-Type", "application/json");
		httppost.setHeader("X-OLX-Coming-From-Office", "1");		
		StringEntity parameters = addParametersforJsonContentType(UN, password);
		httppost.setEntity(parameters);
		HttpResponse response = httpclient.execute(httppost);		
		String id = verifyTheLoginResponseAndProvidetheUserToken(response);
	    return id;
	}
	@SuppressWarnings("unchecked")
	public static StringEntity addParametersforJsonContentType(String Username, String Password)throws Exception{
		JSONObject jobj = new JSONObject();
		jobj.put("grant_type", "password");
		jobj.put("username", Username);
		jobj.put("password", Password);
		StringEntity params = new StringEntity(jobj.toString());
		return params;
	}
	
	public static ArrayList<NameValuePair> addParametersForUrlEncodedContentType()throws Exception{
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("grant_type", "password"));
		params.add(new BasicNameValuePair("username", userEmail));
		params.add(new BasicNameValuePair("password", password));
		return params;
	}
	
	public static String verifyTheLoginResponseAndProvidetheUserToken(HttpResponse res) throws Exception{
		String UserToken ;
		JSONParser parse = new JSONParser();
		String responseString = EntityUtils.toString(res.getEntity());
		Object obj = parse.parse(responseString);
		JSONObject jobject = (JSONObject) obj;
		JSONObject jobject2 = (JSONObject)jobject.get("data");
	    UserToken = jobject2.get("access_token").toString();
		return UserToken;
	}
	
	@SuppressWarnings({ "deprecation" })
	public static String uploadImageAndProvideTheImageId(String UserToken, String path) throws Exception{
		String hostip = requiredIp;
		String requestEndpoint = apiEndpointForImageUploading;		
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
		builder.setScheme("http").setHost(hostip).setPath(requestEndpoint);
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("Host", "www.olx.hn");
		httppost.setHeader("Authorization", "Bearer "+UserToken);
		httppost.setHeader("x-origin-olx", "Testing");
		File file = new File(path);
		MultipartEntity mpentity = new MultipartEntity();
		ContentBody cbfile = new FileBody(file, "image/jpeg");
		mpentity.addPart("file", cbfile);
		httppost.setEntity(mpentity);
		HttpResponse response = httpclient.execute(httppost);
		String responseString = EntityUtils.toString(response.getEntity());
		System.out.println(responseString);
		String id = verifyTheResponseAndProvideImageId(responseString);
		return id;
	}
	
	public static String verifyTheResponseAndProvideImageId(String res) throws Exception{	
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(res);
		JSONObject jobj1 = (JSONObject) obj;
		JSONObject jobj2 = (JSONObject) jobj1.get("data");
		String ImageId = jobj2.get("id").toString();
		return ImageId;		
	}
	
	public static void postAnAdUsingUserTokenAndImageId(String UserToken, String ImageId) throws Exception{
		String hostip = requiredIp;
		String requestEndpoint = apiEndpointForPostingAd;
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
		builder.setScheme("http").setHost(hostip).setPath(requestEndpoint);
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("Host", "www.olx.hn");
		httppost.setHeader("xoriginolx", "Testing");
		httppost.setHeader("Authorization", "Bearer "+UserToken);
		httppost.setHeader("Content-Type", "application/json");
		httppost.setHeader("X-OLX-Coming-From-Office", "1");
		StringEntity params = addDataInPostRequest(ImageId);
		httppost.setEntity(params);
		HttpResponse response = httpclient.execute(httppost);
		String responseString = EntityUtils.toString(response.getEntity());
		String newLine = System.getProperty("line.separator");
		System.out.println("Response of posted ad is: "+newLine+responseString);
	}
	
	@SuppressWarnings("unchecked")
	public static StringEntity addDataInPostRequest(String imageid) throws Exception{
		JSONObject obj1 = new JSONObject();
		JSONObject obj2 = new JSONObject();
		obj2.put("title", "this is used for title");
		obj2.put("description", "this is used for description");
		obj2.put("category_id", 200);
		JSONArray arr1 = new JSONArray();
		JSONObject obj3 = new JSONObject();
		obj3.put("id", imageid);
		arr1.add(obj3);
		obj2.put("images", arr1);
		JSONArray arr2 = new JSONArray();
		JSONObject obj4 = new JSONObject();
		obj4.put("lat", 5.05);
		obj4.put("lon", 2);
		arr2.add(obj4);
		obj2.put("locations", arr2);
		JSONArray arr3 = new JSONArray();
		JSONObject obj5 = new JSONObject();
		JSONObject obj6 = new JSONObject();
		obj6.put("raw", 500);
		obj5.put("price", obj6);
		JSONObject obj7 = new JSONObject();
		obj7.put("phone", "123456789");
		arr3.add(obj5);
		arr3.add(obj7);
		obj2.put("parameters", arr3);
		JSONObject obj8 = new JSONObject();
		obj8.put("name", "panapiauto");
		obj2.put("user", obj8);
		obj1.put("data", obj2);
		StringEntity para = new StringEntity(obj1.toString());
		return para;
	}	
	
	public static void validateTitleToCategoryApiResponseAndConfidence(String res, String ti, BufferedWriter bw) throws Exception{
		String cat_id = "";
		String confidence = "";
		String title = "";
		String content = "";
		JSONParser parser = new JSONParser();
		Object obj1 = parser.parse(res);
		JSONObject jobj1 = (JSONObject) obj1;
		JSONArray arr = (JSONArray) jobj1.get("data");
		for(Object obj2:arr){
			JSONObject jobj2 = (JSONObject) obj2;
			cat_id = jobj2.get("id").toString();
			confidence = jobj2.get("confidence").toString();
			break ;
		}		
		title = ti.replaceAll(",", "");
		cat_id = cat_id.replaceAll(",", "");
		confidence = confidence.replaceAll(",", "");
		content = title+","+cat_id+","+confidence;
		bw.write(content+"\n");
		bw.flush();
	}
}
