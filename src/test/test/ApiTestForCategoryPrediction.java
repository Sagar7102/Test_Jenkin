package test;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import pages.CategoryPrediction;

public class ApiTestForCategoryPrediction {
//	@Test
//	public void testApiResponse() throws ClientProtocolException{
//		try{
//		    String requestUrl = "http://52.74.239.12:3005/api/v1/categories/suggest/?title=iphone 5s";
//		    CategoryPrediction.secondMethod(requestUrl);
//		}catch (Exception e){
//			System.out.println(e.toString());
//		}		
//	}
//	@Test
//	public void testVerifyApiResponse() throws Exception{
//		try{
//			String host = "52.74.239.12:3005";
//			String apirequest = "/api/v1/categories/suggest/";
//			CategoryPrediction.verifyResponseforCategoryPrediction(host, apirequest);
//		}catch(Exception e){
//			System.out.println(e);
//		}
//	}
	
	public static void main(String args[]) throws URISyntaxException, ClientProtocolException, IOException{
		String pathforJsonFile = "D:\\categoryPredictionPositiveData.json";
		JSONParser parser = new JSONParser();
		try
        {
			Object object = parser.parse(new FileReader(pathforJsonFile));
            JSONObject jsonObject = (JSONObject)object;           
            JSONObject parentelement = (JSONObject) jsonObject.get("categoryPredictionTestData");
            JSONArray testJsonArr = (JSONArray) parentelement.get("positiveData");
            for(Object json : testJsonArr)
            {
            	JSONObject childelement = (JSONObject) json;
            	String title = childelement.get("title").toString();
            	System.out.println("The title is :"+title);
            	ApiRequestAndResponse(title);
            }
           
//            String name = (String) jsonObject.get("categoryPredictionTestData");     
//            JSONArray titleList1= (JSONArray) jsonObject.get("positiveData"); 
//            for(Object titles : titleList1){
//            	System.out.println(titles.toString());            	
//            }
        }
        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public static void ApiRequestAndResponse(String tit) throws Exception{
		String requestHeader = "10.0.42.148";
		String requestEndpoint = "/api/v2/categories/suggest/";
		String request = requestHeader+requestEndpoint;
		System.out.println("Api request is: "+request);
		CategoryPrediction.verifyResponseforTitleToCategoryPrediction(tit);
	}
}
