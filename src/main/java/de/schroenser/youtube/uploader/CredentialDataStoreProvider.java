package de.schroenser.youtube.uploader;

import java.io.IOException;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;

public class CredentialDataStoreProvider implements Provider<DataStore<StoredCredential>>
{
    public static final String DATASTORE_ID = "uploadvideo";

    private final Path credentialsFile;

    @Inject
    protected CredentialDataStoreProvider(@DataDirectory Path dataDirectory)
    {
        credentialsFile = dataDirectory.resolve(".oauth-credentials");
    }

    @Override
    public DataStore<StoredCredential> get()
    {
        DataStore<StoredCredential> result;
        try
        {
            FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(credentialsFile.toFile());
            result = fileDataStoreFactory.getDataStore(DATASTORE_ID);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error creating credential data store", e);
        }
        return result;
    }
}
