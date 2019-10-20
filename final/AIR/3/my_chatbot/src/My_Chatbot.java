import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

	

public class My_Chatbot {

		boolean exit =false;
		
		Map<String,String> nouns = new HashMap<String,String>();
		Map<String,String> welcome = new HashMap<String,String>();
		Map<String,String> farewell = new HashMap<String,String>();
		
		public My_Chatbot(){
			
			nouns.put("loans?", "options are: 1) 2) 3)");
			nouns.put("regions", "barclas");
			
			welcome.put("hi", "hello");
			welcome.put("hello", "hey");
			
			farewell.put("bye", "bye");
		}
	
		String getAns(String input){
			
			String tokens[] = input.split("\\s");
			
			for(int i=0;i<tokens.length;i++){
				
				// note:: 3 cases
				// note:: welcome.containsKey
				if(welcome.containsKey(tokens[i].toLowerCase()))
					return welcome.get(tokens[i]);
					
				else if(nouns.containsKey(tokens[i].toLowerCase()))
					return nouns.get(tokens[i]);
				
				else if(farewell.containsKey(tokens[i].toLowerCase()))
					return farewell.get(tokens[i]);
					
			}
			
			return "Sorry.. I dont understand";
		}
		
		public static void main(String args[]){
			
			System.out.println("Welcome to our investment banking system");
			
			Scanner sc = new Scanner(System.in);
			My_Chatbot c = new My_Chatbot();
			
			while(true)
			{
				String input = sc.nextLine();
				
				String output = c.getAns(input);
				
				System.out.println(output);
				
				if(c.exit)
					break;
			}
		}
}
