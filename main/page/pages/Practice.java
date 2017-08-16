package pages;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

public class Practice {
//	public static void main(String args[]) throws Exception{		
//		URIBuilder builder = new URIBuilder();
//		HttpClient client = HttpClients.createDefault();
//		builder.setScheme("https").setHost("").setPath("");
//		URI uri = builder.build();
//		HttpPost httppost = new HttpPost(uri);
//		File file = new File("");
//		MultipartEntity mpentity = new MultipartEntity();
//		ContentBody cbfile = new FileBody(file);
//		mpentity.addPart("image", cbfile);
//		httppost.setEntity(mpentity);
//		HttpResponse response = client.execute(httppost);
//		String responseString = EntityUtils.toString(response.getEntity());
//		System.out.println(responseString);
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse(responseString);
//		JSONObject jobj = (JSONObject) obj;
//		String objectq = jobj.get("").toString();
//		System.out.println(objectq);
//		System.out.println(jobj.get("").toString());
//	}
	public static void apitestForTitletoCategoryPrediction()throws Exception{
		URIBuilder builder = new URIBuilder();
		HttpClient httpclient = HttpClients.createDefault();
		builder.setScheme("http").setHost("52.74.239.12:3005").setPath("/api/v1/categories/suggest/").addParameter("title", "iphone 5s");
		URI uri = builder.build();
		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		String responseString = EntityUtils.toString(response.getEntity());
		System.out.println(responseString);
	}
	public static void main(String args[]) throws Exception{
		String s1 = "abc";
		String s2 = "abc";
		String s3 = new String("abc");
		if(s1==s2){
			System.out.println("s1 and s2 are equal");
		}
		if(s1==s3){
			System.out.println("s1 and s3 are equal");
		}
	}
}
