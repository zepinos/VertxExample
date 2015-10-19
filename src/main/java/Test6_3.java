import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;

public class Test6_3 extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		super.start();

		EventBus eventBus = vertx.eventBus();
		JsonObject config = context.config();

		eventBus.consumer("Test6_3", (Message<Object> message) -> {

			String testMessage = config.getString("TestMessage");

			LocalMap<String, String> map = vertx.sharedData().getLocalMap("Test6.LocalMap");

			String localMapValue = map.get("LocalMapKey");

			System.out.println("LocalMapValue : " + localMapValue);

			String sCount = map.get("Count");

			if (sCount == null) {

				sCount = "0";

			}

			int count = Integer.parseInt(sCount) + 1;

			map.put("Count", Integer.toString(count));

			localMapValue = "Count" + count;

			map.put("LocalMapKey", localMapValue);

			message.reply(testMessage);

		});

	}

	@Override
	public void stop() throws Exception {

	}

}