package com.abudko.reseller.huuto.image;

import java.io.IOException;

import org.junit.Test;

public class ImageManipulatorTest {

    private ImageManipulator imageManipulator = new ImageManipulator();

    @Test
    public void test() throws IOException {
        String url = "http://kuvat2.huuto.net/c/9c/93c94d9bce19ad57340e3084b8deb-orig.jpg";
        imageManipulator.storeImage(url, "file:gaba.jpg", "vk.com/kombezi.finland");
    }

}