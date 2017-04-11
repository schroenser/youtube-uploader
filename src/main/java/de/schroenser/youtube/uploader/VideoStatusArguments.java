package de.schroenser.youtube.uploader;

import com.beust.jcommander.Parameter;
import com.google.api.services.youtube.model.VideoStatus;

public class VideoStatusArguments
{
    @Parameter(names = "-privacyStatus", description = "The video's privacy status.")
    private String privacyStatus;

    @Parameter(names = "-publicStatsViewable",
        description = "Indicates if the extended video statistics on the watch page can be viewed by everyone. Note that the view count, likes, etc will still be visible if this is disabled.")
    private boolean publicStatsViewable;

    public VideoStatus getVideoStatus()
    {
        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus(privacyStatus);
        status.setPublicStatsViewable(publicStatsViewable);
        return status;
    }
}
