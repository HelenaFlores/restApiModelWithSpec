package tests.users.login;

import models.users.login.*;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

public class LoginTests extends TestBase {
    @Test
    public void successfulLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);

        SuccessfulLoginResponseModel loginResponse = api.auth.login(loginData);

        String actualAccess = loginResponse.access();
        String actualRefresh = loginResponse.refresh();
        assertThat(actualAccess).startsWith(LOGIN_TOKEN_PREFIX);
        assertThat(actualRefresh).startsWith(LOGIN_TOKEN_PREFIX);
        assertThat(actualAccess).isNotEqualTo(actualRefresh);
    }

    @Test
    public void wrongCredentialsLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_WRONG_PASSWORD);

        WrongCredentialsLoginResponseModel loginResponse = api.auth.loginWrongCredentials(loginData);

        String expectedDetailError = LOGIN_WRONG_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.detail();
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    public void wrongLoginNullUsernameTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_WRONG_PASSWORD_OR_USERNAME_NULL, LOGIN_PASSWORD);

        WrongLoginNullUsernameResponseModel loginResponse = api.auth.wrongLoginNullUsernameResponse(loginData);

        String expectedDetailError = LOGIN_WRONG_PASSWORD_OR_USERNAME_NULL_ERROR;
        String actualDetailError = loginResponse.username().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    public void wrongPasswordNullTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_WRONG_PASSWORD_OR_USERNAME_NULL);

        WrongLoginNullPasswordResponseModel loginResponse = api.auth.wrongLoginNullPasswordResponse(loginData);

        String expectedDetailError = LOGIN_WRONG_PASSWORD_OR_USERNAME_NULL_ERROR;
        String actualDetailError = loginResponse.password().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }
}
