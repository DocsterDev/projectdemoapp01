package com.dsp.image.processor.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class TwoDimensionalFilter implements Filter {

    private double[][] filter;
    private int size;
    private int filterPixelCount;

    public TwoDimensionalFilter(int size) {
        this.size = size;
        log.info("Initiated filter with value: {}", this.filter);
    }

    @Override
    public void init() {
        if (this.size == 0) {
            throw new RuntimeException("Filter length must be defined and greater than 0");
        }
        this.filter = new double[size][size];
        this.filterPixelCount = size ^ 2;
    }

    @Override
    public void printFilter() {
        System.out.println("Printing Filter...");
        for (int i=0; i<this.size; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j=0; j<this.size; j++) {
                double val = this.filter[i][j];
                sb.append(String.format("%-5s", val));
            }
            System.out.println(sb.toString());
        }
    }

    public void setFilterPixel(int x, int y, double value) {
        this.filter[y][x] = value;
    }

    public double getFilterPixel(int x, int y) {
        return this.filter[y][x];
    }

    public void setFilter(double[][] filter){
        if (filter.length != this.filter.length) {
            throw new RuntimeException("Filter lengths do not match. Should be length of " + this.size);
        }
        this.filter = filter;
    }

}
