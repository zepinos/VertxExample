import io.vertx.core.AbstractVerticle;

public class Test4_1 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		vertx.createNetServer().connectHandler(socket -> {

			String handlerID = socket.writeHandlerID();

			socket.handler(buffer -> {

				String message = buffer.toString().trim();

				System.out.println(socket.remoteAddress() + " (" + handlerID + ") => " + message + "\r\n");

				socket.write("Receive => " + message + "\r\n");

			});

		}).listen(9999);

	}

	@Override
	public void stop() throws Exception {

	}

}