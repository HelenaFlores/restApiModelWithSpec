package tests.registration;

import models.registration.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

public class RegistrationTests extends TestBase {

    String username;
    String password;

    @BeforeEach
    public void prepareTestData() {

        username = "user_" + System.currentTimeMillis();
        password = "pass_" + System.currentTimeMillis();
    }

    @Test
    public void successfulRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);

        SuccessfulRegistrationResponseModel registrationResponse =
                api.users.register(registrationData);

        assertThat(registrationResponse.id()).isGreaterThan(0);
        assertThat(registrationResponse.username()).isEqualTo(username);
        assertThat(registrationResponse.firstName()).isEqualTo("");
        assertThat(registrationResponse.lastName()).isEqualTo("");
        assertThat(registrationResponse.email()).isEqualTo("");

        assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
    }

    @Test
    public void existingUserWrongRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(username, password);

        SuccessfulRegistrationResponseModel firstRegistrationResponse =
                api.users.register(registrationData);

        assertThat(firstRegistrationResponse.username()).isEqualTo(username);

        ExistingUserResponseModel secondRegistrationResponse =
                api.users.registerExistingUser(registrationData);

        String expectedError = REGISTRATION_EXISTING_USER_ERROR;
        String actualError = secondRegistrationResponse.username().get(0);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    public void wrongRegistrationWithoutPasswordTest() {
        RegistrationBodyWithoutPasswordModel registrationData = new RegistrationBodyWithoutPasswordModel(username);

        WrongRegistrationWithoutPasswordResponseModel registrationResponse =
                api.users.registerWithoutPassword(registrationData);

        String expectedError = REGISTRATION_WRONG_WITHOUT_PASSWORD_OR_LOGIN;
        String actualError = registrationResponse.password().get(0);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    public void wrongRegistrationWithoutLoginTest() {
        RegistrationBodyWithoutLoginModel registrationData = new RegistrationBodyWithoutLoginModel(password);

        WrongRegistrationWithoutLoginResponseModel registrationResponse =
                api.users.registerWithoutLogin(registrationData);

        String expectedError = REGISTRATION_WRONG_WITHOUT_PASSWORD_OR_LOGIN;
        String actualError = registrationResponse.username().get(0);
        assertThat(actualError).isEqualTo(expectedError);
    }


}