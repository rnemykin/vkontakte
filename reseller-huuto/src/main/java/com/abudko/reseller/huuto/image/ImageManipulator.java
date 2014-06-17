package com.abudko.reseller.huuto.image;

import java.awt.Color;
import java.awt.Graphics;
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

    public void storeImage(String imageUrlStr, String outputFile, String addText) {
        log.info(String.format("Croping image from %s and storing in %s", imageUrlStr, outputFile));

        try {
            URL url = new URL(imageUrlStr);
            BufferedImage image = ImageIO.read(url);
            BufferedImage subimage = cropImage(image);
            addTextToImage(subimage, addText);
            URL output = new URL(outputFile);
            File outputfile = new File(output.getFile());
            ImageIO.write(subimage, "jpg", outputfile);
        } catch (IOException e) {
            String error = String.format("Exception while cropping image from url %s", imageUrlStr);
            log.error(error);
            throw new RuntimeException(error, e);
        }
    }

    private BufferedImage cropImage(BufferedImage image) {
        return image.getSubimage(0, 0, image.getWidth(), image.getHeight() * 28 / 30);
    }

    private void addTextToImage(BufferedImage image, String addText) {
        if (addText != null) {
            Graphics g = image.getGraphics();
            float fontSize = 22f;
            g.setFont(g.getFont().deriveFont(fontSize));
            g.setColor(Color.WHITE);
            final int x = 5;
            final int y = (int) fontSize;
            g.drawString(addText, x, y);
            g.dispose();
        }
    }
}
