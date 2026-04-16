package api;

import models.registration.*;

import static io.restassured.RestAssured.given;
import static specs.registration.RegistrationSpec.*;

public class UsersApiClient {

    public SuccessfulRegistrationResponseModel register(RegistrationBodyModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
    }

    public ExistingUserResponseModel registerExistingUser(RegistrationBodyModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(existingUserRegistrationResponseSpec)
                .extract()
                .as(ExistingUserResponseModel.class);
    }

    public WrongRegistrationWithoutPasswordResponseModel registerWithoutPassword(RegistrationBodyWithoutPasswordModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(wrongRegistrationWithoutPasswordResponseSpec)
                .extract()
                .as(WrongRegistrationWithoutPasswordResponseModel.class);
    }

    public WrongRegistrationWithoutLoginResponseModel registerWithoutLogin(RegistrationBodyWithoutLoginModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(wrongRegistrationWithoutLoginResponseSpec)
                .extract()
                .as(WrongRegistrationWithoutLoginResponseModel.class);
    }
}