package pl.iordervivi.science;

import pl.iordervivi.science.service.CurvesService;
import pl.iordervivi.science.service.ReadDataTabText;

public class NumDifMain {
    public static void main(String[] args) {
        String fileName = "displacment.txt";
        String[] delimiters = {"\t", " "};

        ReadDataTabText dataReader = new ReadDataTabText();
        CurvesService curvesDB = new CurvesService(dataReader.getDataTab("", fileName, delimiters));
        curvesDB.writeToFileAllResult("output.txt  ");
    }
}
