package com.dsp.image.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class OneDimensionalProcessor {

    private static final double[] FILTER = {0.1, 0.2, 0.3, 0.4, 0.5};

    public static void main(String[] args) {
        log.info("Begin test");
        OneDimensionalProcessor oneDimensionalProcessor = new OneDimensionalProcessor();
        List<Double> data = Lists.newArrayList();
        oneDimensionalProcessor.createTestData(data);
        oneDimensionalProcessor.applyFilter(data, FILTER);
    }

    private void createTestData(List<Double> data) {
        log.info("Creating test data...");
        for (int i = 0; i < 100; i++) {
            data.add(Math.random());
        }
        log.info(data.toString());
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

        Map<String, Double> filteredArray = Maps.newLinkedHashMap();
        // Apply filter to data
        for (int i = startIndex; i < endIndex; i++) {
            List<Double> dataChunk = data.subList(i-lookback, i+(lookback+1));
            log.info("Data Chunk Index: {} to {}", i-lookback, i+lookback);
            log.info("Data Chunk: {}", dataChunk);
            double filteredValue = applyFilterSegment(dataChunk, filter);
            String label = String.format("y[%s]", i);
            filteredArray.put(label, filteredValue);
            log.info("Filtered Value y[{}]: {}", i, filteredValue);
            log.info("Filtered Array: {}", filteredArray);
        }
    }

    private double applyFilterSegment(List<Double> data, double[] filter) {
        double filteredValue = 0;
        for (int i = 0; i < data.size(); i++) {
            filteredValue += (data.get(i) * filter[i]);
        }
        return filteredValue;
    }

    private int calculateFilterLookback(double[] filter) {
        log.info("Calculating filter lookback length...");
        int lookback = filter.length / 2;
        log.info("Filter lookback: {}", lookback);
        return lookback;
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
