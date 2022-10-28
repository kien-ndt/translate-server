package translate.app.external.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import translate.app.external.entity.WordEntity;

@Repository
public interface WordsRepository extends ReactiveMongoRepository<WordEntity, String> {

	public Mono<WordEntity> findByTextAndSourceLang(String text, String sourceLang);
	
}
