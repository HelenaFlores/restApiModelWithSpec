package tests.update.put;

import models.login.LoginBodyModel;
import models.logout.LogoutBodyModel;
import models.logout.WrongLogoutNoValidTokenResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.update.AllUpdateBodyModel;
import models.update.SuccessfulAllUpdateResponseModel;
import models.update.WrongAllUpdateMethodAllowedResponseModel;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;
import static tests.TestData.LOGOUT_WRONG_CODE_ERROR;

public class AllUpdateUserTests  extends TestBase {
    
    Faker faker = new Faker();
    String username;
    String password;
    String firstname;
    String lastName;
    String email;

    @BeforeEach
    public void prepareTestData() {

        firstname = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().emailAddress();
        username = "user_" + System.currentTimeMillis();
        password = "pass_" + System.currentTimeMillis();
    }

    @Test
    public void successfulAllUpdateTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse =
                api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(registrationData.username(), registrationData.password());
        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        AllUpdateBodyModel updateData = new AllUpdateBodyModel(username, firstname, lastName, email);
        SuccessfulAllUpdateResponseModel updateResponse = api.auth.putUpdate(accessToken, updateData);

        String userNameData = updateData.username();
        String userNameResponse = updateResponse.username();
        assertThat(userNameData).isEqualTo(userNameResponse);
    }

    @Test
    public void wrongMethodAllowedAllUpdateTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
        SuccessfulRegistrationResponseModel registrationResponse =
                api.users.register(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(registrationData.username(), registrationData.password());
        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        AllUpdateBodyModel updateData = new AllUpdateBodyModel(username, firstname, lastName, email);
        WrongAllUpdateMethodAllowedResponseModel updateResponse = api.auth.errorMethodAllowedPutUpdate(accessToken, updateData);

        String expectedDetailError = PUT_UPDATE_WRONG_DETAIL_ERROR;
        String actualDetailError = String.valueOf(updateResponse.detail());
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }
}
