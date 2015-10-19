import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class Test6_1 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		EventBus eventBus = vertx.eventBus();
		JsonObject config = context.config();

		DeploymentOptions options = new DeploymentOptions().setConfig(config);

		vertx.deployVerticle("Test6_2.java", options);
		vertx.deployVerticle("Test6_3.java", options);

		/////////////////////////////////////////////////
		// HTTP Server
		/////////////////////////////////////////////////

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

				eventBus.send(method, param, (AsyncResult<Message<String>> result) -> {

					if (result.succeeded()) {

						String returnValue = result.result().body();

						request.response().putHeader("content-type", "application/json");
						request.response().end("{\"METHOD\": \"" + method + "\", \"RESULT\": \"" + returnValue + "\"}");

					} else {

						request.response().putHeader("content-type", "application/json");
						request.response().end("{\"METHOD\": \"" + method + "\", \"ERROR\": \"" + result.cause().getMessage() + "\"}");
						result.cause().printStackTrace();

					}

				});

				System.out.println("Send End!!!");

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