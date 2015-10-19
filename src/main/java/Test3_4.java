import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class Test3_4 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		EventBus eventBus = vertx.eventBus();

		eventBus.consumer("Test.Login", (Message<JsonObject> message) -> {

			JsonObject param = message.body();

			System.out.println(param.getString("METHOD") + " Start!!!");

			message.reply(param.getString("METHOD") + " End");

		});

	}

	@Override
	public void stop() throws Exception {

	}

}