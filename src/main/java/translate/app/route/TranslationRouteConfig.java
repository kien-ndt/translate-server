package translate.app.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import translate.app.api.analyst.popularword.get.PopularWordsGetHandler;
import translate.app.api.translation.TranslationGetHandler;

@Configuration
public class TranslationRouteConfig {
	
	@Autowired
	private TranslationGetHandler translationGetHandler;
	
	@Autowired
	private PopularWordsGetHandler popularWordsGetHandler;

    @Bean
    public RouterFunction<ServerResponse> translationGetRoute() {
        return RouterFunctions.route()
                .GET("/translation", translationGetHandler::getTranslation)
                .GET("/analyst/popularwords", popularWordsGetHandler::getPopularWords)
                .build();
    }
	
}
