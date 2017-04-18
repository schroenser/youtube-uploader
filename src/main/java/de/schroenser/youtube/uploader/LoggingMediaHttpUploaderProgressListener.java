package de.schroenser.youtube.uploader;

import java.io.IOException;
import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoggingMediaHttpUploaderProgressListener implements MediaHttpUploaderProgressListener
{
    private long lastNumBytesUploaded;
    private long lastProgressTimestamp;

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
                lastNumBytesUploaded = 0;
                lastProgressTimestamp = System.currentTimeMillis();
                break;
            case MEDIA_IN_PROGRESS:
                long deltaBytes = uploader.getNumBytesUploaded() - lastNumBytesUploaded;
                lastNumBytesUploaded = uploader.getNumBytesUploaded();
                long deltaTime = System.currentTimeMillis() - lastProgressTimestamp;
                lastProgressTimestamp = System.currentTimeMillis();
                long bytesPerSecond = deltaBytes * 1000 / deltaTime;
                BigDecimal percentage = BigDecimal.valueOf(uploader.getProgress() * 100.0)
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN);
                String speed = ByteCounts.toHumanReadable(bytesPerSecond, false) + "/s";
                log.info("Uploaded {}% at {}", percentage, speed);
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
