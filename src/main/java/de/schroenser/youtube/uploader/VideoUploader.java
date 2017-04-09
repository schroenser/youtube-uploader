package de.schroenser.youtube.uploader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoFileDetails;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.net.MediaType;

@RequiredArgsConstructor(onConstructor = @_(@Inject), access = AccessLevel.PROTECTED)
public class VideoUploader
{
    private final YouTube youTube;
    private final MediaHttpUploaderProgressListener mediaHttpUploaderProgressListener;

    public Video upload(
        String title, String description, List<String> tags, String language, String privacyStatus, Path videoFilePath)
    {
        VideoSnippet snippet = createVideoSnippet(title, description, tags, language);
        VideoStatus status = createVideoStatus(privacyStatus);
        VideoFileDetails fileDetails = createFileDetails(videoFilePath);
        AbstractInputStreamContent mediaContent = createAbstractInputStreamContent(videoFilePath);
        return uploadVideo(snippet, status, fileDetails, mediaContent);
    }

    private VideoSnippet createVideoSnippet(
        String title, String description, List<String> tags, String language)
    {
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setTags(tags);
        snippet.setDefaultLanguage(language);
        snippet.setDefaultAudioLanguage(language);
        return snippet;
    }

    private VideoStatus createVideoStatus(String privacyStatus)
    {
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus(privacyStatus);
        status.setPublicStatsViewable(false);
        return status;
    }

    private VideoFileDetails createFileDetails(Path videoFilePath)
    {
        VideoFileDetails fileDetails = new VideoFileDetails();
        fileDetails.setFileName(videoFilePath.getFileName().toString());
        return fileDetails;
    }

    private AbstractInputStreamContent createAbstractInputStreamContent(Path videoFilePath)
    {
        return new FileContent(MediaType.MP4_VIDEO.toString(), videoFilePath.toFile());
    }

    private Video uploadVideo(
        VideoSnippet snippet, VideoStatus status, VideoFileDetails fileDetails, AbstractInputStreamContent mediaContent)
    {
        Video video = new Video();
        video.setSnippet(snippet);
        video.setStatus(status);
        String part = "snippet,status";
        Video result;
        try
        {
            YouTube.Videos.Insert insert = youTube.videos().insert(part, video, mediaContent);
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
