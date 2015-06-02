package com.abudko.reseller.huuto.query;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

public class CustomStringHttpMessageConverter extends StringHttpMessageConverter {

    @Override
    protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        InputStream in = inputMessage.getBody();
        
        Assert.notNull(in, "No InputStream specified");
        StringBuilder out = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(in, charset);
        char[] buffer = new char[StreamUtils.BUFFER_SIZE];
        int bytesRead = -1;
        do {
            try {
            bytesRead = reader.read(buffer);
            }
            catch (EOFException e) {
                //
            }
            if (bytesRead != -1) {
                out.append(buffer, 0, bytesRead);
            }
        }
        while (bytesRead != -1);
        return out.toString();
    }
    
    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            return contentType.getCharSet();
        }
        else {
            return DEFAULT_CHARSET;
        }
    }
}
