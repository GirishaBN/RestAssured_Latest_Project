package framework.auth;

public record Token(String accessToken,long expiresAt) {
public boolean isExpired()
{
	return System.currentTimeMillis()>=expiresAt;
}
}
