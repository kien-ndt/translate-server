package translate.app.api.translation.external.fetcher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import translate.app.api.translation.external.entity.TranslationResult;

@Component
public class TranslationGetFetcher {
	
	private WebClient client = WebClient.create();
	
	@Value("${oxford.app_id}")
	private String appId;
	
	@Value("${oxford.app_key}")	
	private String appKey;
	
	public Mono<TranslationResult> fetchTranslationResult(String sourceLang, String targetLang) {
		return client.get()
		.uri(
			"https://od-api.oxforddictionaries.com/api/v2/translations/" + sourceLang+"/"+targetLang+"/money?strictMatch=false")
		.header("Accept", "application/json")
		.header("app_id", appId)
		.header("app_key", appKey)
		.exchangeToMono(ri -> ri.bodyToMono(TranslationResult.class));
	}

}
