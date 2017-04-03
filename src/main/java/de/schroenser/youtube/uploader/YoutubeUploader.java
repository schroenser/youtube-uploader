package de.schroenser.youtube.uploader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.Video;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;

@RequiredArgsConstructor(onConstructor = @_(@Inject), access = AccessLevel.PROTECTED)
public class YoutubeUploader
{
    public static void main(String[] args)
    {
        Guice.createInjector(new YoutubeUploaderModule()).getInstance(YoutubeUploader.class).upload();
    }

    private final VideoUploader videoUploader;
    private final PlaylistAppender playlistAppender;

    private void upload()
    {
        String title = "";
        String description = "";
        List<String> tags = ImmutableList.of();
        String language = "";
        String privacyStatus = "";
        Path videoFilePath = Paths.get("");
        String playlistId = "";

        Video video = videoUploader.upload(title, description, tags, language, privacyStatus, videoFilePath);
        PlaylistItem playlistItem = playlistAppender.append(playlistId, video);
    }
}