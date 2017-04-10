package de.schroenser.youtube.uploader;

import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import com.google.api.services.youtube.model.Video;
import com.google.inject.Guice;
import net.israfil.gcommander.JCommanderModuleBuilder;

@RequiredArgsConstructor(onConstructor = @_(@Inject), access = AccessLevel.PROTECTED)
public class YoutubeUploader
{
    public static void main(String[] args)
    {
        Guice.createInjector(
            new YoutubeUploaderModule(), JCommanderModuleBuilder.bindParameters(
                PlaylistItemSnippetArguments.class,
                VideoFileArgument.class,
                VideoSnippetArguments.class,
                VideoStatusArguments.class).withArguments(args).build()).getInstance(YoutubeUploader.class).upload();
    }

    private final VideoUploader videoUploader;
    private final PlaylistAppender playlistAppender;

    private void upload()
    {
        Video video = videoUploader.upload();
        playlistAppender.append(video);
    }
}