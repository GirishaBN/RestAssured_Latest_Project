package framework.auth;

import framework.config.ConfigManager;

import io.restassured.response.Response;

import java.util.concurrent.locks.ReentrantLock;

import static io.restassured.RestAssured.given;

public class TokenManager {

	private final SecretProvider secretProvider;

	private volatile Token cachedToken;

	private final ReentrantLock lock = new ReentrantLock();

	public TokenManager(SecretProvider secretProvider) {
		this.secretProvider = secretProvider;
	}

	public String getAccessToken() {

		Token token = cachedToken;

		if (token != null && !token.isExpired()) {
			return token.accessToken();
		}

		return refreshToken();
	}

	private String refreshToken() {

		lock.lock();

		try {

			/*
			 * Double-check after acquiring the lock. Another thread may have already
			 * refreshed it.
			 */
			Token token = cachedToken;

			if (token != null && !token.isExpired()) {
				return token.accessToken();
			}

			String clientId = secretProvider.getSecret("API_CLIENT_ID");

			String clientSecret = secretProvider.getSecret("API_CLIENT_SECRET");

			Response response = given().baseUri(ConfigManager.getString("base.url"))
					.contentType("application/x-www-form-urlencoded").formParam("grant_type", "client_credentials")
					.formParam("client_id", clientId).formParam("client_secret", clientSecret).when()
					.post("/oauth/token").then().statusCode(200).extract().response();

			String accessToken = response.jsonPath().getString("access_token");

			long expiresIn = response.jsonPath().getLong("expires_in");

			/*
			 * Refresh slightly before actual expiry. This avoids race conditions around
			 * token expiry.
			 */
			long safetyBufferSeconds = 30;

			long expiresAt = System.currentTimeMillis() + ((expiresIn - safetyBufferSeconds) * 1000);

			cachedToken = new Token(accessToken, expiresAt);

			return accessToken;

		} finally {

			lock.unlock();
		}
	}
}