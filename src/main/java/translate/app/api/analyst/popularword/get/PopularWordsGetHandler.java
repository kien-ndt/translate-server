package translate.app.api.analyst.popularword.get;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import translate.app.external.entity.WordEntity;
import translate.app.external.repository.WordsRepository;

@Service
public class PopularWordsGetHandler {
	
	@Autowired
	private WordsRepository wordsRepository;
	
	public Mono<ServerResponse> getPopularWords(ServerRequest serverRequest) {
		return ServerResponse.ok().body(
				wordsRepository.findAll(
					Sort.by(Sort.Direction.DESC, "searchTime")), WordEntity.class);
	}
	
}
