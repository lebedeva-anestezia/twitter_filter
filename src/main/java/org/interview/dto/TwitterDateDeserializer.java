package org.interview.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TwitterDateDeserializer extends StdDeserializer<Date> {
    private static SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
    public TwitterDateDeserializer(){
        this(null);
    }
    public TwitterDateDeserializer(Class<?> c){
        super(c);
    }
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException, JsonProcessingException {
        String date = jsonParser.getText();
        simpleDateFormat.setLenient(true);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            // TODO: think about exception
            throw new RuntimeException(e);
        }
    }
}
