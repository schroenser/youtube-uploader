package de.schroenser.youtube.uploader;

import java.nio.file.Paths;

import com.beust.jcommander.Parameter;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.common.net.MediaType;

public class VideoFileArgument
{
    /**
     * We could use a {@code java.nio.file.Path} here, according to JCommander. But the jcommander-guice library is
     * older and doesn't support {@code java.nio.file.Path} when creating the annotated bindings, resulting in a {@code
     * java.lang.NullPointerException}.
     */
    @Parameter(names = "-file",
        description = "The actual video file.",
        required = true)
    private String path;

    public AbstractInputStreamContent getMediaContent()
    {
        return new FileContent(MediaType.MP4_VIDEO.toString(), Paths.get(path).toFile());
    }
}
