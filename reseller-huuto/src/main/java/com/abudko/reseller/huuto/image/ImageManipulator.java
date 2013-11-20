package com.abudko.reseller.huuto.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageManipulator {
    
    public void storeImage(URL url, String outputFile) throws IOException {
        BufferedImage image = ImageIO.read(url);
        BufferedImage subimage = image.getSubimage(0, 0, image.getWidth(), image.getHeight() * 28 / 30);
        File outputfile = new File(outputFile);
        ImageIO.write(subimage, "jpg", outputfile);
    }
    
    public void cropImage() {
        
    }

}
