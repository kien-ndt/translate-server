package translate.app.exception.handler;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalErrorHandler extends AbstractErrorWebExceptionHandler {

	public GlobalErrorHandler(ErrorAttributes errorAttributes, Resources resources,
			ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
		super(errorAttributes, resources, applicationContext);
		this.setMessageReaders(serverCodecConfigurer.getReaders());
		this.setMessageWriters(serverCodecConfigurer.getWriters());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::handleException);
	}
	
	private Mono<ServerResponse> handleException(ServerRequest serverRequest) {
		Map<String, Object> error = 
				this.getErrorAttributes(serverRequest, ErrorAttributeOptions.defaults());
		HttpStatus httpStatus = 
				error.get("httpStatus") != null 
				? (HttpStatus) error.get("httpStatus") : HttpStatus.INTERNAL_SERVER_ERROR;
		error.remove("httpStatus");
		return ServerResponse.status(httpStatus).body(BodyInserters.fromValue(error));
	}

}
