package testForPractice;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PracticeForJsonData {
	public static void main(String args[]) throws Exception{
		String pathforJsonFile = "D:\\practice.json";
		JSONParser parser = new JSONParser();
		Object obj1 = parser.parse(new FileReader(pathforJsonFile));
		JSONObject jobj1 = (JSONObject) obj1;
		JSONArray arr1 = (JSONArray) jobj1.get("categoryPredictionTestData");
		int i = 0;
		for(Object obj2 : arr1){
			JSONObject jobj2 = (JSONObject) obj2;
			JSONArray arr2;
			if(i==0){
				 arr2 = (JSONArray) jobj2.get("positiveData");
			}else{
				 arr2 = (JSONArray) jobj2.get("negativeData");
			}
			
			i++ ;
			for(Object obj3 : arr2){
				JSONObject jobj3 = (JSONObject) obj3;
				String title = jobj3.get("title").toString();
				System.out.println(title);
			}
		}
	}

}
