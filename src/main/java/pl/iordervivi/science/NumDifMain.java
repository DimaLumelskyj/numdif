package pl.iordervivi.science;

import pl.iordervivi.science.service.CurvesService;
import pl.iordervivi.science.service.ReadDataTabText;

public class NumDifMain {
    public static void main(String[] args) {
        String fileName = "przemieszczenie.txt";
        String[] delims = {"\t", " "};

        ReadDataTabText dataReader = new ReadDataTabText();
        CurvesService curvesDB = new CurvesService(dataReader.getDataTab("", fileName, delims));
        curvesDB.writeToFileAllResult("output.txt  ");
    }
}
