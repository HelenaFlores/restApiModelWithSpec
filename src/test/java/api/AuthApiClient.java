package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.login.*;
import models.logout.LogoutBodyModel;
import models.logout.LogoutEmptyBodyModel;
import models.logout.WrongLogoutNoValidTokenResponseModel;
import models.logout.WrongLogoutWithoutTokenResponseModel;

import static io.restassured.RestAssured.given;
import static specs.login.LoginSpec.*;
import static specs.logout.LogoutSpec.*;

public class AuthApiClient {

    public SuccessfulLoginResponseModel login(LoginBodyModel loginBody) {
        return given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .as(SuccessfulLoginResponseModel.class);
    }

    @Step("Авторизация и получение токена")
    public String loginAndGetRefreshToken(LoginBodyModel loginBody) {
        return given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .path("refresh");
    }

    public WrongCredentialsLoginResponseModel loginWrongCredentials(LoginBodyModel loginBody) {
        return given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(wrongCredentialsLoginResponseSpec)
                .extract()
                .as(WrongCredentialsLoginResponseModel.class);
    }

    @Step("Отправка запроса logout")
    public void logout(LogoutBodyModel logoutBody) {
        given(logoutRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successfulLogoutResponseSpec);
    }


    public WrongLogoutNoValidTokenResponseModel logoutNoValidToken(LogoutBodyModel logoutBody) {
        return given(logoutRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(errorNoValidNokenLogoutResponseSpec)
                .extract()
                .as(WrongLogoutNoValidTokenResponseModel.class);
    }

    public WrongLogoutWithoutTokenResponseModel logoutWithoutRefreshToken(LogoutEmptyBodyModel logoutBody) {
       return given(logoutRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(errorEmptyBodyLogoutResponseSpec)
                .extract()
                .as(WrongLogoutWithoutTokenResponseModel.class);
    }

    public WrongLoginNullUsernameResponseModel wrongLoginNullUsernameResponse(LoginBodyModel loginBody) {
        return given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(wrongLoginNullUsernameResponseSpec)
                .extract()
                .as(WrongLoginNullUsernameResponseModel.class);
    }

    public WrongLoginNullPasswordResponseModel wrongLoginNullPasswordResponse(LoginBodyModel loginBody) {
        return given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(wrongLoginNullPasswordResponseSpec)
                .extract()
                .as(WrongLoginNullPasswordResponseModel.class);
    }
}
