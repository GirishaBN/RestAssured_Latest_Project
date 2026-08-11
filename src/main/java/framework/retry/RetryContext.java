package framework.retry;

public final class RetryContext {

    private RetryContext() {
    }

    private static final ThreadLocal<Integer> STATUS_CODE = new ThreadLocal<>();

    public static void setStatusCode(int statusCode) {
        STATUS_CODE.set(statusCode);
    }

    public static Integer getStatusCode() {
        return STATUS_CODE.get();
    }

    public static void clear() {
        STATUS_CODE.remove();
    }
}