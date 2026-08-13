package framework.logging;

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

	@Override
	public Response filter(FilterableRequestSpecification request, FilterableResponseSpecification responseSpec,
			FilterContext context) {

		logRequest(request);

		Response response = context.next(request, responseSpec);

		// Centralized status capture for retry
		RetryContext.setStatusCode(response.statusCode());

		logResponse(response);

		return response;
	}

	private void logRequest(FilterableRequestSpecification request) {

		StringBuilder output = new StringBuilder();

		output.append("Method: ").append(request.getMethod()).append("\n");

		output.append("URI: ").append(request.getURI()).append("\n");

		output.append("Headers: ").append(maskHeaders(request)).append("\n");

		if (request.getBody() != null) {
			output.append("Body: ").append(maskBody(request.getBody().toString())).append("\n");
		}

		String requestData = output.toString();

		ApiEvidence.setRequest(requestData);

		log.info("API REQUEST\n{}", requestData);
	}

	private void logResponse(Response response) {

		String responseData = "Status: " + response.statusCode() + "\n" + "Time: " + response.getTime() + " ms\n"
				+ "Headers: " + response.getHeaders() + "\n" + "Body: " + response.asString();

		ApiEvidence.setResponse(responseData);

		log.info("API RESPONSE\n{}", responseData);
	}

	private String maskHeaders(FilterableRequestSpecification request) {

		String headers = request.getHeaders().toString();

		return headers.replaceAll("(?i)(Authorization\\s*:\\s*Bearer\\s+)[^,}\\]]+", "$1******");
	}

	private String maskBody(String body) {

		return body.replaceAll("(?i)(\"password\"\\s*:\\s*\")[^\"]*", "$1******")
				.replaceAll("(?i)(\"token\"\\s*:\\s*\")[^\"]*", "$1******");
	}
}