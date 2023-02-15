package ru.bokov;

import ru.bokov.exception.ReaderException;
import ru.bokov.exception.SorterException;
import ru.bokov.reader.IntegerReader;
import ru.bokov.reader.Reader;
import ru.bokov.reader.StringReader;
import org.apache.commons.cli.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ArgumentsParser.parse(args);
            SortInfo sortInfo = ArgumentsParser.getSortInfo();
            Sorter sorter = new Sorter(sortInfo);
            if(sortInfo.getInputType().equals(Integer.class)) {
                sorter.sort(makeIntegerReaders(sortInfo.getInputFilenames()));
            } else {
                sorter.sort(makeStringReaders(sortInfo.getInputFilenames()));
            }
        } catch (ParseException | SorterException e) {
            System.err.println(e.getMessage());
            ArgumentsParser.printHelp();
        }
    }

    private static List<Reader<Integer>> makeIntegerReaders(List<String> filenames) {
        List<Reader<Integer>> readers = new ArrayList<>();
        for (var filename: filenames) {
            try {
                readers.add(new IntegerReader(filename));
            } catch (ReaderException e) {
                System.err.println(e.getMessage());
            }
        }
        return readers;
    }

    private static List<Reader<String>> makeStringReaders(List<String> filenames) {
        List<Reader<String>> readers = new ArrayList<>();
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