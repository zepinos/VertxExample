import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class Test4_3 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		EventBus eventBus = vertx.eventBus();

		eventBus.consumer("Test.Login", (Message<JsonObject> message) -> {

			JsonObject param = message.body();

			System.out.println(param.getString("METHOD") + " Start!!!");

			message.reply(param.getString("METHOD") + " End");

		});

		eventBus.consumer("Test.Sleep", (Message<JsonObject> message) -> {

			JsonObject param = message.body();

			System.out.println(param.getString("METHOD") + " Start!!!");

			try {

				Thread.sleep(10000L);

			} catch (Exception e) {

				e.printStackTrace();

			}

			message.reply(param.getString("METHOD") + " End");

		});

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

				eventBus.send("Test." + method, param, (AsyncResult<Message<String>> result) -> {

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

		/////////////////////////////////////////////////
		// Net Server
		/////////////////////////////////////////////////

		Handler<NetSocket> handler = socket -> {

			String handlerID = socket.writeHandlerID();

			socket.handler(RecordParser.newDelimited("\n", buffer -> {

				String message = buffer.toString().trim();

				try {

					JsonObject param = new JsonObject(message);

					String method = param.getString("METHOD");

					eventBus.send("Test." + method, param, (AsyncResult<Message<String>> result) -> {

						if (result.succeeded()) {

							String returnValue = result.result().body();

							eventBus.send(handlerID, Buffer.buffer().appendString("{\"METHOD\": \"" + method + "\", \"RESULT\": \"" + returnValue + "\"}\r\n"));

						} else {

							eventBus.send(handlerID, Buffer.buffer().appendString("{\"METHOD\": \"" + method + "\", \"ERROR\": \"" + result.cause().getMessage() + "\"}\r\n"));
							result.cause().printStackTrace();

						}

					});

				} catch (Exception e) {

					socket.write(e.getMessage());

				}

			}));

		};

		vertx.createNetServer().connectHandler(handler)
				.listen(9999);

	}

	@Override
	public void stop() throws Exception {

	}

}