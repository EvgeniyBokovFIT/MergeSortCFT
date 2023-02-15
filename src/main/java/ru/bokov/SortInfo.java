package ru.bokov;

import java.util.List;

public class SortInfo {
    private Class<? extends Comparable<?>> inputType;
    private SortOrder sortOrder;
    private String outputFilename;
    private List<String> inputFilenames;

    public SortInfo() {

    }

    public SortInfo(Class<? extends Comparable<?>> inputType, SortOrder sortOrder, String outputFilename, List<String> inputFilenames) {
        this.inputType = inputType;
        this.sortOrder = sortOrder;
        this.outputFilename = outputFilename;
        this.inputFilenames = inputFilenames;
    }

    public Class<? extends Comparable<?>> getInputType() {
        return inputType;
    }

    public void setInputType(Class<? extends Comparable<?>> inputType) {
        this.inputType = inputType;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getOutputFilename() {
        return outputFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    public List<String> getInputFilenames() {
        return inputFilenames;
    }

    public void setInputFilenames(List<String> inputFilenames) {
        this.inputFilenames = inputFilenames;
    }
}
