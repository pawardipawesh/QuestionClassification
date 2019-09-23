import java.util.ResourceBundle;

import opennlp.tools.util.featuregen.GeneratorFactory;

public class Configuration {

	
	private static String resourceName="config";
	private static ResourceBundle resource;
	
	public static String getProperty(String name){
		
		String value=null;
		try{
			value=getResourceBundle().getString(name);
			
		}catch(Exception e){
			System.out.println("Error in getting the value of the resource :"+e.getMessage());
		}
		return value;
	}

	private static ResourceBundle getResourceBundle() {
		
		if(resource==null){
			
			resource=ResourceBundle.getBundle(getResourceName());
		}
		return resource;
	}

	private static String getResourceName() {
		
		return resourceName;
		
	}
}
