package com.dsp.image.processor.entity;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

@Data
public class Image{

    private int width;
    private int height;
    private long pixelCount;
    private int[][] pixelArray;

    public void init(int width, int height){
        this.width = width;
        this.height = height;
        this.pixelCount = width * height;
        this.pixelArray = new int[width][height];
    }

    public void init(BufferedImage image) {
        if (Objects.isNull(image)) {
            throw new RuntimeException("Image cannot be null");
        }
        init(image.getWidth(), image.getHeight());
        convertImageToPixelArray(image);
    }

    public int getPixel(int x, int y) {
        return this.pixelArray[y][x];
    }

    public void setPixel(int x, int y, int value) {
        this.pixelArray[y][x] = value;
    }

    private void convertImageToPixelArray(BufferedImage image) {
        System.out.println("Converting image to pixel array...");
        for (int y = 0; y < this.height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < this.width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                int pixel = color.getRed(); // < --- Only grabbing red value to convert to grayscale
                this.pixelArray[y][x] = pixel;
                sb.append(String.format("%-5s", pixel));
            }
            System.out.println(sb.toString());
        }
    }

}
