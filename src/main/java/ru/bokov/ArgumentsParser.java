package ru.bokov;

import org.apache.commons.cli.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ArgumentsParser {

    private static final Integer MESSAGE_WIDTH = 100;
    private static final Integer LEFT_PADDING = 2;
    private static final Integer DESCRIPTION_PADDING = 5;

    private static SortInfo sortInfo;
    private static Options options;

    private ArgumentsParser() {
    }

    public static SortInfo getSortInfo() {
        return sortInfo;
    }

    public static void parse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        initOptions();
        sortInfo = new SortInfo();

        CommandLine commandLine = parser.parse(options, args);

        sortInfo.setSortOrder(SortOrder.ASC);
        if(commandLine.hasOption("d")) {
            sortInfo.setSortOrder(SortOrder.DESC);
        }

        sortInfo.setInputType(String.class);
        if(commandLine.hasOption("i")) {
            sortInfo.setInputType(Integer.class);
        }

        if(commandLine.getArgList().size() < 2) {
            throw new ParseException("Too few arguments");
        }

        sortInfo.setOutputFilename(parseOutputFilename(commandLine));
        sortInfo.setInputFilenames(parseInputFilenames(commandLine));
    }

    private static void initOptions() {
        options = new Options();
        OptionGroup sortOrder = new OptionGroup();
        sortOrder.addOption(Option.builder("a")
                .desc("sort ascending")
                .build());
        sortOrder.addOption(Option.builder("d")
                .desc("sort descending")
                .build());
        sortOrder.setRequired(false);
        options.addOptionGroup(sortOrder);

        OptionGroup dataType = new OptionGroup();
        dataType.addOption(Option.builder("s")
                .desc("sort strings")
                .build());
        dataType.addOption(Option.builder("i")
                .desc("sort integers")
                .build());
        dataType.setRequired(true);
        options.addOptionGroup(dataType);
    }

    public static void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        helpFormatter.printUsage(printWriter, MESSAGE_WIDTH,
                "java -jar MergeSortCFT-1.0-jar-with-dependencies.jar " +
                        "[-a|-d] {-i|-s} outfile.txt infile1.txt [infile2.txt...]");
        helpFormatter.printOptions(printWriter, MESSAGE_WIDTH, options,
                LEFT_PADDING, DESCRIPTION_PADDING);
        printWriter.close();
    }

    private static String parseOutputFilename(CommandLine commandLine) throws ParseException {
        String filename = commandLine.getArgList().get(0);

        if(!filename.endsWith(".txt")) {
            throw new ParseException("Invalid extension of file " + filename);
        }
        return filename;
    }

    private static List<String> parseInputFilenames(CommandLine commandLine) throws ParseException {
        List<String> argList = new ArrayList<>(commandLine.getArgList());
        argList.remove(0);

        List<String> filenames = new ArrayList<>();

        for (String filename : argList) {
            if(!filename.endsWith(".txt")) {
                System.err.println("Invalid extension of file " + filename +
                        ". File data will be skipped");
            } else {
                filenames.add(filename);
            }
        }

        if(filenames.isEmpty()) {
            throw new ParseException("Correctly specified input data files are missing");
        }

        return filenames;
    }
}
