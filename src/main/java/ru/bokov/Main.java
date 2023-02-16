package ru.bokov;

import ru.bokov.exception.ReaderException;
import ru.bokov.exception.SorterException;
import ru.bokov.reader.IntegerReader;
import ru.bokov.reader.AbstractReader;
import ru.bokov.reader.StringReader;
import org.apache.commons.cli.ParseException;
import ru.bokov.sorter.MergeSorter;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ArgumentsParser.parse(args);
            SortInfo sortInfo = ArgumentsParser.getSortInfo();
            MergeSorter mergeSorter = new MergeSorter(sortInfo);
            if(sortInfo.getInputType().equals(Integer.class)) {
                mergeSorter.sort(makeIntegerReaders(sortInfo.getInputFilenames()));
            } else {
                mergeSorter.sort(makeStringReaders(sortInfo.getInputFilenames()));
            }
        } catch (ParseException | SorterException e) {
            System.err.println(e.getMessage());
            ArgumentsParser.printHelp();
        }
    }

    private static List<AbstractReader<Integer>> makeIntegerReaders(List<String> filenames) {
        List<AbstractReader<Integer>> readers = new ArrayList<>();
        for (var filename: filenames) {
            try {
                readers.add(new IntegerReader(filename));
            } catch (ReaderException e) {
                System.err.println(e.getMessage());
            }
        }
        return readers;
    }

    private static List<AbstractReader<String>> makeStringReaders(List<String> filenames) {
        List<AbstractReader<String>> readers = new ArrayList<>();
        for (var filename: filenames) {
            try {
                readers.add(new StringReader(filename));
            } catch (ReaderException e) {
                System.err.println(e.getMessage());
            }
        }
        return readers;
    }
}