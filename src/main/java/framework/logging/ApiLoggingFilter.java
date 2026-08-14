package framework.logging;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.reporting.ApiEvidence;
import framework.retry.RetryContext;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public final class ApiLoggingFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(ApiLoggingFilter.class);

	private static final int MAX_BODY_LENGTH = 10_0;

	@Override
	public Response filter(FilterableRequestSpecification request, FilterableResponseSpecification responseSpec,
			FilterContext context) {

		String correlationId = UUID.randomUUID().toString();

		long startTime = System.nanoTime();

		logRequest(request, correlationId);

		Response response = context.next(request, responseSpec);

		long duration = System.nanoTime() - startTime;

		/*
		 * Centralized status capture for RetryAnalyzer.
		 *
		 * RetryContext should internally use ThreadLocal for parallel execution.
		 */
		RetryContext.setStatusCode(response.statusCode());

		logResponse(response, correlationId, duration);

		return response;
	}

	// ============================================================
	// REQUEST LOGGING
	// ============================================================

	private void logRequest(FilterableRequestSpecification request, String correlationId) {

		StringBuilder output = new StringBuilder();

		output.append("CorrelationId: ").append(correlationId).append("\n");

		output.append("Thread: ").append(Thread.currentThread().getName()).append("\n");

		output.append("Method: ").append(request.getMethod()).append("\n");

		output.append("URI: ").append(request.getURI()).append("\n");

		output.append("Headers: ").append(maskSensitiveHeaders(request.getHeaders().toString())).append("\n");

		if (request.getBody() != null) {

			String body = request.getBody().toString();

			output.append("Body: ").append(maskAndTruncateBody(body)).append("\n");
		}

		String requestData = output.toString();

		/*
		 * Store evidence for Extent Report / reporting layer.
		 */
		ApiEvidence.setRequest(requestData);

		log.info("API REQUEST\n{}", requestData);
	}

	// ============================================================
	// RESPONSE LOGGING
	// ============================================================

	private void logResponse(Response response, String correlationId, long duration) {

		String responseBody = response.asString();

		StringBuilder output = new StringBuilder();

		output.append("CorrelationId: ").append(correlationId).append("\n");

		output.append("Thread: ").append(Thread.currentThread().getName()).append("\n");

		output.append("Status: ").append(response.statusCode()).append("\n");

		output.append("Time: ").append(response.getTime()).append(" ms").append("\n");

		output.append("Filter Duration: ").append(duration).append(" ms").append("\n");

		output.append("Headers: ").append(maskSensitiveHeaders(response.getHeaders().toString())).append("\n");

		output.append("Body: ").append(maskAndTruncateBody(responseBody)).append("\n");

		String responseData = output.toString();

		/*
		 * Store response evidence for reporting.
		 */
		ApiEvidence.setResponse(responseData);

		log.info("API RESPONSE\n{}", responseData);
	}

	

	// ============================================================
	// HEADER MASKING
	// ============================================================

	private String maskSensitiveHeaders(String headers) {

		if (headers == null || headers.isBlank()) {
			return headers;
		}

		/*
		 * Authorization
		 */
		headers = headers.replaceAll("(?i)(Authorization\\s*[:=]\\s*Bearer\\s+)[^,}\\]]+", "$1******");

		/*
		 * API keys
		 */
		headers = headers.replaceAll("(?i)(X-API-Key\\s*[:=]\\s*)[^,}\\]]+", "$1******");

		/*
		 * Cookies
		 */
		headers = headers.replaceAll("(?i)(Cookie\\s*[:=]\\s*)[^,}\\]]+", "$1******");

		/*
		 * Set-Cookie
		 */
		headers = headers.replaceAll("(?i)(Set-Cookie\\s*[:=]\\s*)[^,}\\]]+", "$1******");

		return headers;
	}

	// ============================================================
	// BODY MASKING
	// ============================================================

	private String maskAndTruncateBody(String body) {

		if (body == null || body.isBlank()) {
			return body;
		}

		String sanitizedBody = maskSensitiveFields(body);

		return truncate(sanitizedBody);
	}

	// ============================================================
	// SENSITIVE JSON FIELD MASKING
	// ============================================================

	private String maskSensitiveFields(String body) {

		/*
		 * Password
		 */
		body = body.replaceAll("(?i)(\"password\"\\s*[:=]\\s*\")[^\"]*", "$1******");

		/*
		 * Access token
		 */
		body = body.replaceAll("(?i)(\"access_token\"\\s*[:=]\\s*\")[^\"]*", "$1******");

		/*
		 * Refresh token
		 */
		body = body.replaceAll("(?i)(\"refresh_token\"\\s*[:=]\\s*\")[^\"]*", "$1******");

		/*
		 * Generic token
		 */
		body = body.replaceAll("(?i)(\"token\"\\s*[:=]\\s*\")[^\"]*", "$1******");

		/*
		 * Client secret
		 */
		body = body.replaceAll("(?i)(\"client_secret\"\\s*[:=]\\s*\")[^\"]*", "$1******");

		/*
		 * Secret
		 */
		body = body.replaceAll("(?i)(\"secret\"\\s*[:=]\\s*\")[^\"]*", "$1******");

		return body;
	}

	// ============================================================
	// LARGE BODY PROTECTION
	// ============================================================

	private String truncate(String body) {

		if (body == null) {
			return null;
		}

		if (body.length() <= MAX_BODY_LENGTH) {
			return body;
		}

		return body.substring(0, MAX_BODY_LENGTH) + "\n...[BODY TRUNCATED]...";
	}
}