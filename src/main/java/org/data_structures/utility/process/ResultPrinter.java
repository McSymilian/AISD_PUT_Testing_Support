package org.data_structures.utility.process;

import lombok.extern.java.Log;
import org.data_structures.utility.annotation.Duration;
import org.data_structures.utility.annotation.Examined;
import org.data_structures.utility.annotation.Factor;
import org.data_structures.utility.annotation.Measurement;
import org.data_structures.utility.Pair;
import org.data_structures.utility.annotation.Scale;
import org.data_structures.utility.exception.FactorSearchException;
import org.data_structures.utility.exception.ResultFileCreationException;
import org.data_structures.utility.exception.ResultMergingException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
public class ResultPrinter {
    private ResultPrinter() {
        throw new IllegalStateException("Utility class");
    }

    public static void mergeAndPrintResult(List<Pair<String, Object>> examinedResultList) {
        log.info("Results are being merged and printed...");
        examinedResultList.stream()
                .filter(it -> it.second().getClass().isAnnotationPresent(Examined.class))
                .collect(Collectors.groupingBy(Pair::first))
                .forEach((dataGeneratorName, structures) -> structures.stream()
                        .collect(Collectors.groupingBy(it -> it.second().getClass().getSimpleName()))
                        .forEach((structureName, list) -> {
                            var file = createFile(dataGeneratorName, structureName);

                            var measuredList = list.stream()
                                    .map(Pair::second)
                                    .collect(Collectors.groupingBy(ResultPrinter::groupByScale))
                                    .entrySet()
                                    .stream()
                                    .flatMap(entry -> entry
                                            .getValue()
                                            .stream()
                                            .collect(Collectors.groupingBy(it -> {
                                                var factor = Arrays.stream(it.getClass().getDeclaredMethods())
                                                        .filter(method -> method.isAnnotationPresent(Factor.class))
                                                        .findFirst();
                                                if (factor.isPresent()) {
                                                    try {
                                                        return (Comparable<?>) factor.get().invoke(it);
                                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                                        throw new ResultMergingException(e);
                                                    }
                                                } else {
                                                    return 0L;
                                                }
                                            }))
                                            .values()
                                            .stream()
                                            .map(objects -> {
                                                var tmp = Map.Entry.copyOf(entry);
                                                tmp.setValue(objects);
                                                return tmp;
                                            })
                                            .toList()
                                            .stream()
                                    )
                                    .map(ResultPrinter::mapToMapOfColumnNameAndAverageValue)
                                    .sorted(Comparator.comparingDouble(it -> it.get("size")))
                                    .toList();

                            List<String> columnNames = measuredList.getFirst()
                                    .keySet()
                                    .stream()
                                    .sorted((a, b) -> {
                                        if (a.equals(scaleName)) return -1;
                                        else if (b.equals(scaleName)) return 1;
                                        else return a.compareTo(b);
                                    })
                                    .toList();

                            try (Writer writer = new FileWriter(file)) {
                                printColumnNames(columnNames, writer);
                                printRows(measuredList, columnNames, writer);

                            } catch (IOException e) {
                                throw new ResultFileCreationException(e);
                            }
                        }));
    }

    private static void printRows(List<HashMap<String, Double>> measuredList, List<String> columnNames, Writer writer) {
        measuredList.forEach(printRow(columnNames, writer));
    }

    private static Consumer<HashMap<String, Double>> printRow(List<String> columnNames, Writer writer) {
        return map -> {
            columnNames.forEach(value -> {
                try {
                    writer.append(String.valueOf(map.get(value))).append(";");
                } catch (IOException e) {
                    throw new ResultFileCreationException("Error while writing durations", e);
                }
            });
            try {
                writer.append("\n");
            } catch (IOException e) {
                throw new ResultFileCreationException(e);
            }
        };
    }

    private static void printColumnNames(List<String> columnNames, Writer writer) throws IOException {
        columnNames.forEach(key -> {
            try {
                writer.append(key).append(";");
            } catch (IOException e) {
                throw new ResultFileCreationException("Error while writing columns names to file", e);
            }
        });
        writer.append("\n");
    }

    private static File createFile(String dataGeneratorName, String structureName) {
        String fileName = "data/%s".formatted(dataGeneratorName);
        var file = new File(fileName);
        file.mkdirs();

        fileName = "%s/%s.csv".formatted(fileName, structureName);
        file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new ResultFileCreationException(e);
            }
        }
        return file;
    }

    private static String scaleName = null;
    private static Long groupByScale(Object it) {
        var scale = Arrays.stream(it.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Scale.class))
                .findFirst();
        if (scale.isPresent()) {
            try {
                if (scaleName == null)
                    scaleName = scale.get().getAnnotation(Scale.class).value();
                return (long) scale.get().invoke(it);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ResultMergingException(e);
            }
        } else {
            return 0L;
        }
    }

    private static HashMap<String, Double> mapToMapOfColumnNameAndAverageValue(Map.Entry<Long, List<Object>> entry) {
        var innerMeasurementsMap = new HashMap<String, Double>();
        long scale = entry.getKey();
        innerMeasurementsMap.put(scaleName == null ? "scale" : scaleName, (double) scale);

        var input = entry.getValue();
        Arrays.stream(
                        input.getFirst().getClass()
                                .getDeclaredMethods()
                )
                .filter(method -> method.isAnnotationPresent(Factor.class)
                )
                .collect(Collectors.toSet())
                .forEach(method-> {
            var factorName = method.getAnnotation(Factor.class).value();
            try {
                innerMeasurementsMap.put(factorName, (Double) method.invoke(entry.getValue().getFirst()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new FactorSearchException(e);
            }
        });

        var listToMeasure = entry.getValue();
        var durationsAndMeasurementMethods = Arrays.stream(
                        listToMeasure.getFirst().getClass()
                                .getDeclaredMethods()
                )
                .filter(method -> method.isAnnotationPresent(Measurement.class) ||
                        method.isAnnotationPresent(Duration.class)
                )
                .collect(Collectors.toSet())
                .stream()
                .toList();

        var valuesListsMap = new HashMap<String, List<Long>>();
        durationsAndMeasurementMethods.forEach(method -> {
            var fieldName = method.isAnnotationPresent(Measurement.class) ?
                    method.getAnnotation(Measurement.class).value() :
                    method.getAnnotation(Duration.class).value();
            valuesListsMap.put(fieldName, new ArrayList<>());
            listToMeasure.forEach(object -> {
                try {
                    valuesListsMap.get(fieldName).add((long) method.invoke(object));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new ResultMergingException(e);
                }
            });
        });

        var computedValues = new HashMap<String, Double>();
        valuesListsMap.forEach((fieldName, values) -> {
            var average = values.stream().mapToLong(Long::longValue).average().orElse(0);
            computedValues.put(fieldName, average);

            var standardDeviation = Math.sqrt(
                    values.stream()
                            .mapToDouble(value -> Math.pow(value - average, 2))
                            .sum() / values.size()
            );
            computedValues.put(fieldName + "_deviation", standardDeviation);
        });

        innerMeasurementsMap.putAll(computedValues);
        return innerMeasurementsMap;
    }

}
