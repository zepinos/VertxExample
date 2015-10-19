import io.vertx.core.AbstractVerticle;

public class Test1 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		vertx.createHttpServer()
				.requestHandler(httpServerRequest -> {})
				.listen(8080);

	}

	@Override
	public void stop() throws Exception {

	}

}