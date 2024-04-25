package org.data_structures;

import static org.data_structures.utility.process.DataInitiator.initDataSets;
import static org.data_structures.utility.process.ExaminedGenerator.generateExamined;
import static org.data_structures.utility.process.FieldExamination.examFields;
import static org.data_structures.utility.process.ResultPrinter.mergeAndPrintResult;

public class Main {

    public static void main(String[] args) {
        mergeAndPrintResult(examFields(generateExamined(initDataSets())));
    }
}