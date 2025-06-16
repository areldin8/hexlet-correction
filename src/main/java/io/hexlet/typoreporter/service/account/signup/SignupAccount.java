package io.hexlet.typoreporter.service.account.signup;


public record SignupAccount(
    String username,
    String email,
    String password,
    String firstName,
    String lastName,
    String authProvider,
    String externalId
) {

    @Override
    public String toString() {
        return "SignupAccount(username=%s, email=%s, firstName=%s, lastName=%s, id=%s)"
            .formatted(username, email, firstName, lastName, externalId);
    }

}
