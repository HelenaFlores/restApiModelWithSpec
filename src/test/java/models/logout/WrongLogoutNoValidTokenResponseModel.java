package models.logout;


import java.util.List;

public record WrongLogoutNoValidTokenResponseModel(String detail, String code) {}