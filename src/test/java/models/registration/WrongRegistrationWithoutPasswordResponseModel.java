package models.registration;


import java.util.List;

public record WrongRegistrationWithoutPasswordResponseModel(List<String> password) {}