package com.abudko.reseller.huuto.image;

import java.io.IOException;

import org.junit.Test;

public abstract class ImageManipulatorTest {

    private ImageManipulator imageManipulator = new ImageManipulator();

    @Test
    public void test() throws IOException {
        String url = "http://kuvat2.huuto.net/8/39/8988038c2ebacc8836b3b40f8d30e-orig.jpg";
        imageManipulator.storeImage(url, "file:gaba.jpg", "пересыл по России бесплатно");
    }

}