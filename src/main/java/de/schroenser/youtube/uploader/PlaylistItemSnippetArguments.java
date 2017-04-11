package de.schroenser.youtube.uploader;

import com.beust.jcommander.Parameter;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Video;

public class PlaylistItemSnippetArguments
{
    @Parameter(names = "-playlistId",
        description = "The ID that YouTube uses to uniquely identify the playlist that the playlist item is in.",
        required = true)
    private String playlistId;

    public PlaylistItemSnippet getPlaylistItemSnippet(Video video)
    {
        ResourceId resourceId = new ResourceId();
        resourceId.setKind(video.getKind());
        resourceId.setVideoId(video.getId());

        PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
        playlistItemSnippet.setPlaylistId(playlistId);
        playlistItemSnippet.setResourceId(resourceId);
        return playlistItemSnippet;
    }
}
