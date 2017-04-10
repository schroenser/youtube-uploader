package de.schroenser.youtube.uploader;

import java.io.IOException;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.Video;

@RequiredArgsConstructor(onConstructor = @_(@Inject), access = AccessLevel.PROTECTED)
public class PlaylistAppender
{
    private final PlaylistItemSnippetArguments playlistItemSnippetArguments;
    private final YouTube youTube;

    public PlaylistItem append(Video video)
    {
        PlaylistItem playlistItem = new PlaylistItem();
        playlistItem.setSnippet(playlistItemSnippetArguments.getPlaylistItemSnippet(video));
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
