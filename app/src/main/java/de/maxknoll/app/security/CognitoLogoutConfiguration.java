package de.maxknoll.app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CognitoLogoutConfiguration {

    @Value("${logouthandler.domain}")
    private String domain;

    @Value("${logouthandler.redirect}")
    private String logoutRedirectUrl;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String userPoolClientId;




    public String getUserPoolClientId() {
        return userPoolClientId;
    }

    public void setUserPoolClientId(String userPoolClientId) {
        this.userPoolClientId = userPoolClientId;
    }

    public String getLogoutRedirectUrl() {
        return logoutRedirectUrl;
    }

    public void setLogoutRedirectUrl(String logoutRedirectUrl) {
        this.logoutRedirectUrl = logoutRedirectUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
