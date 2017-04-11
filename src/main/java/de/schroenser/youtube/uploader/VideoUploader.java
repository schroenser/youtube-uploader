package de.schroenser.youtube.uploader;

import java.io.IOException;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;

@RequiredArgsConstructor(onConstructor = @_(@Inject), access = AccessLevel.PROTECTED)
public class VideoUploader
{
    private final VideoSnippetArguments videoSnippetArguments;
    private final VideoStatusArguments videoStatusArguments;
    private final VideoFileArgument videoFileArgument;
    private final YouTube youTube;
    private final MediaHttpUploaderProgressListener mediaHttpUploaderProgressListener;

    public Video upload()
    {
        Video video = new Video();
        video.setSnippet(videoSnippetArguments.getVideoSnippet());
        video.setStatus(videoStatusArguments.getVideoStatus());
        String part = "snippet,status";
        Video result;
        try
        {
            YouTube.Videos.Insert insert = youTube.videos().insert(part, video, videoFileArgument.getMediaContent());
            insert.getMediaHttpUploader().setProgressListener(mediaHttpUploaderProgressListener);
            result = insert.execute();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error uploading video", e);
        }
        return result;
    }
}
