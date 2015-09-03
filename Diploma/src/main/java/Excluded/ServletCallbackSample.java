package Excluded;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by user on 02.09.2015.
 */
public class ServletCallbackSample extends AbstractAuthorizationCodeCallbackServlet {

    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
            throws ServletException, IOException {
        resp.sendRedirect("/");
    }

    @Override
    protected void onError(
            HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
            throws ServletException, IOException {
        // handle error
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath("/oauth2callback");
        return url.build();
    }

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws IOException {
        return new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
                new NetHttpTransport(),
                new JacksonFactory(),
                new GenericUrl(APPLICATION_URL + "/token"),
                new BasicAuthentication(CLIENT_ID, CLIENT_SECRET),
                CLIENT_ID,
                APPLICATION_URL +"/authorize").setCredentialDataStore(
                StoredCredential.getDefaultDataStore(
                        new FileDataStoreFactory(new File("datastoredir"))))
                .build();
    }

    @Override
    protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
        return "sergey.tolokunsky";
    }
}
