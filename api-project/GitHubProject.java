package GitHubProject;
import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
public class GitHubProject {
	// Declare request specification
	RequestSpecification requestSpec;
	// Declare response specification
	ResponseSpecification responseSpec;
	// Global properties
	String sshKey;
	int sshKeyId;
	@BeforeClass
	public void setUp() {
		// Create request specification
		requestSpec = new RequestSpecBuilder()
				// Set content type
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token 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")
				// Set base URL
				.setBaseUri("https://api.github.com")
				// Build request specification
				.build();
		sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDkNBv1jZlAPhy+amjivsoyTMKaPQGpCzBjsOJewME3s3T5zJvRzpaLLuGyjpRkcDUu9I2ojG0HivLfmMHgTIjAPkbepsWa8bC523+4T7SYNtVOJLZ5H2K27pzf/YMSL6OwNTyZvSU4Ya5w1cSe+FEORkNOxaUB9tT4WV1BveGX9/M3qQvr1zbjwS+lp3xhSP/o3HZs3FVa7GGhq3aPbKMQKR1NgWuXnXvek/SMIImjVuxjRmTm8PfpkH5huMm6axuuOLgfQGZCKoy6WCRzidMUvad7BJmLV3MpoQqomALNVuqGjF8eNXv2XpMwPd6xLePVEnJdmxE0IcHea/HzgSx376Ir/DdOljSaGapsYP4d9blvBNtCo1npTLIi4xo9dJN72JTJsywYWafyAQe9SvVxycH7x3Uc0v3fakj217QSzfbHlj+iqtvZ1AW95soIPaj9YDV93+TxrEv5T+M20zuln7LAskFKZlhVedrICkurItWFldn8gSUzNh84RG4a8Uc=";
	}
	@Test(priority = 1)
	// Test case using a DataProvider
	public void addKeys() {
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
		Response response = given().spec(requestSpec) // Use requestSpec
				.body(reqBody) // Send request body
				.when().post("/user/keys"); // Send POST request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		sshKeyId = response.then().extract().path("id");
		// Assertions
		response.then().statusCode(201);
	}
	@Test(priority = 2)
	// Test case using a DataProvider
	public void getKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.when().get("/user/keys"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(200);
	}
	@Test(priority = 3)
	// Test case using a DataProvider
	public void deleteKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.pathParam("keyId", sshKeyId).when().delete("/user/keys/{keyId}"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(204);
	}
}