package de.maxknoll.app.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Cognito has a custom logout url.
 * See more information <a href="https://docs.aws.amazon.com/cognito/latest/developerguide/logout-endpoint.html">here</a>.
 */
@Component
public class CognitoLogoutHandler extends SimpleUrlLogoutSuccessHandler {

    private final CognitoLogoutConfiguration cognitoLogoutConfiguration;

    @Autowired
    CognitoLogoutHandler(CognitoLogoutConfiguration cognitoLogoutConfiguration) {
        this.cognitoLogoutConfiguration = cognitoLogoutConfiguration;
    }

    /**
     * The domain of your user pool.
     */
//    private String domain = "https://eu-central-1n2ffqea6l.auth.eu-central-1.amazoncognito.com" ;

    /**
     * An allowed callback URL.
     */

//    private String logoutRedirectUrl = "https://cl-a2b16ea083ef413095bc58593cfe00a0.ecs.eu-central-1.on.aws";


    /**
     * The ID of your User Pool Client.
     */
//    private String userPoolClientId = "1vt7mo8a1862q1gtaiqg845du1";

    /**
     * Here, we must implement the new logout URL request. We define what URL to send our request to, and set out client_id and logout_uri parameters.
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        return UriComponentsBuilder
                .fromUri(URI.create(cognitoLogoutConfiguration.getDomain() + "/logout"))
                .queryParam("client_id", cognitoLogoutConfiguration.getUserPoolClientId())
                .queryParam("logout_uri", cognitoLogoutConfiguration.getLogoutRedirectUrl())
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }
}
