import io.vertx.core.AbstractVerticle;
import io.vertx.core.parsetools.RecordParser;

public class Test4_2 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		vertx.createNetServer().connectHandler(socket -> {

			String handlerID = socket.writeHandlerID();

			socket.handler(RecordParser.newDelimited("\n", buffer -> {

				String message = buffer.toString().trim();

				System.out.println(socket.remoteAddress() + " (" + handlerID + ") => " + message + "\r\n");

				socket.write("Receive => " + message + "\r\n");

			}));

			socket.drainHandler(handler -> {

				System.out.println("drainHandler (" + handlerID + ") => " + handler);

			});

			socket.endHandler(handler -> {

				System.out.println("endHandler (" + handlerID + ") => " + handler);

			});

			socket.closeHandler(handler -> {

				System.out.println("closeHandler (" + handlerID + ") => " + handler);


			});

			socket.exceptionHandler(e -> {

				System.out.println("exceptionHandler (" + handlerID + ")");

				e.printStackTrace();

			});

		}).listen(9999);

	}

	@Override
	public void stop() throws Exception {

	}

}