import java.util.Random;

public class Test5_2 {

	public static void main(String[] args) throws Exception {

		System.out.println("Start Test5_2");

		Test5_2 test5_2 = new Test5_2();
		Test5_3 test5_3 = new Test5_3();

		Random random = new Random(System.currentTimeMillis());

		for (int iCount = 0; iCount < 10; iCount++) {

			if (random.nextInt(2) == 0) {

				if (random.nextInt(2) == 0) {

					test5_2.test1();

				} else {

					test5_2.test2();

				}

			} else {

				if (random.nextInt(2) == 0) {

					test5_3.test3();

				} else {

					test5_3.test4();

				}

			}

		}

		System.out.println("End Test5_2");

	}

	public void test1() {

		Test5_1 test5_1 = Test5_1.getInstance();

		System.out.println("Get Instance Test5_1 : test1");

	}

	public void test2() {

		Test5_1 test5_1 = Test5_1.getInstance();

		System.out.println("Get Instance Test5_1 : test2");

	}

}