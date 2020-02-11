package pl.iordervivi.science.service;

import pl.iordervivi.science.domain.Curve;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurvesService {
    private ArrayList<Curve> curvesDB = new ArrayList<>();

    public CurvesService(ArrayList<ArrayList<Double>> rawTextData) {

        switch (rawTextData.get(0).size()) {
            case 2://one curve input
                Curve epsCurve = new Curve("Curve EPS", "Time", "Eps", rawTextData, true);
                curvesDB.add(epsCurve);
                break;

            case 4://two curves input time eps1 and time eps2
                ArrayList<ArrayList<Double>> eps1Data = new ArrayList<>();
                ArrayList<ArrayList<Double>> eps2Data = new ArrayList<>();
                ArrayList<ArrayList<Double>> eps3Data = new ArrayList<>();

                for (ArrayList<Double> row : rawTextData) {
                    ArrayList<Double> e3Row = new ArrayList<>();
                    ArrayList<Double> e1Row = new ArrayList<>();
                    ArrayList<Double> e2Row = new ArrayList<>();

                    e1Row.add(row.get(0));
                    e1Row.add(row.get(1));
                    e2Row.add(row.get(2));
                    e2Row.add(row.get(3));
                    e3Row.add(row.get(0));
                    e3Row.add(row.get(1) + row.get(3));

                    eps1Data.add(e1Row);
                    eps2Data.add(e2Row);
                    eps3Data.add(e3Row);
                }
                Curve eps1Curve = new Curve("Curve EPS1", "Time", "Eps1", eps1Data, true);
                Curve eps2Curve = new Curve("Curve EPS2", "Time", "Eps2", eps2Data, true);
                Curve eps3Curve = new Curve("Curve EPS3", "Time", "Eps3", eps3Data, true);
                curvesDB.add(eps1Curve);
                curvesDB.add(eps2Curve);
                curvesDB.add(eps3Curve);
                break;
            case 8:
                ArrayList<ArrayList<Double>> eps1DataB = new ArrayList<>();
                ArrayList<ArrayList<Double>> eps2DataB = new ArrayList<>();
                ArrayList<ArrayList<Double>> eps3DataB = new ArrayList<>();

                ArrayList<ArrayList<Double>> eps1DataA = new ArrayList<>();
                ArrayList<ArrayList<Double>> eps2DataA = new ArrayList<>();
                ArrayList<ArrayList<Double>> eps3DataA = new ArrayList<>();

                for (ArrayList<Double> row : rawTextData) {
                    ArrayList<Double> e3RowB = new ArrayList<>();
                    ArrayList<Double> e1RowB = new ArrayList<>();
                    ArrayList<Double> e2RowB = new ArrayList<>();
                    ArrayList<Double> e3RowA = new ArrayList<>();
                    ArrayList<Double> e1RowA = new ArrayList<>();
                    ArrayList<Double> e2RowA = new ArrayList<>();

                    /*B*/
                    e1RowB.add(row.get(0)); //time
                    e1RowB.add(row.get(1)); //eps1
                    e2RowB.add(row.get(2)); //time
                    e2RowB.add(row.get(3)); //eps2
                    e3RowB.add(row.get(0)); //time
                    e3RowB.add(row.get(1) + row.get(3)); //eps3
                    /*A*/
                    e1RowA.add(row.get(4)); //time
                    e1RowA.add(row.get(5)); //eps1
                    e2RowA.add(row.get(6)); //time
                    e2RowA.add(row.get(7)); //eps2
                    e3RowA.add(row.get(4)); //time
                    e3RowA.add(row.get(5) + row.get(7)); //eps3

                    eps1DataB.add(e1RowB);
                    eps2DataB.add(e2RowB);
                    eps3DataB.add(e3RowB);

                    eps1DataA.add(e1RowA);
                    eps2DataA.add(e2RowA);
                    eps3DataA.add(e3RowA);

                }

                Curve eps1CurveB = new Curve("Curve EPS1 B", "Time", "Eps1B", eps1DataB, true);
                Curve eps2CurveB = new Curve("Curve EPS2 B", "Time", "Eps2B", eps2DataB, true);
                Curve eps3CurveB = new Curve("Curve EPS3 B", "Time", "Eps3B", eps3DataB, true);
                Curve eps1CurveA = new Curve("Curve EPS1 A", "Time", "Eps1A", eps1DataA, true);
                Curve eps2CurveA = new Curve("Curve EPS2 A", "Time", "Eps2A", eps2DataA, true);
                Curve eps3CurveA = new Curve("Curve EPS3 A", "Time", "Eps3A", eps3DataA, true);

                curvesDB.add(eps1CurveB);
                curvesDB.add(eps2CurveB);
                curvesDB.add(eps3CurveB);
                curvesDB.add(eps1CurveA);
                curvesDB.add(eps2CurveA);
                curvesDB.add(eps3CurveA);

                break;
            default:
                System.out.println("unknown size of epsilon input from txt data file. EXIT");
                System.exit(-1);
                break;
        }
    }

    public void writeToFileAllResult(String filename) {
        //assuming all curves have the same size - number of points in curves are equal
        String[] outputData = new String[curvesDB.get(0).getCurveXYData().size()];
        List<String> curveNames = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();

        Arrays.fill(outputData, "");

        if (curvesDB.size() == 6) { //if there is Curves for B and A zones calculating deps1B/deps1A and deps3B/deps3A

            ArrayList<ArrayList<Double>> deps1CurveDataB = null;
            ArrayList<ArrayList<Double>> deps3CurveDataB = null;
            ArrayList<ArrayList<Double>> deps1CurveDataA = null;
            ArrayList<ArrayList<Double>> deps3CurveDataA = null;
            ArrayList<ArrayList<Double>> divDeps1BDeps1A = new ArrayList<>();
            ArrayList<ArrayList<Double>> divDeps3BDeps3A = new ArrayList<>();

            for (Curve curve : curvesDB) {
                if (curve.getName().equals("Curve EPS1 B")) {
                    deps1CurveDataB = curve.getD2Curve();
                }
                if (curve.getName().equals("Curve EPS3 B")) {
                    deps3CurveDataB = curve.getDCurve();
                }
                if (curve.getName().equals("Curve EPS1 A")) {
                    deps1CurveDataA = curve.getDCurve();
                }
                if (curve.getName().equals("Curve EPS3 A")) {
                    deps3CurveDataA = curve.getDCurve();
                }
            }

            if (deps1CurveDataB != null && deps3CurveDataB != null &&
                    deps1CurveDataA != null && deps3CurveDataA != null) {
                for (int i = 0; i < deps1CurveDataB.size(); i++) {
                    ArrayList<Double> rowDivDeps1BDeps1A = new ArrayList<>();
                    ArrayList<Double> rowDivDeps3BDeps3A = new ArrayList<>();

                    rowDivDeps1BDeps1A.add(deps1CurveDataB.get(i).get(0));
                    rowDivDeps1BDeps1A.add(deps1CurveDataB.get(i).get(1) / deps1CurveDataA.get(i).get(1));
                    divDeps1BDeps1A.add(i, rowDivDeps1BDeps1A);

                    rowDivDeps3BDeps3A.add(deps3CurveDataB.get(i).get(0));
                    rowDivDeps3BDeps3A.add(deps3CurveDataB.get(i).get(1) / deps3CurveDataA.get(i).get(1));
                    divDeps3BDeps3A.add(i, rowDivDeps3BDeps3A);
                }
            }

            Curve divEeps1CurveBA = new Curve("Curve dEPS1B/dEPS1A", "Time", "dEPS1B/dEPS1A", divDeps1BDeps1A, false);
            Curve divEeps3CurveBA = new Curve("Curve dEPS3B/dEPS3A", "Time", "dEPS3B/dEPS3A", divDeps3BDeps3A, false);
            curvesDB.add(divEeps1CurveBA);
            curvesDB.add(divEeps3CurveBA);

        }

        for (int iCurve = 0; iCurve < curvesDB.size(); iCurve++) {

            ArrayList<ArrayList<Double>> curveData = curvesDB.get(iCurve).getCurveXYData();
            ArrayList<ArrayList<Double>> dCurveData = curvesDB.get(iCurve).getDCurve();
            ArrayList<ArrayList<Double>> d2CurveData = curvesDB.get(iCurve).getD2Curve();

            curveNames.add(curvesDB.get(iCurve).getName());
            columnNames.add(curvesDB.get(iCurve).getXName());
            columnNames.add(curvesDB.get(iCurve).getYName());
            if (curvesDB.get(iCurve).isDerivatives()) {
                columnNames.add(curvesDB.get(iCurve).getXName());
                columnNames.add(curvesDB.get(iCurve).getDyName());
                columnNames.add(curvesDB.get(iCurve).getXName());
                columnNames.add(curvesDB.get(iCurve).getD2yName());
            }

            for (int i = 0; i < curveData.size(); i++) {
                if (curvesDB.get(iCurve).isDerivatives()) {
                    outputData[i] += curveData.get(i).get(0).toString() + "\t" + curveData.get(i).get(1).toString() + "\t" +
                            dCurveData.get(i).get(0).toString() + "\t" + dCurveData.get(i).get(1).toString() + "\t" +
                            d2CurveData.get(i).get(0).toString() + "\t" + d2CurveData.get(i).get(1).toString();

                } else {
                    outputData[i] += curveData.get(i).get(0).toString() + "\t" + curveData.get(i).get(1).toString();
                }
                if (iCurve != curvesDB.size() - 1) {
                    outputData[i] += "\t";
                }
            }
        }

        writeToFile(outputData, curveNames, columnNames, filename);

    }

    private void writeToFile(String[] outputData, List<String> curveNames, List<String> columnNames, String filename) {
        int size = outputData.length;

        StringBuilder beginLines = new StringBuilder();


        for (int i = 0; i < curveNames.size(); i++) {
            beginLines.append(curveNames.get(i));
            if (curveNames.get(i).equals("Curve dEPS1B/dEPS1A") || curveNames.get(i).equals("Curve dEPS3B/dEPS3A")) {
                if (i != (curveNames.size() - 1)) {
                    beginLines.append("\t\t");
                } else {
                    beginLines.append("\n");
                }
            } else {
                if (i != (curveNames.size() - 1)) {
                    beginLines.append("\t\t\t\t\t\t");
                } else {
                    beginLines.append("\n");
                }
            }
        }

        for (int i = 0; i < columnNames.size(); i++) {
            beginLines.append(columnNames.get(i));
            beginLines.append("\t");
            beginLines.append(columnNames.get(i + 1));
            i++;
            if (i != (columnNames.size() - 1)) {
                beginLines.append("\t");
            } else {
                beginLines.append("\n");
            }
        }

        try {
            // Create file
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(beginLines.toString());
            for (String outputDatum : outputData) {
                out.write(outputDatum + "\n");
            }
            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

}
