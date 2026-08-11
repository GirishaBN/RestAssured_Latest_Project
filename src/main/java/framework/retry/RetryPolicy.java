package framework.retry;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Set;

public final class RetryPolicy {

    private RetryPolicy() {
    }

    private static final Set<Integer> RETRYABLE_STATUS_CODES =Set.of(429, 500, 502, 503, 504);

    public static boolean isRetryableStatus(Integer statusCode) {
        return statusCode != null && RETRYABLE_STATUS_CODES.contains(statusCode);
    }

    public static boolean isRetryableException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }

        Throwable current = throwable;

		/*
		 * Reason while and getCause use 
		 * 
		 * RuntimeException 
		 *     ↓ 
		 * RestAssuredException
		 *     ↓
		 * IOException
		 *     ↓ 
		 * SocketTimeoutException
		 */
        while (current != null) {

            if (current instanceof SocketTimeoutException|| current instanceof ConnectException
                    || current instanceof SocketException|| current instanceof UnknownHostException) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}