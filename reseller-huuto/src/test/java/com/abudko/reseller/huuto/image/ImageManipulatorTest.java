package com.abudko.reseller.huuto.image;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

public class ImageManipulatorTest {
    
    private ImageManipulator imageManipulator = new ImageManipulator();

    @Test
    public void test() throws IOException {
        URL url = new URL("http://kuvat2.huuto.net/c/9c/93c94d9bce19ad57340e3084b8deb-orig.jpg");
        imageManipulator.storeImage(url, "gaba.jpg");
    }

}
