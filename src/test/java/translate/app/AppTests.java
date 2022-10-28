package translate.app;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import translate.app.api.translation.resource.TranslationGetResponse;

@SpringBootTest
class AppTests {

	private WebTestClient webClient = WebTestClient.bindToServer()
			.baseUrl("http://localhost:8080")
			.responseTimeout(Duration.ofMinutes(1L))
			.build();
	
	@Test
	void contextLoads() {
		webClient.get().uri(
			"translation?source_lang_translate={sourceLang}&target_lang_translate={targetLang}&word={word}",
			"en", "es", "money").exchange().expectStatus().isOk().expectBody(TranslationGetResponse.class);
	}

}
