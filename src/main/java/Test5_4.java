import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

import java.util.Random;

public class Test5_4 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		vertx.deployVerticle("Test5_5.java");

		EventBus eventBus = vertx.eventBus();

		eventBus.consumer("Test5_4.test1", (Message<Object> message) -> {

			Test5_1 test5_1 = Test5_1.getInstance();

			System.out.println("Get Instance Test5_1 : test1");

			message.reply(null);

		});

		eventBus.consumer("Test5_4.test2", (Message<Object> message) -> {

			Test5_1 test5_1 = Test5_1.getInstance();

			System.out.println("Get Instance Test5_1 : test2");

			message.reply(null);

		});

		Random random = new Random(System.currentTimeMillis());

		for (int iCount = 0; iCount < 10; iCount++) {

			if (random.nextInt(2) == 0) {

				if (random.nextInt(2) == 0) {

					eventBus.send("Test5_4.test1", null);

				} else {

					eventBus.send("Test5_4.test2", null);

				}

			} else {

				if (random.nextInt(2) == 0) {

					eventBus.send("Test5_5.test3", null);

				} else {

					eventBus.send("Test5_5.test4", null);

				}

			}

		}

	}

	@Override
	public void stop() throws Exception {

	}

}