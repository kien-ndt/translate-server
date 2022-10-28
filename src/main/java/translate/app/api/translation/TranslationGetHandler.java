package translate.app.api.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import translate.app.api.translation.external.entity.TranslationResult;
import translate.app.api.translation.external.fetcher.TranslationGetFetcher;
import translate.app.api.translation.mapping.MappingTranslationGetResponse;
import translate.app.api.translation.resource.TranslationGetRequestParams;
import translate.app.api.translation.resource.TranslationGetResponse;
import translate.app.api.translation.validation.TranslationGetValidator;
import translate.app.external.entity.WordEntity;
import translate.app.external.repository.WordsRepository;

@Service
public class TranslationGetHandler {
	
	@Autowired
	private TranslationGetValidator validator;
	
	@Autowired
	private TranslationGetFetcher fetcher;
	
	@Autowired
	private MappingTranslationGetResponse mapping;
	
	@Autowired
	private WordsRepository wordsRepository;
	
	
	public Mono<ServerResponse> getTranslation(ServerRequest serverRequest) {
						
		Mono<TranslationGetResponse> result = 
				Mono.just(new TranslationGetRequestParams(
						serverRequest.queryParam("source_lang_translate").orElse(""), 
						serverRequest.queryParam("target_lang_translate").orElse(""),
						serverRequest.queryParam("word").orElse("")))
				.flatMap(validator::validate)
				.flatMap(requestParams -> 
					fetcher.fetchTranslationResult(
							((TranslationGetRequestParams) requestParams).getSourceLang(),
							((TranslationGetRequestParams) requestParams).getTargetLang(),
							((TranslationGetRequestParams) requestParams).getWord()))
				.flatMap(translationResult -> Mono.just(mapping.mappingResponseBody(translationResult)))
				.flatMap(translationResponse -> {
					WordEntity newWord = new WordEntity();
					newWord.text = translationResponse.id;
					newWord.searchTime = 0;
					newWord.sourceLang = serverRequest.queryParam("source_lang_translate").orElse("");
					return wordsRepository.findByTextAndSourceLang(newWord.text, newWord.sourceLang)
					.defaultIfEmpty(newWord).flatMap(r -> {
						r.text = newWord.text;
						r.sourceLang = newWord.sourceLang;
						r.searchTime += 1;
						return wordsRepository.save(r);
					}).map(entity -> translationResponse);
				})
				.onErrorResume(r -> Mono.error(r));
		return ServerResponse.ok().body(result, TranslationResult.class);
	}

}
