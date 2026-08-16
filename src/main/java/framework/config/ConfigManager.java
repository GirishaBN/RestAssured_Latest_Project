package framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;

import framework.logging.FrameworkLogger;

public final class ConfigManager {

	private static final Logger log = FrameworkLogger.getLogger(ConfigManager.class);

	private static final String DEFAULT_ENV = "qa";
	private static final String CONFIG_DIRECTORY = "config/";

	private static final Properties PROPERTIES = new Properties();

	private ConfigManager() {
		// Utility class
	}

	static {
		loadConfiguration();
	}

	private static void loadConfiguration() {

		String env = System.getProperty("env", DEFAULT_ENV);

		if (env == null || env.isBlank()) {
			env = DEFAULT_ENV;
		}

		env = env.trim().toLowerCase(Locale.ROOT);

		String file = CONFIG_DIRECTORY + env + ".properties";

		log.info("Loading configuration for environment: {}", env);

		try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(file)) {

			if (input == null) {
				throw new IllegalStateException("Configuration file not found: " + file);
			}

			PROPERTIES.load(input);

			log.info("Configuration loaded successfully: {}", file);

		} catch (IOException e) {

			throw new IllegalStateException("Unable to load configuration file: " + file, e);
		}
	}

	public static String getString(String key) {

		return getRequiredProperty(key);
	}

	public static int getInt(String key) {

		String value = getRequiredProperty(key);

		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid integer configuration: " + key + " = " + value, e);
		}
	}

	public static long getLong(String key) {

		String value = getRequiredProperty(key);

		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid long configuration: " + key + " = " + value, e);
		}
	}

	public static boolean getBoolean(String key) {

		String value = getRequiredProperty(key);

		if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {

			throw new IllegalArgumentException("Invalid boolean configuration: " + key + " = " + value);
		}

		return Boolean.parseBoolean(value);
	}

	private static String getRequiredProperty(String key) {

		if (key == null || key.isBlank()) {
			throw new IllegalArgumentException("Configuration key cannot be null or blank");
		}

		String value = PROPERTIES.getProperty(key);

		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Missing configuration: " + key);
		}

		return value.trim();
	}
}