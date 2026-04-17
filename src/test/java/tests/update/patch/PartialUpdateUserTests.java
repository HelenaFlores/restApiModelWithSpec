package tests.update.patch;

import models.login.LoginBodyModel;
import models.logout.LogoutBodyModel;
import org.junit.jupiter.api.Test;

import static tests.TestData.LOGIN_PASSWORD;
import static tests.TestData.LOGIN_USERNAME;

public class PartialUpdateUserTests {
    // patch 2
    @Test
    public void successfulLogoutTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);
        String refreshToken = api.AuthApiClient.loginAndGetRefreshToken(loginData);

        LogoutBodyModel logoutData = new LogoutBodyModel(refreshToken);
        api.AuthApiClient.logout(logoutData);
    }


}
