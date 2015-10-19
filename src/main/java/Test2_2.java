import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class Test2_2 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		Router router = Router.router(vertx);
		Route route = router.route("/:METHOD");

		route.handler(routingContext -> {

			HttpServerRequest request = routingContext.request();
			MultiMap params = request.params();

			String method = params.get("METHOD");
			System.out.println(method);

			request.endHandler(empty -> {

				JsonObject param = new JsonObject();
				params.forEach(entry -> param.put(entry.getKey(), entry.getValue()));

				request.response().putHeader("content-type", "application/json");
				request.response().end("{\"METHOD\": \"" + method + "\"}");

			});

		});

		HttpServerOptions httpServerOptions = new HttpServerOptions();
		httpServerOptions.setCompressionSupported(true);

		vertx.createHttpServer(httpServerOptions)
				.requestHandler(router::accept)
				.listen(8080);

	}

	@Override
	public void stop() throws Exception {

	}

}