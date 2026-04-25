package api;

import io.qameta.allure.Step;
import models.users.registration.*;

import static io.restassured.RestAssured.given;
import static specs.users.registration.RegistrationSpec.*;

public class UsersApiClient {

    @Step("Отправка register запроса на создание пользователя")
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

    @Step("Отправка register запроса на создание существующего пользователя")
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

    @Step("Отправка register запроса на создание пользователя без password")
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

    @Step("Отправка register запроса на создание пользователя без username")
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