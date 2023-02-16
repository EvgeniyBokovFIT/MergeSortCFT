package ru.bokov.sorter;

import ru.bokov.exception.SorterException;
import ru.bokov.reader.AbstractReader;

import java.util.List;

public interface Sorter {
    <T extends Comparable<T>> void sort(List<AbstractReader<T>> readers) throws SorterException;
}
