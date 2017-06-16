package test;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import static pages.GlobalConstants.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import static pages.MethodsForAPITests.*;

public class APITest {
	@Test
	/*
	 * @Author: Sagar
	 * @Desc: Read title form JSON file and for each title, hit the API request for title to category prediction 
	 */
	public void testVerifyApiResponseForTitleToCategoryPrediction() throws Exception{
		String pathforJsonFile = "D:\\test.json";
		String pathforResponseResult = "D:\\TitleToCategoryApiResponse.csv";
		FileWriter fw = new FileWriter(pathforResponseResult);
		BufferedWriter bw = new BufferedWriter(fw);
		JSONParser parser = new JSONParser();
		try{
			Object object = parser.parse(new FileReader(pathforJsonFile));
            JSONObject jsonObject = (JSONObject)object;           
            JSONObject parentelement = (JSONObject) jsonObject.get("categoryPredictionTestData");
            JSONArray testJsonArr = (JSONArray) parentelement.get("positiveData");
            for(Object json : testJsonArr){
            	JSONObject childelement = (JSONObject) json;
            	String title = childelement.get("title").toString();
            	System.out.println("The title is: "+title);
            	String response = verifyResponseforTitleToCategoryPrediction(title);
            	validateTitleToCategoryApiResponseAndConfidence(response, title, bw);
        		System.out.println(response);
            }          
        }
		catch(FileNotFoundException fe){
			fe.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	/*
	 * @Author: Sagar
	 * @Desc: An API request for image to category prediction will hit and response got verified
	 */
	public void testVerifyApiresponseforImageToCategoryPrediction() throws Exception{
		String pathforImageFile = "C:\\Users\\Sagar\\Desktop\\123_images\\Dell.jpeg";
		verifyResponseforImagetoCategoryPrediction(pathforImageFile);
	}
	
	@Test
	/*
	 * @Author: Sagar
	 * @Desc: Post n number of ads using posting apis
	 */
	public void testVerifyPostingAd() throws Exception{
		int numberOfAds = 1, i = 1;
		String Username = userEmail;
		String Password = password;
		String pathforImageFile = "C:\\Users\\Sagar\\Desktop\\31-8-16\\15.jpg";
		String UserToken = verifyUserTokenFromLoginApi(Username, Password);
		System.out.println("Token of login user is: "+UserToken);
		while(i<= numberOfAds){			
			String imageId = uploadImageAndProvideTheImageId(UserToken, pathforImageFile);
			System.out.println("Id of uploaded image is: "+imageId);
			postAnAdUsingUserTokenAndImageId(UserToken, imageId);
			i++;
		}
	}
}
