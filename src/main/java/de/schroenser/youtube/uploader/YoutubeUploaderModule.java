package de.schroenser.youtube.uploader;

import java.nio.file.Path;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.services.youtube.YouTube;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class YoutubeUploaderModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(Path.class).annotatedWith(DataDirectory.class).toProvider(DataDirectoryProvider.class);
        bind(JsonFactory.class).toInstance(JacksonFactory.getDefaultInstance());
        bind(GoogleClientSecrets.class).toProvider(GoogleClientSecretsProvider.class);
        bind(
            new TypeLiteral<DataStore<StoredCredential>>()
            {
            }).toProvider(CredentialDataStoreProvider.class);
        bind(HttpTransport.class).toInstance(new NetHttpTransport());
        bind(Credential.class).toProvider(CredentialProvider.class);
        bind(YouTube.class).toProvider(YouTubeProvider.class);
        bind(MediaHttpUploaderProgressListener.class).to(LoggingMediaHttpUploaderProgressListener.class);
    }
}
