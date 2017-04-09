package de.schroenser.youtube.uploader;

import javax.inject.Inject;
import javax.inject.Provider;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.YouTube;

@RequiredArgsConstructor(onConstructor = @_(@Inject), access = AccessLevel.PROTECTED)
public class YouTubeProvider implements Provider<YouTube>
{
    public static final String APPLICATION_NAME = "youtube-cmdline-uploadvideo-sample";

    private final HttpTransport httpTransport;
    private final JsonFactory jsonFactory;
    private final Credential credential;

    @Override
    public YouTube get()
    {
        return new YouTube.Builder(httpTransport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build();
    }
}
