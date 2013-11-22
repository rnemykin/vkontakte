package com.abudko.reseller.huuto.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImageManipulator {

    private Logger log = LoggerFactory.getLogger(getClass());

    public void storeImage(String urlStr, String outputFile) {
        try {
            URL url = new URL(urlStr);
            BufferedImage image = ImageIO.read(url);
            BufferedImage subimage = image.getSubimage(0, 0, image.getWidth(), image.getHeight() * 28 / 30);
            URL output = new URL(outputFile);
            File outputfile = new File(output.getFile());
            ImageIO.write(subimage, "jpg", outputfile);
        } catch (IOException e) {
            String error = String.format("Exception while cropping image from url %s", urlStr);
            log.error(error);
            throw new RuntimeException(error, e);
        }
    }
}
