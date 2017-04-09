package de.schroenser.youtube.uploader;

import java.io.IOException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoggingMediaHttpUploaderProgressListener implements MediaHttpUploaderProgressListener
{
    @Override
    public void progressChanged(MediaHttpUploader uploader) throws IOException
    {
        switch (uploader.getUploadState())
        {
            case INITIATION_STARTED:
                log.info("Initiation started");
                break;
            case INITIATION_COMPLETE:
                log.info("Initiation complete");
                break;
            case MEDIA_IN_PROGRESS:
                log.info("Upload percentage: {}", uploader.getProgress());
                break;
            case MEDIA_COMPLETE:
                log.info("Upload completed");
                break;
            case NOT_STARTED:
                log.info("Upload not started");
                break;
        }
    }
}
