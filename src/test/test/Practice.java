package test;

import org.junit.Test;
public class Practice {
	public static void main(String args[]) throws Exception{
		String originalString = "My Name is Sagar";
		String reverseString = "";
		String[] originalStringPart = {};
		char part[] = {};
		originalStringPart = originalString.split(" ");
		for(int i=0; i < originalStringPart.length; i++){
			part = originalStringPart[i].toCharArray();
			for(int j = 0; j < part.length; j++);
			for(int k = part.length-1; k >= 0; k--){
				reverseString = reverseString + part[k];
			}
			if(i<originalStringPart.length-1){
				reverseString = reverseString + " ";
			}
		}
		System.out.println("Original string is: "+ originalString);
		System.out.println("After reversing each word: "+ reverseString);
	}
	@Test
	public void testVerifyresult() throws Exception{
		
	}
}
