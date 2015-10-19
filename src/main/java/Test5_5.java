import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

public class Test5_5 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		EventBus eventBus = vertx.eventBus();

		eventBus.consumer("Test5_5.test3", (Message<Object> message) -> {

			Test5_1 test5_1 = Test5_1.getInstance();

			System.out.println("Get Instance Test5_1 : test3");

			message.reply(null);

		});

		eventBus.consumer("Test5_5.test4", (Message<Object> message) -> {

			Test5_1 test5_1 = Test5_1.getInstance();

			System.out.println("Get Instance Test5_1 : test4");

			message.reply(null);

		});

	}

	@Override
	public void stop() throws Exception {

	}

}