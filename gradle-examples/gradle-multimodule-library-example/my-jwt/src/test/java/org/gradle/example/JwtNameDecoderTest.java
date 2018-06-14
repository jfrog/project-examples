package org.gradle.example;

import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class JwtNameDecoderTest {

	@Test
	public void testDecoder() {
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
		assertThat(JwtClaimDecoder.getClaim(token, "name")).isEqualTo("John Doe");
	}
}
