package pl.iordervivi.science.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Reads data from the text file and returns
 */
public class ReadDataTabText {
    //    private static Logger LOGGER = LoggerFactory.getLogger(ReadDataTabText.class);
    private List<String> loadFile(String workFolder, String fileName) {

        Path filePath = Paths.get(workFolder, fileName);
        Charset charset = StandardCharsets.UTF_8;
        List<String> lines = null;
        try {
            lines = Files.readAllLines(filePath, charset);
        } catch (IOException e) {
            System.out.println("Error read file: " + filePath);
            System.out.println(e.toString());
            System.exit(-1);
        }
        return lines;
    }

    private String parseDelims(String[] delims) {
        StringBuilder allDelims = new StringBuilder();
        int i = 0;
        for (String delim : delims) {
            allDelims.append(delim);
            if (i++ != delims.length - 1) {
                allDelims.append("|");
            }
        }
        return allDelims.toString();
    }

    public ArrayList<ArrayList<Double>> getDataTab(String workFolder, String fileName, String[] delims) {

        ArrayList<ArrayList<Double>> parsedTab = null;
        try {
            List<String> stringList = loadFile(workFolder, fileName);
            parsedTab = new ArrayList<>(stringList.size());
            String[] splitedLineVect;

            for (int i = 0; i < stringList.size(); i++) {
                ArrayList<Double> rowDouble = new ArrayList<>();
                splitedLineVect = stringList.get(i).split(parseDelims(delims));
                for (String number : splitedLineVect) {
                    rowDouble.add(Double.valueOf(number));
                }
                parsedTab.add(i, rowDouble);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array is out of Bounds" + e);
        } catch (ArithmeticException e) {
            System.out.println("Can't divide by Zero" + e);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return parsedTab;
    }
}
