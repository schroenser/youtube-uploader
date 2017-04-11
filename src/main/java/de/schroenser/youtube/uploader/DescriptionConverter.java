package de.schroenser.youtube.uploader;

import com.beust.jcommander.IStringConverter;

public class DescriptionConverter implements IStringConverter<String>
{
    @Override
    public String convert(String value)
    {
        return value.replace("\\n", "\n");
    }
}
