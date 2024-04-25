package org.data_structures.utility.process;

import lombok.extern.java.Log;
import org.data_structures.utility.annotation.Exam;
import org.data_structures.utility.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

@Log
public class FieldExamination {
    private FieldExamination() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Pair<String, Object>> examFields(List<Pair<String, Object>> measurements) {
        log.info("Objects are being examined...");
        measurements.forEach(pair -> {
            var examObj = pair.getSecond();
            Arrays.stream(examObj.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Exam.class))
                    .forEach(method -> {
                        try {
                            method.invoke(examObj);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
        });

        return measurements;
    }
}
