package com.dsp.image.processor;

import com.dsp.image.processor.entity.TwoDimensionalImageDesired;
import com.dsp.image.processor.entity.TwoDimensionalImage;
import com.dsp.image.processor.filter.TwoDimensionalFilter;
import lombok.Data;
import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Data
public class TwoDimensionalImageProcessor {

    public static void main(String[] args) {
        int filterSize = 11;
        try {
            BufferedImage input = ImageIO.read(new File("test-01.png"));
            BufferedImage desired = ImageIO.read(new File("test-01.png"));
            TwoDimensionalImageProcessor twoDimensionalImageProcessor = new TwoDimensionalImageProcessor(input, desired, filterSize);
            twoDimensionalImageProcessor.init();
            twoDimensionalImageProcessor.process();
        } catch (IOException e) {
            throw new RuntimeException("Error reading input images", e);
        }
    }

    @NonNull
    private int filterSize;
    private BufferedImage input;
    private BufferedImage desired;
    private TwoDimensionalImage twoDimensionalImageInput;
    private TwoDimensionalImage twoDimensionalImageFiltered;
    private TwoDimensionalImageDesired twoDimensionalImageDesired;
    private TwoDimensionalFilter twoDimensionalFilter;

    public TwoDimensionalImageProcessor(BufferedImage input, BufferedImage desired, int filterSize) {
        this.filterSize = filterSize;
        this.input = input;
        this.desired = desired;
    }

    public void init() {
        this.twoDimensionalImageInput = new TwoDimensionalImage(this.input);
        this.twoDimensionalImageDesired = new TwoDimensionalImageDesired(this.desired, this.filterSize);
        this.twoDimensionalImageFiltered = new TwoDimensionalImage(twoDimensionalImageInput.getWidth(), twoDimensionalImageInput.getHeight());
        this.twoDimensionalFilter = new TwoDimensionalFilter(this.filterSize);
    }

    public void process() {

    }



}
