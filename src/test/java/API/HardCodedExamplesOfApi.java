package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamplesOfApi {


 /*
Given- pre condition- prepare the request
WheEN- Action- sending the request/ hitting the endpoint
Then- result - verify response
BU ADIMLARI IZLEYECEGIZ.
*/


    String baseURI= RestAssured.baseURI  = "http://hrm.syntaxtechs.net/syntaxapi/api" ;
    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MzkzNTQ2NjgsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYzOTM5Nzg2OCwidXNlcklkIjoiMzM4MyJ9.BldYQf7J_ssoe7Kf2TlTiCRoLT9MaJPOoQG-12ubH_Q";
    static String employee_id;
    @Test
public void dgetDetailsOfOneEmployee(){
  //rest assured consider baseurl as basuri

     //given part
RequestSpecification preparedRequest= given().header("Authorization",token)
        .header("Content-Type","application/json").queryParam("employee_id","26048A");

     //when part- hitting the endpoint
    Response response= preparedRequest.when().get("/getOneEmployee.php");
    System.out.println(response.asString());
    //then-result-assertion
    response.then().assertThat().statusCode(200);
}
//simdi diegr bir adimla devam edecegiz.



@Test

public void acreateEmployee() {

    //given part
    RequestSpecification preparedRequest = given().header("Authorization", token)
            .header("Content-Type", "application/json").body("{\"emp_firstname\": \"ENE\",\n" +
                    "\"emp_lastname\": \"AK\",\n" +
                    "\"emp_middle_name\": \"SSS\",\n" +
                    "\"emp_gender\": \"M\",\n" +
                    "\"emp_birthday\": \"2021-12-04\",\n" +
                    "\"emp_status\": \"Employee\",\n" +
                    "\"emp_job_title\" : \"API\"\n}");


    //when part
    Response response = preparedRequest.when().post("/createEmployee.php");
    response.prettyPrint();
    //this prettyprint  does the same job as Syso.  //System.out.println(response.asString());

    //jsonPath() we use this to get specific information from the json object

    employee_id = response.jsonPath().getString("Employee.employee_id");
    System.out.println(employee_id);

    //then part

    response.then().assertThat().statusCode(201);
    response.then().assertThat().body("Employee.emp_firstname", equalTo("ENE"));
    response.then().assertThat().body("Message", equalTo("Employee Created"));
    response.then().assertThat().header("Server", equalTo("Apache/2.4.39 (Win64) PHP/7.2.18"));

}


@Test
public void bgetCreatedEmployee(){

        RequestSpecification preparedRequest= given().header("Authorization",token)
                .header("Content-Type","application/json").queryParam("employee_id",employee_id);


      Response response = preparedRequest.when().get("/getOneEmployee.php");

      String empID= response.jsonPath().getString("employee.employee_id");

      boolean comparingEmpID=empID.contentEquals(employee_id);

       Assert.assertTrue(comparingEmpID);

       String firstName=response.jsonPath().getString("employee.emp_firstname");
       Assert.assertTrue(firstName.contentEquals("ENE"));




    }

@Test
public void cupdatedCreatedEmployee(){

    RequestSpecification preparedRequest= given().header("Authorization",token)
            .header("Content-Type","application/json").body("{\n" +
                    "    \"employee\": {\n" +
                    "        \"employee_id\": \""+employee_id+ "\",\n" +
                    "        \"emp_firstname\": \"ramo\",\n" +
                    "        \"emp_lastname\": \"la\",\n" +
                    "         \"emp_middle_name\": \"lo\",\n" +
                    "         \"emp_gender\": \"F\",\n" +
                    "        \"emp_birthday\": \"2011-03-04\",\n" +
                    "        \"emp_status\": \"Active\"\n" +
                    "        \"emp_job_title\": \"Database Administrator\"\n" +
                    "    }");


    Response response =preparedRequest.when().put("/updateEmployee.php");
response.prettyPrint();




}



}
