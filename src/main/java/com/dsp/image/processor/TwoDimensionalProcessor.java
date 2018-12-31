package com.dsp.image.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class TwoDimensionalProcessor {

    private static final double[] FILTER = {0.1, 0.2, 0.3, 0.4, 0.5};

    public static void main(String[] args){
        log.info("Start 2D processor...");
        List<List<Double>> data = Lists.newArrayList();
        TwoDimensionalProcessor twoDimensionalProcessor = new TwoDimensionalProcessor();
        twoDimensionalProcessor.createTestData(data);
        twoDimensionalProcessor.applyFilter(data, FILTER);
    }

    private void createTestData(List<List<Double>> data) {
        log.info("Creating test data...");
        for (int i = 0; i < 100; i++) {
            List<Double> x = Lists.newArrayList();
            for (int j = 0; j < 100; j++) {
                x.add(Math.random());
            }
            data.add(x);
        }
        log.info(data.toString());
    }

    private void applyFilter(List<List<Double>> data, double[] filter) {
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

        List<Map<String, Double>> filtered2DArray = Lists.newArrayList();
        for (List<Double> x : data) {
            Map<String, Double> filteredArray = Maps.newLinkedHashMap();
            // Apply filter to data
            for (int i = startIndex; i < endIndex; i++) {
                List<Double> dataChunk = x.subList(i-lookback, i+(lookback+1));
                log.debug("Data Chunk Index: {} to {}", i-lookback, i+lookback);
                log.debug("Data Chunk: {}", dataChunk);
                double filteredValue = applyFilterSegment(dataChunk, filter);
                String label = String.format("y[%s]", i);
                filteredArray.put(label, filteredValue);
                log.debug("Filtered Value y[{}]: {}", i, filteredValue);
                log.debug("Filtered Array: {}", filteredArray);
            }
            filtered2DArray.add(filteredArray);
            log.info(filteredArray.toString());
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

}
