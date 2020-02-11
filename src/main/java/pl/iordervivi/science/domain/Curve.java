package pl.iordervivi.science.domain;

import lombok.Getter;
import pl.iordervivi.science.service.NumericalDerivative;

import java.util.ArrayList;

@Getter
public class Curve {

    private String name;
    private String xName;
    private String yName;
    private String dyName;
    private String d2yName;
    private ArrayList<ArrayList<Double>> curveXYData;
    private ArrayList<ArrayList<Double>> dCurve;
    private ArrayList<ArrayList<Double>> d2Curve;
    private boolean isDerivatives;

    public Curve(String name, String xName, String yName, ArrayList<ArrayList<Double>> curveXYdata, boolean isDerivatives) {
        this.name = name;
        this.xName = xName;
        this.yName = yName;
        this.curveXYData = curveXYdata;
        this.dyName = "d" + yName + "/dt";
        this.d2yName = "d2" + yName + "/dt^2";
        this.isDerivatives = isDerivatives;
        if (this.isDerivatives) {
            ArrayList<ArrayList<ArrayList<Double>>> derivative = NumericalDerivative.CurveDerivativeCalc(curveXYdata);
            dCurve = derivative.get(0);
            d2Curve = derivative.get(1);
        }
    }
}
