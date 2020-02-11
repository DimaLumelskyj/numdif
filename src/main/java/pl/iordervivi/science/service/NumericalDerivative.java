package pl.iordervivi.science.service;

import java.util.ArrayList;

public class NumericalDerivative {
    public static ArrayList<ArrayList<ArrayList<Double>>> CurveDerivativeCalc(ArrayList<ArrayList<Double>> inputCurve) {

        ArrayList<ArrayList<ArrayList<Double>>> result = new ArrayList<>();
        ArrayList<Double> inputX = new ArrayList<>();
        ArrayList<Double> inputY = new ArrayList<>();
        ArrayList<Double> calcdY;
        ArrayList<Double> calcd2Y;

        ArrayList<ArrayList<Double>> resultdY = new ArrayList<>();
        ArrayList<ArrayList<Double>> resultd2Y = new ArrayList<>();

        for (ArrayList<Double> row : inputCurve) {
            inputX.add(row.get(0));
            inputY.add(row.get(1));
        }

        calcdY = df1TableFunction(inputX, inputY);
        calcd2Y = df1TableFunction(inputX, calcdY);


        for (int i = 0; i < calcdY.size(); i++) {
            ArrayList<Double> calcRowdY = new ArrayList<>();
            ArrayList<Double> calcRowd2Y = new ArrayList<>();

            calcRowdY.add(inputX.get(i));
            calcRowdY.add(calcdY.get(i));
            calcRowd2Y.add(inputX.get(i));
            calcRowd2Y.add(calcd2Y.get(i));

            resultdY.add(calcRowdY);
            resultd2Y.add(calcRowd2Y);
        }

        result.add(resultdY);
        result.add(resultd2Y);

        return result;
    }

    static ArrayList<Double> df1TableFunction(ArrayList<Double> X, ArrayList<Double> Y) {
        //derivative calculation of the piecewise function
        //function of Y(X) in points X1 ... XN using three points.
        ArrayList<Double> retDY = new ArrayList<>();

        double d21, d32, d31;
        int i, n;
        //begin
        n = X.size();
        if (n < 3) {
            System.err.println("Error: n<3");
            return retDY;
        }

        d21 = (Y.get(2) - Y.get(1)) / (X.get(2) - X.get(1));
        d32 = (Y.get(3) - Y.get(2)) / (X.get(3) - X.get(2));
        d31 = (Y.get(3) - Y.get(1)) / (X.get(3) - X.get(1));

        retDY.add(d21 - d32 + d31);
        retDY.add(d21 + d32 - d31);

        for (i = 2; i < n - 1; i++) {
            d21 = d32;
            d32 = (Y.get(i + 1) - Y.get(i)) / (X.get(i + 1) - X.get(i));
            d31 = (Y.get(i + 1) - Y.get(i - 1)) / (X.get(i + 1) - X.get(i - 1));
            retDY.add(d21 + d32 - d31);
        }

        retDY.add(-d21 + d32 + d31);

        return retDY;
    }
}
