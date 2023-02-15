package ru.bokov.reader;

import ru.bokov.exception.ReaderException;

import java.util.Optional;

public class IntegerReader extends Reader<Integer>{

    public IntegerReader(String filename) throws ReaderException{
        super(filename);
    }

    @Override
    public Optional<Integer> readNext() {

        String nextLine;

        do {
            if (!scanner.hasNext()) {
                return Optional.empty();
            }
            nextLine = scanner.nextLine();
        } while (!isInteger(nextLine));

        return Optional.of(Integer.parseInt(nextLine));
    }

    private boolean isInteger(String string) {
        if (string == null) {
            return false;
        }
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
