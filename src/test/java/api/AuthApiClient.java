package api;

import io.qameta.allure.Step;
import models.login.*;
import models.logout.LogoutBodyModel;
import models.logout.LogoutEmptyBodyModel;
import models.logout.WrongLogoutNoValidTokenResponseModel;
import models.logout.WrongLogoutWithoutTokenResponseModel;
import models.update.AllUpdateBodyModel;
import models.update.PartialUpdateBodyModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.WrongUpdateMethodAllowedResponseModel;

import static io.restassured.RestAssured.given;
import static specs.login.LoginSpec.*;
import static specs.logout.LogoutSpec.*;
import static specs.update.UpdateSpec.*;

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

    @Step("Авторизация и получение рефреш токена")
    public static String loginAndGetRefreshToken(LoginBodyModel loginBody) {
        return given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .path("refresh");
    }

    @Step("Авторизация и получение access токена")
    public String loginAndGetAccessToken(LoginBodyModel loginBody) {
        return given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .path("access");
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
    public static void logout(LogoutBodyModel logoutBody) {
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

    @Step("Отправка запроса put update")
    public static SuccessfulUpdateResponseModel putUpdate(String accessToken, AllUpdateBodyModel putUpdateBody) {
      return given(updateRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(putUpdateBody)
                .when()
                .put("/users/me/")
                .then()
                .spec(successfulPutUpdateResponseSpec)
                .extract()
                .as(SuccessfulUpdateResponseModel.class);
    }

    @Step("Отправка запроса patch update")
    public static SuccessfulUpdateResponseModel putPartialUpdate(String accessToken, PartialUpdateBodyModel putUpdateBody) {
        return given(updateRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(putUpdateBody)
                .when()
                .patch("/users/me/")
                .then()
                .spec(successfulPutUpdateResponseSpec)
                .extract()
                .as(SuccessfulUpdateResponseModel.class);
    }

    @Step("Отправка запроса post update")
    public static WrongUpdateMethodAllowedResponseModel errorMethodAllowedPutUpdate(String accessToken, AllUpdateBodyModel putUpdateBody) {
        return given(updateRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(putUpdateBody)
                .when()
                .post("/users/me/")
                .then()
                .spec(errorMethodAllowedResponseSpec)
                .extract()
                .as(WrongUpdateMethodAllowedResponseModel.class);
    }

    @Step("Отправка запроса post update")
    public static WrongUpdateMethodAllowedResponseModel errorMethodAllowedPatchUpdate(String accessToken, PartialUpdateBodyModel putUpdateBody) {
        return given(updateRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(putUpdateBody)
                .when()
                .post("/users/me/")
                .then()
                .spec(errorMethodAllowedResponseSpec)
                .extract()
                .as(WrongUpdateMethodAllowedResponseModel.class);
    }
}
