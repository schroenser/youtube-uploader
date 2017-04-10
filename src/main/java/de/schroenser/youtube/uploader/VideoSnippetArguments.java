package de.schroenser.youtube.uploader;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.google.api.services.youtube.model.VideoSnippet;

public class VideoSnippetArguments
{
    @Parameter(names = "-title", description = "The video's title.")
    private String title;

    @Parameter(names = "-description", description = "The video's description.")
    private String description;

    @Parameter(names = "-tags",
        description = "A comma separated list of keyword tags associated with the video.")
    //TODO how to support tags with spaces?
    private List<String> tags;

    @Parameter(names = "-defaultLanguage", description = "he language of the videos's default snippet.")
    private String defaultLanguage;

    @Parameter(names = "-defaultAudioLanguage", description = "The language spoken in the video's default audio track.")
    private String defaultAudioLanguage;

    public VideoSnippet getVideoSnippet()
    {
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setTags(tags);
        snippet.setDefaultLanguage(defaultLanguage);
        snippet.setDefaultAudioLanguage(defaultAudioLanguage);
        return snippet;
    }
}
