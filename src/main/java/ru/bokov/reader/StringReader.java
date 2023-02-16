package ru.bokov.reader;

import ru.bokov.exception.ReaderException;

import java.util.Optional;

public class StringReader extends AbstractReader<String> {

    public StringReader(String filename) throws ReaderException {
        super(filename);
    }

    @Override
    public Optional<String> readNext() {

        String nextLine;

        do {
            if (!scanner.hasNext()) {
                return Optional.empty();
            }
            nextLine = scanner.nextLine();
        } while (nextLine.contains(" "));

        return Optional.of(nextLine);
    }
}
