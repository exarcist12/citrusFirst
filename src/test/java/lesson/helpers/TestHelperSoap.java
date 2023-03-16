package lesson.helpers;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.dataaccess.webservicesserver.NumberToDollars;
import com.dataaccess.webservicesserver.NumberToDollarsResponse;
import lesson.feathers.CustomMarshaller;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;

public class TestHelperSoap extends TestNGCitrusTestRunner {
	private TestContext context;

	@Test(description = "Получение", enabled=true)
	@CitrusTest
	public void getTestActions() {
		this.context = citrus.getCitrusContext().createTestContext();

		CustomMarshaller<Class<NumberToDollars>> convRequest = new CustomMarshaller<>();
		CustomMarshaller<Class<NumberToDollarsResponse>> convResponse = new CustomMarshaller<>();


		soap(soapActionBuilder -> soapActionBuilder
						.client("soapHelper")
						.send()
//                .payload(getNumberToDollarsRequest(), "marshallerRequest")
						.payload(convRequest.convert(NumberToDollars.class, getNumberToDollarsRequest(), "http://www.dataaccess.com/webservicesserver/", "NumberToDollars" ))
		);

		soap(soapActionBuilder -> soapActionBuilder
						.client("soapHelper")
						.receive()
						.xsdSchemaRepository("schemaRepositoryService")
//                .payload(getNumberToDollarsResponse(), "marshallerR
						.payload(convResponse.convert(NumberToDollarsResponse.class, getNumberToDollarsResponse(), "http://www.dataaccess.com/webservicesserver/", "NumberToDollarsResponse" ))
		);
	}

	public NumberToDollars getNumberToDollarsRequest() {
		NumberToDollars numberToDollars = new NumberToDollars();
		numberToDollars.setDNum(new BigDecimal("15"));
		return numberToDollars;
	}

	public NumberToDollarsResponse getNumberToDollarsResponse() {
		NumberToDollarsResponse numberToDollarsResponse = new NumberToDollarsResponse();
		numberToDollarsResponse.setNumberToDollarsResult("fifteen dollars");
		return numberToDollarsResponse;
	}
}