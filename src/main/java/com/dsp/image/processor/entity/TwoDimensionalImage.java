package com.dsp.image.processor.entity;

import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class TwoDimensionalImage extends Image {

    public TwoDimensionalImage(int width, int height) {
        init(width, height);
    }

    public TwoDimensionalImage(BufferedImage image) {
        init(image);
    }

}
