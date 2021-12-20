/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CG_task_4.models;

import CG_task_4.math.Vector3;
import CG_task_4.third.IModel;
import CG_task_4.third.PolyLine3D;

import java.util.Arrays;
import java.util.List;

/**
 * Описывает трёхмерный отрезок
 * @author Alexey
 */
public class Line3D implements IModel {
    private Vector3 p1, p2;

    public Line3D(Vector3 p1, Vector3 p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public List<PolyLine3D> getLines() {
        return Arrays.asList(new PolyLine3D(
                Arrays.asList(p1, p2)
            , false));
    }
    
}
