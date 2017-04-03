package de.schroenser.youtube.uploader;

import java.io.IOException;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Video;

@RequiredArgsConstructor(onConstructor = @_(@Inject), access = AccessLevel.PROTECTED)
public class PlaylistAppender
{
    private final YouTube youTube;

    public PlaylistItem append(String playlistId, Video video)
    {
        PlaylistItemSnippet playlistItemSnippet = createPlaylistItemSnippet(playlistId, video);
        return insertPlaylistItem(playlistItemSnippet);
    }

    private PlaylistItemSnippet createPlaylistItemSnippet(String playlistId, Video video)
    {
        ResourceId resourceId = new ResourceId();
        resourceId.setKind(video.getKind());
        resourceId.setVideoId(video.getId());

        PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
        playlistItemSnippet.setPlaylistId(playlistId);
        playlistItemSnippet.setResourceId(resourceId);
        return playlistItemSnippet;
    }

    private PlaylistItem insertPlaylistItem(PlaylistItemSnippet snippet)
    {
        PlaylistItem playlistItem = new PlaylistItem();
        playlistItem.setSnippet(snippet);
        String part = "snippet";
        PlaylistItem result;
        try
        {
            YouTube.PlaylistItems.Insert insert = youTube.playlistItems().insert(part, playlistItem);
            result = insert.execute();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error appending to playlist", e);
        }
        return result;
    }
}
