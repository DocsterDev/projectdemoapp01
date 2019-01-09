package com.dsp.image.processor.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SimpleFilter implements Filter {

    private double[] filter;
    private int length;

    public SimpleFilter(int length) {
        this.filter = new double[length];
        this.length = this.filter.length;
        log.info("Initiated filter with value: {}", this.filter);
    }

    public void init() {
        if (length == 0) {
            throw new RuntimeException("Filter length must be defined and greater than 0");
        }
    }

    @Override
    public int getLength() {
        return this.length;
    }

}
