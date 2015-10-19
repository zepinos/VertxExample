import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class Test3_5 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		EventBus eventBus = vertx.eventBus();

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

	}

	@Override
	public void stop() throws Exception {

	}

}