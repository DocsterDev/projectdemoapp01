package com.dsp.image.processor.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageImporterUtil {

    public static void main(String[] arg) {
        log.info("Importing image...");
        ImageImporterUtil imageImporterUtil = new ImageImporterUtil();
        try {
            imageImporterUtil.getImageDimensions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void getImageDimensions() throws IOException {
        BufferedImage image = ImageIO.read(new File("test-01.png"));

        int width = image.getWidth();
        int height = image.getHeight();

        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        for (int y=0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x=0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                String pixels = String.format("[%s %s %s]", red, green, blue);
                sb.append(String.format("%-15s", pixels));
            }
            System.out.println(sb.toString());
        }
    }

}
