package tests.users.logout;

import models.users.login.LoginBodyModel;
import models.users.logout.LogoutBodyModel;
import models.users.logout.LogoutEmptyBodyModel;
import models.users.logout.WrongLogoutNoValidTokenResponseModel;
import models.users.logout.WrongLogoutWithoutTokenResponseModel;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

public class LogoutTests extends TestBase {

    @Test
    public void successfulLogoutTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);

        String refreshToken =
                api.auth.loginAndGetRefreshToken(loginData);

            LogoutBodyModel logoutData = new LogoutBodyModel(refreshToken);
                api.auth.logout(logoutData);
    }


    @Test
    public void noValidTokenLogoutTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);
        String refreshToken =
                api.auth.loginAndGetRefreshToken(loginData);

        LogoutBodyModel logoutData =
                new LogoutBodyModel(refreshToken + ADDITIONAL_SYMBOLS);
        WrongLogoutNoValidTokenResponseModel logoutResponse =
                api.auth.logoutNoValidToken(logoutData);

        String expectedDetailError = LOGOUT_WRONG_DETAIL_ERROR;
        String expectedCodeError = LOGOUT_WRONG_CODE_ERROR;
        String actualDetailError = logoutResponse.detail();
        String actualCodeError = logoutResponse.code();
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
        assertThat(actualCodeError).isEqualTo(expectedCodeError);
    }

    @Test
    public void wrongWithoutRefreshTokenLogoutTest() {
        LogoutEmptyBodyModel logoutData = new LogoutEmptyBodyModel();
        WrongLogoutWithoutTokenResponseModel logoutResponse =
                api.auth.logoutWithoutRefreshToken(logoutData);

        String expectedDetailError = LOGOUT_WRONG_REFRESH_ERROR;
        String actualRefreshError = logoutResponse.refresh().get(0);
        assertThat(actualRefreshError).isEqualTo(expectedDetailError);
    }
}
