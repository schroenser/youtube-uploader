package de.schroenser.youtube.uploader;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Provider;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.common.collect.ImmutableSet;

@RequiredArgsConstructor(onConstructor = @_(@Inject), access = AccessLevel.PROTECTED)
public class CredentialProvider implements Provider<Credential>
{
    public static final Collection<String> SCOPES = ImmutableSet.of("https://www.googleapis.com/auth/youtube");

    private final HttpTransport httpTransport;
    private final JsonFactory jsonFactory;
    private final GoogleClientSecrets googleClientSecrets;
    private final DataStore<StoredCredential> credentialDataStore;

    @Override
    public Credential get()
    {
        AuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
            httpTransport, jsonFactory, googleClientSecrets, SCOPES).setCredentialDataStore(
            credentialDataStore).build();
        VerificationCodeReceiver verificationCodeReceiver = new LocalServerReceiver.Builder().setPort(8080).build();
        AuthorizationCodeInstalledApp authorizationCodeInstalledApp = new AuthorizationCodeInstalledApp(
            authorizationCodeFlow, verificationCodeReceiver);
        Credential result = null;
        try
        {
            result = authorizationCodeInstalledApp.authorize("user");
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error during authorization", e);
        }
        return result;
    }
}
