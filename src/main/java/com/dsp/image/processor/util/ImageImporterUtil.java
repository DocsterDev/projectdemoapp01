package com.dsp.image.processor.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageImporterUtil {

    public static void main(String[] arg) {
        log.info("Importing image...");
        ImageImporterUtil imageImporterUtil = new ImageImporterUtil();
        try {
            //imageImporterUtil.loadImageFromDisk();
            imageImporterUtil.saveImageToDisk();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadImageFromDisk() throws IOException {
        BufferedImage image = ImageIO.read(new File("test-01.png"));
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                String pixels = String.format("[%s %s %s]", color.getRed(), color.getGreen(), color.getBlue());
                sb.append(String.format("%-14s", pixels));
            }
            System.out.println(sb.toString());
        }
    }

    private void saveImageToDisk() throws IOException {
        BufferedImage image = ImageIO.read(new File("test-01.png"));
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true);


                /**
                 *
                 * If pixel is black, convert pixel to blue
                 *
                 *
                  */
                if (color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0) {
                    Color c2 = new Color(0, 0, 255);
                    image.setRGB(x, y, c2.getRGB());
                }


                Color updatedColor = new Color(image.getRGB(x, y), true);
                String pixels = String.format("[%s %s %s]",  updatedColor.getRed(), updatedColor.getGreen(), updatedColor.getBlue());
                sb.append(String.format("%-14s", pixels));
            }
            System.out.println(sb.toString());
        }
        ImageIO.write(image, "png", new File("test-01-modded.png"));
    }

}
