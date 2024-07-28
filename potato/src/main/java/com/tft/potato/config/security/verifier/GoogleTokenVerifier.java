package com.tft.potato.config.security.verifier;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Slf4j
//@Component
public class GoogleTokenVerifier {

    private GoogleTokenVerifier(){}

    private static String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    public void setClientId(String value) {
        clientId = value;
    }

    /**
     *  Google Id Token을 검증.
     *  아래 링크는 ID Token의 구성에 대한 정보이다.
     * @see : https://cloud.google.com/docs/authentication/token-types?hl=ko#id
     *
     * @param idTokenString
     * @throws GeneralSecurityException
     * @throws IOException
     */

    public static GoogleIdToken verifyIdToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                //.setAudience(Collections.singletonList(clientId))
                .setAudience(Collections.singleton(clientId))

                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();


        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            log.info("It's Correct Id Token : " + idToken.toString());
            // GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            // String userId = payload.getSubject();

        } else {
            log.info("Invalid ID token.");
        }

        return idToken;
    }
}
