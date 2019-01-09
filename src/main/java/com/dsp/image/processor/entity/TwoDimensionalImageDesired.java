package com.dsp.image.processor.entity;

import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class TwoDimensionalImageDesired extends Image {

    static final double INIT_SCOPE_PERCENTAGE = 0.25;

    private double intensity;
    private double mu;
    private int filterSize;

    public TwoDimensionalImageDesired(BufferedImage image, int filterSize) {
        init(image);
        this.filterSize = filterSize;
        calculateImageIntensity();
    }

    private void calculateImageIntensity() {
        int initScopeWidth = (int) (getWidth() * INIT_SCOPE_PERCENTAGE);
        int initScopeHeight = (int) (getHeight() * INIT_SCOPE_PERCENTAGE);
        int startIndexX = initScopeWidth / 2;
        int endIndexX = startIndexX + initScopeWidth;
        int startIndexY = initScopeHeight / 2;
        int endIndexY = startIndexY + initScopeHeight;
        this.intensity = 0;
        for (int y=startIndexY; y<endIndexY; y++) {
            for (int x=startIndexX; x<endIndexX; x++) {
                this.intensity = this.intensity + (getPixel(x, y) ^ 2);
            }
        }
        int initialScopePixelCount = initScopeWidth * initScopeHeight;
        this.mu = (0.50 * initialScopePixelCount) / (this.intensity * this.filterSize);
    }

}
