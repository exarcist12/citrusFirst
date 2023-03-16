package lesson.helpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.message.MessageType;
import lesson.pojo.Data;
import lesson.pojo.Support;
import lesson.pojo.User;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

public class TestHelperHTTP extends TestNGCitrusTestRunner {
	private TestContext context;

	@Test(description = "Получение информации о пользователе", enabled=true)
	@CitrusTest
	public void getTestActions() {
		this.context = citrus.getCitrusContext().createTestContext();

		http(httpActionBuilder -> httpActionBuilder
				.client("httpHelperClient")
				.send()
				.get("users/${userId}"));

		http(httpActionBuilder -> httpActionBuilder
				.client("httpHelperClient")
				.receive()
				.response(HttpStatus.OK)
				.messageType(MessageType.JSON)
				.payload(getJsonData(), "objectMapper"));
	}


	public User getJsonData() {
		User user = new User();

		Data data = new Data();
		data.setId(Integer.valueOf(context.getVariable("userId")));
		data.setEmail("janet.weaver@reqres.in");
		data.setFirstName("Janet");
		data.setLastName("Weaver");
		data.setAvatar("https://reqres.in/img/faces/2-image.jpg");
		user.setData(data);

		Support support = new Support();
		support.setUrl("https://reqres.in/#support-heading");
		support.setText("To keep ReqRes free, contributions towards server costs are appreciated!");

		user.setSupport(support);

		return user;

	}
}
