package driver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Driver;

public class DriverTests {
	
	
	@Test
	public void CompleteTest()
	{
		RestAssured.baseURI="http://ergast.com/api/f1/drivers.json";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json"));
		List<Driver> drivers = response.jsonPath().getList("MRData.DriverTable.Drivers",Driver.class);
		Assert.assertTrue(drivers.size()>1);
		Driver result= drivers.stream().filter(driver->"adams".equalsIgnoreCase(driver.getDriverId())).findAny().orElse(null);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getNationality(), "Belgian");
		System.out.println("nationality");
		drivers.stream().filter(driver->"British".equals(driver.getNationality())).forEach(driver-> System.out.println(driver.getDriverId()));
		System.out.println("Date of Birth");
		drivers.stream().filter(driver->isDrivereDateOfBirthBetween("1920","1925",driver)).forEach(driver-> System.out.println(driver.getDriverId()));
		System.out.println("family Name");
		drivers.stream().filter(driver->!driver.getDriverId().equalsIgnoreCase(driver.getFamilyName())).forEach(driver-> System.out.println(driver.getDriverId()));
		System.out.println("Given Name");
		drivers.stream().filter(driver->driver.getGivenName().startsWith("B")).forEach(driver-> System.out.println(driver.getGivenName()));
		
	}

	private boolean isDrivereDateOfBirthBetween(String fromYear, String toYear, Driver driver) {
		// TODO Auto-generated method stub
		 LocalDate date= LocalDate.parse(driver.getDateOfBirth(), DateTimeFormatter.ISO_LOCAL_DATE);
		 LocalDate fromdate = LocalDate.of(Integer.parseInt(fromYear), 1, 1);
		 LocalDate todate = LocalDate.of(Integer.parseInt(toYear), 12, 31);
		 return date.isAfter(fromdate) && date.isBefore(todate);
	}

//	@Test
//	public void TestDriversCount()
//	{
//		RestAssured.baseURI="http://ergast.com/api/f1/drivers.json";
//		RequestSpecification httpRequest = RestAssured.given();
//		Response response = httpRequest.request(Method.GET);
//		Assert.assertEquals(response.getStatusCode(), 200);
//		List<Driver> drivers = response.jsonPath().getList("MRData.DriverTable.Drivers");
//		Assert.assertTrue(drivers.size()>1);
//	}
//
//	@Test
//	public void DriverNataionality()
//	{
//		RestAssured.baseURI="http://ergast.com/api/f1/drivers.json";
//		RequestSpecification httpRequest = RestAssured.given();
//		Response response = httpRequest.request(Method.GET);
//		Assert.assertEquals(response.getStatusCode(), 200);
//		JsonPath jsonPathEvaluator = response.jsonPath();
//		List<Driver> drivers = jsonPathEvaluator.getList("MRData.DriverTable.Drivers",Driver.class);
//		Driver result= drivers.stream().filter(driver->"adams".equalsIgnoreCase(driver.getDriverId())).findAny().orElse(null);
//		Assert.assertNotNull(result);
//		Assert.assertEquals(result.getNationality(), "Belgian");
//	}
}
