public class Test5_1 {

	private Test5_1() {

	}

	private static Test5_1 instance;

	public static Test5_1 getInstance() {

		if (instance == null) {

			System.out.println("Create Instance Test5_1");

			instance = new Test5_1();

		}

		return instance;

	}

}