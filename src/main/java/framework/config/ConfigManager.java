package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {
	private final static Properties PROPERTIES = new Properties();

	private ConfigManager() {	
	};

	static {
		String env=System.getProperty("env", "qa");
		if(env.isBlank())
		{
			env="qa";
		}
		String file="config/"+env+".properties";
		try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(file)) {
			if(input==null)
			{
				throw new IllegalArgumentException("Configuration file not found: " + file);
			}
			PROPERTIES.load(input);

		} catch (IOException e) {
			throw new RuntimeException("Unable to load configuration: " + file,e);
		}
	}
	
	public static String get(String key)
	{
		String value= PROPERTIES.getProperty(key);
		if(value==null||value.isBlank())
		{
			throw new IllegalArgumentException("Missing Configuration: "+key);
		}
		return value;
	}
	public static long getLong(String key)
	{
		String value= PROPERTIES.getProperty(key);
		if(value==null||value.isBlank())
		{
			throw new IllegalArgumentException("Missing Configuration: "+key);
		}
		try {
		return Long.parseLong(value.trim());
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("Invalid long value for configuration: " + key +" = " + value,e);
		}
	}

}
