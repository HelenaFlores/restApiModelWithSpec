package models.update;

public record SuccessfulAllUpdateResponseModel(String id, String username,
                                               String firstName, String lastName,
                                               String email, String remoteAddr) {}