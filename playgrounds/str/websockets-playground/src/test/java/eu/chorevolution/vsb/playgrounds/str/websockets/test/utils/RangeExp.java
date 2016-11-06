/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.chorevolution.vsb.playgrounds.str.websockets.test.utils;
import java.util.ArrayList;
import java.util.List;

public class RangeExp {
    private double rate;
    private List<Double> values = new ArrayList<Double>();

    public RangeExp(double r) {
        rate = r;
        values.add(r);
    }

    public double next() {
        double next = -Math.log( Math.pow(Math.E, -rate*60) * (1 - Math.random()) ) / rate;
        values.add(next);
        return next;
    }

    //modification
    public double average() {
        double average = 0;
        for (Double d : values) {
            average += d;
        }
        return average / values.size();
    }

    public static double exp(double lam) {
        return -Math.log(Math.random()) / lam;
    }
}
