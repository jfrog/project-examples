package org.gradle.example;

public class JwtNameDecoder {

	public static String getName(String token) {
	    return JwtClaimDecoder.getClaim(token, "name");
	}
}
