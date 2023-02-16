package ru.bokov.reader;

import java.util.Optional;

public interface Reader<T> {
    Optional<T> readNext();
}
