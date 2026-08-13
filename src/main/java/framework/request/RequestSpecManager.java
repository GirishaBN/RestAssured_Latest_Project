package framework.request;

import io.restassured.specification.RequestSpecification;

public final class RequestSpecManager {
	private static final ThreadLocal<RequestSpecification> REQUEST_SPEC = new ThreadLocal<>();

	private RequestSpecManager() {
	}

	public static void set(RequestSpecification specification) {

		REQUEST_SPEC.set(specification);
	}

	public static RequestSpecification get() {

		RequestSpecification requestSpec = REQUEST_SPEC.get();

		if (requestSpec == null) {
			throw new IllegalStateException(
					"RequestSpecification not initialized for thread: " + Thread.currentThread().getName());
		}

		return requestSpec;
	}

	public static void clear() {

		REQUEST_SPEC.remove();
	}
}
