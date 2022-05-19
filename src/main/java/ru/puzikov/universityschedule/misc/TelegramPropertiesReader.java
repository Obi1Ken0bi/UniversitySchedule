package ru.puzikov.universityschedule.misc;


import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

@Service
public class TelegramPropertiesReader {
    final Properties properties = new Properties();
    Reader fileReader;

    public TelegramPropertiesReader() throws IOException {
        fileReader = new FileReader("src/main/resources/telegram.properties");
        properties.load(fileReader);
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}
