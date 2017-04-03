package de.schroenser.youtube.uploader;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Provider;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DataDirectoryProvider implements Provider<Path>
{
    public static final String USER_HOME_DIRECTORY_NAME = System.getProperty("user.home");
    public static final String PROGRAM_DIRECTORY_NAME = ".youtube-uploader";

    @Override
    public Path get()
    {
        return Paths.get(USER_HOME_DIRECTORY_NAME, PROGRAM_DIRECTORY_NAME).toAbsolutePath();
    }
}
