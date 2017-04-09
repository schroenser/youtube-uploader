package de.schroenser.youtube.uploader;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Provider;

import lombok.extern.slf4j.Slf4j;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;

@Slf4j
public class GoogleClientSecretsProvider implements Provider<GoogleClientSecrets>
{
    private final Path clientSecretsFile;
    private final JsonFactory jsonFactory;

    @Inject
    protected GoogleClientSecretsProvider(@DataDirectory Path dataDirectory, JsonFactory jsonFactory)
    {
        clientSecretsFile = dataDirectory.resolve("client_secrets.json");
        this.jsonFactory = jsonFactory;
    }

    @Override
    public GoogleClientSecrets get()
    {
        if (!Files.exists(clientSecretsFile))
        {
            log.info(
                "Create client ID and secret at {} and store them under {}",
                "https://console.developers.google.com/project/_/apiui/credential",
                clientSecretsFile.toString());
            log.info(
                "The file needs to have the following structure: {}",
                "{\"installed\": {\"client_id\": \"\", \"client_secret\": \"\"}}");
            throw new RuntimeException("Client secrets file " + clientSecretsFile.toString() + " is missing");
        }

        GoogleClientSecrets result;
        try
        {
            Reader clientSecretReader = Files.newBufferedReader(clientSecretsFile, StandardCharsets.UTF_8);
            result = GoogleClientSecrets.load(jsonFactory, clientSecretReader);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error loading client secrets", e);
        }
        return result;
    }
}
