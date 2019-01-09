package com.dsp.image.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class OneDimensionalProcessor {

    private static final double[] FILTER = {0.1, 0.2, 0.3, 0.4, 0.5};

    public static void main(String[] args) {
        System.out.println("Begin test");
        OneDimensionalProcessor oneDimensionalProcessor = new OneDimensionalProcessor();
        List<Double> data = Lists.newArrayList();
        oneDimensionalProcessor.createTestData(data);
        oneDimensionalProcessor.applyFilter(data, FILTER);
    }

    private void createTestData(List<Double> data) {
        System.out.println("Creating test data...");
        for (int i = 0; i < 100; i++) {
            data.add(Math.random());
        }
        System.out.println(data.toString());
    }

    private void applyFilter(List<Double> data, double[] filter) {
        int lookback = calculateFilterLookback(filter);

        System.out.println("Applying filter...");

        int dataSize = data.size();
        int filterSize = filter.length;
        int startIndex = lookback;
        int endIndex = dataSize - lookback;
        System.out.println("Data size: " + dataSize);
        System.out.println("Filter size: " + filterSize);

        // Validate input data
        validate(dataSize, filterSize);

        Map<String, Double> filteredArray = Maps.newLinkedHashMap();
        // Apply filter to data
        for (int i = startIndex; i < endIndex; i++) {
            List<Double> dataChunk = data.subList(i-lookback, i+(lookback+1));
            System.out.println(String.format("Data Chunk Index: %s to %s", i-lookback, i+lookback));
            System.out.println(String.format("Data Chunk: %s", dataChunk));
            double filteredValue = applyFilterSegment(dataChunk, filter);
            String label = String.format("y[%s]", i);
            filteredArray.put(label, filteredValue);
            System.out.println(String.format("Filtered Value y[%s]: %s", i, filteredValue));
            System.out.println("Filtered Array: {}" + filteredArray);
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
        System.out.println("Calculating filter lookback length...");
        int lookback = filter.length / 2;
        System.out.println("Filter lookback: " + lookback);
        return lookback;
    }

    private void validate(int dataSize, int filterSize) {
        if (filterSize > dataSize) {
            throw new RuntimeException("Data size must be greater than filter size");
        }
        if ((filterSize % 2) == 0 || filterSize == 1) {
            System.err.println("Filter is an even length at " + filterSize + ". For best results, create a filter with an odd number of values.");
        }
    }

}
