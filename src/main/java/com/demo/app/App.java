package com.demo.app;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class App {

    private static final double[] FILTER = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6};

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

    private void applyFilter(List<Double> data, double[] filter) {
        log.info("Applying filter...");
        int dataSize = data.size();
        int filterSize = filter.length;
        if (filterSize > dataSize) {
            throw new RuntimeException("Data size must be greater than filter size");
        }
        // Calculate chunks
        int chunks = dataSize / filterSize;
        // Calculate remainder
        int remainder = dataSize % filterSize;

        log.info("Data size: {}", dataSize);
        log.info("Filter size: {}", filterSize);
        log.info("Chunks: {}", chunks);
        log.info("Remainder: {}", remainder);

        // Apply filter to chunks
        for (int i=0; i<(dataSize-remainder); i+=(filterSize+1)) {
            List<Double> chunkData = data.subList(i, i+filterSize);
            log.info("Chunk : {} to {}", i, i+filterSize);
            log.info(chunkData.toString());
        }

        // Apply filter to remainder
        List<Double> chunkData = data.subList(dataSize-remainder, dataSize);
        log.info("Chunk : {} to {}", dataSize-remainder, dataSize);
        log.info(chunkData.toString());
    }

    private double[] applyFilterSegment(double[] data, double[] filter) {
        assert(data.length == filter.length);
        double[] filteredData = new double[data.length];
        for (int i=0; i<data.length; i++) {
            filteredData[i] = data[i] * filter [i];
        }
        return filteredData;
    }



}
