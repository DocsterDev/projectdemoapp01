package com.demo.app;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class App {

    private static final double[] FILTER = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7};

    public static void main(String[] args) {
        log.info("Begin test");
        App app = new App();
        List<Double> data = Lists.newArrayList();
        app.createTestData(data);
        app.applyFilter(data, FILTER);
    }

    private void createTestData(List<Double> data) {
        log.info("Creating test data...");
        for (int i=0; i<100; i++) {
            data.add(Math.random());
        }
        log.info(data.toString());
    }

    private int calculateFilterLookback(double[] filter) {
        log.info("Calculating filter lookback length...");
        int lookback = filter.length / 2;
        log.info("Filter lookback: {}", lookback);
        return lookback;
    }

    private void applyFilter(List<Double> data, double[] filter) {
        int lookback = calculateFilterLookback(filter);

        log.info("Applying filter...");

        int dataSize = data.size();
        int filterSize = filter.length;
        int startIndex = lookback;
        int endIndex = dataSize - lookback;
        log.info("Data size: {}", dataSize);
        log.info("Filter size: {}", filterSize);
        log.info("Data start index: {}", startIndex);
        log.info("Data end index: {}", endIndex);

        // Validate input data
        validate(dataSize, filterSize);

        List<Double> filteredOutput = Lists.newArrayList();
        // Apply filter to data
        for (int i = startIndex; i < endIndex; i++) {
            List<Double> dataChunk = data.subList(i-lookback, i+(lookback+1));
            log.info("Data Chunk Index: {} to {}", i-lookback, i+lookback);
            log.info("Data Chunk: {}", dataChunk);
            double filteredValue = applyFilterSegment(dataChunk, filter);
            filteredOutput.add(filteredValue);
            log.info("Filtered Value: {}", filteredValue);
            log.info("Filtered Output: {}", filteredOutput);
        }
    }

    private double applyFilterSegment(List<Double> data, double[] filter) {
        double filteredValue = 0;
        for (int i = 0; i < data.size(); i++) {
            filteredValue += (data.get(i) * filter[i]);
        }
        return filteredValue;
    }

    private void validate(int dataSize, int filterSize) {
        if (filterSize > dataSize) {
            throw new RuntimeException("Data size must be greater than filter size");
        }
        if ((filterSize % 2) == 0 || filterSize == 1) {
            log.warn("Filter is an even length at {}. For best results, create a filter with an odd number of values.", filterSize);
        }
    }

}
