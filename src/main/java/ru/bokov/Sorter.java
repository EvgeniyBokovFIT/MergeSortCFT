package ru.bokov;

import ru.bokov.exception.SorterException;
import ru.bokov.reader.Reader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Sorter {
    private final SortInfo sortInfo;

    public Sorter(SortInfo sortInfo) {
        this.sortInfo = sortInfo;
    }

    public <T extends Comparable<T>> void sort(List<Reader<T>> readers) throws SorterException {
        try (FileWriter fileWriter = new FileWriter(sortInfo.getOutputFilename())) {
            var optionalReader = findReaderWithNextElem(readers);

            while (optionalReader.isPresent()) {

                doSortStep(optionalReader.get(), readers, fileWriter);

                optionalReader = findReaderWithNextElem(readers);
            }
        } catch (IOException e) {
            throw new SorterException(sortInfo.getOutputFilename() + " file processing error");
        }
    }


    private <T extends Comparable<T>> Optional<Reader<T>> findReaderWithNextElem(
            List<Reader<T>> readers) {
        if(sortInfo.getSortOrder().equals(SortOrder.ASC)) {
            return readers.stream()
                    .min(Comparator.comparing(Reader::getElement));
        }

        return readers.stream()
                .max(Comparator.comparing(Reader::getElement));
    }

    private <T extends Comparable<T>> boolean isSortedCorrectly(T curElement, T prevElement) {
        if(prevElement == null) {
            return true;
        }

        SortOrder sortOrder = sortInfo.getSortOrder();

        if(sortOrder.equals(SortOrder.ASC)) {
            return curElement.compareTo(prevElement) >= 0;
        }
        return curElement.compareTo(prevElement) <= 0;
    }

    private <T extends Comparable<T>> void removeReader(Reader<T> reader, List<Reader<T>> readers) {
        reader.close();
        readers.remove(reader);
    }

    private <T extends Comparable<T>> void doSortStep(
            Reader<T> reader, List<Reader<T>> readers, FileWriter fileWriter) throws IOException {

        if (!isSortedCorrectly(reader.getElement(), reader.getPrevElement())) {
            removeReader(reader, readers);
            return;
        }

        fileWriter.write(reader.getElement() + "\n");

        var optionalNextElem = reader.readNext();
        optionalNextElem.ifPresentOrElse(
                reader::setElement,
                () -> removeReader(reader, readers));

    }
}
