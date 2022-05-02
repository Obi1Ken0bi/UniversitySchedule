package ru.puzikov.universityschedule.misc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;
@Service
public class TelegramPropertiesReader {
    Reader fileReader;


    Properties properties = new Properties();

    public TelegramPropertiesReader() throws IOException {
        fileReader = new FileReader("src/main/resources/telegram.properties");
        properties.load(fileReader);
    }
    public  String getProperty(String property){
        return properties.getProperty(property);
    }
}
