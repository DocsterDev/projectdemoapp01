package com.demo.app.filter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleFilter implements Filter {

    private double[] filter;
    private int length;

    public SimpleFilter(int length) {
        this.filter = new double[length];
        this.length = this.filter.length;
        log.info("Initiated filter with value: {}", this.filter);
    }

    @Override
    public double[] getFilter() {
        return this.filter;
    }

    @Override
    public int getLength() {
        return this.length;
    }

}
