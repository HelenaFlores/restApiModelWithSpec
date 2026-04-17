package tests.update.patch;

import models.login.LoginBodyModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.update.PartialUpdateBodyModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.WrongUpdateMethodAllowedResponseModel;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

    public class PartialUpdateUserTests extends TestBase {

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
        public void successfulPartialUpdateTest() {
            RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
            SuccessfulRegistrationResponseModel registrationResponse =
                    api.users.register(registrationData);

            LoginBodyModel loginData = new LoginBodyModel(registrationData.username(), registrationData.password());
            String accessToken = api.auth.loginAndGetAccessToken(loginData);

            PartialUpdateBodyModel updateData = new PartialUpdateBodyModel(username, firstname);
            SuccessfulUpdateResponseModel updateResponse = api.auth.putPartialUpdate(accessToken, updateData);

            String userNameData = updateData.username();
            String userNameResponse = updateResponse.username();
            assertThat(userNameData).isEqualTo(userNameResponse);
        }

        @Test
        public void wrongMethodAllowedPartialUpdateTest() {
            RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);
            SuccessfulRegistrationResponseModel registrationResponse =
                    api.users.register(registrationData);

            LoginBodyModel loginData = new LoginBodyModel(registrationData.username(), registrationData.password());
            String accessToken = api.auth.loginAndGetAccessToken(loginData);

            PartialUpdateBodyModel updateData = new PartialUpdateBodyModel(username, firstname);
            WrongUpdateMethodAllowedResponseModel updateResponse = api.auth.errorMethodAllowedPatchUpdate(accessToken, updateData);

            String expectedDetailError = UPDATE_WRONG_DETAIL_ERROR;
            String actualDetailError = String.valueOf(updateResponse.detail());
            assertThat(actualDetailError).isEqualTo(expectedDetailError);
        }
    }

