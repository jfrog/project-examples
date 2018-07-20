package org.gradle.example;
import com.auth0.jwt.JWT;

public class JwtClaimDecoder {

	public static String getClaim(String token, String claimName) {
	    return JWT.decode(token).getClaim(claimName).asString();
	}
}
