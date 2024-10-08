package com.decrypto.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = ChallengeApplicationTests.class)
@ActiveProfiles("test")
class ChallengeApplicationTests {

	@Test
	void contextLoads() {
	}

}
