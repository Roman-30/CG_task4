package CG_task_4.models;

import CG_task_4.math.Vector3;
import CG_task_4.third.IModel;
import CG_task_4.third.PolyLine3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KleinBottle implements IModel {

    private double xConst;
    private double yConst;
    private double zConst;

    /**
     * @param xConst - Константа изменения всех X координат
     * @param yConst - Константа изменения всех Y координат
     * @param zConst - Константа изменения всех Z координат
     */

    public KleinBottle(double xConst, double yConst, double zConst) {
        this.xConst = xConst;
        this.yConst = yConst;
        this.zConst = zConst;
    }

    public KleinBottle() {
        this(15.0, 15.0, 15.0);
    }

    public void setXConst(double xConst) {
        this.xConst = xConst;
    }

    public void setYConst(double yConst) {
        this.yConst = yConst;
    }

    public void setZConst(double zConst) {
        this.zConst = zConst;
    }

    /**
     * @return Возвращает поле линии Бутылки Клейна.
     */
    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        final int N = 32;
        for (int ii = 0; ii < N; ii++) {
            double i = ii * 2 * Math.PI / N;
            double j = (ii + 1) * 2 * Math.PI / N;
            List<Vector3> a = verticalLines((float) i);
            List<Vector3> b = verticalLines((float) j);
            for (int k = 0; k < a.size(); k++) {
                Vector3 p1 = a.get(k);
                Vector3 p2 = b.get(k);
                Vector3 p3 = b.get((k + 1) % b.size());
                Vector3 p4 = a.get((k + 1) % a.size());
                lines.add(new PolyLine3D(Arrays.asList(p1, p2, p3, p4), true));
            }
        }
        return lines;
    }

    /**
     * @param n угловой коэффициент для окружности
     * @param i параматр угла относительно оси Y
     * @return Возвращает вектор окружности Бутылки Клейна
     */
    public List<Vector3> circleV(float n, float i) {
        List<Vector3> vectors = new ArrayList<>();
        double x = (2.0 / this.xConst) * Math.cos(i) * (3 * Math.cos(n) - 30 * Math.sin(i) + 90 * Math.pow(Math.cos(i), 4) * Math.sin(i) - 60 * Math.pow(Math.cos(i), 6) * Math.sin(i) + 5 * Math.cos(i) * Math.cos(n) * Math.sin(i));
        double y = (1.0 / this.yConst) * Math.sin(i) * (3 * Math.cos(n) - 3 * Math.pow(Math.cos(i), 2) - 48 * Math.pow(Math.cos(i), 4) * Math.cos(n) + 48 * Math.pow(Math.cos(i), 6) * Math.cos(n) + 60 * Math.sin(i) + 5 * Math.cos(i) * Math.cos(n) * Math.sin(i) - 5 * Math.pow(Math.cos(i), 3) * Math.cos(n) * Math.sin(i) - 80 * Math.pow(Math.cos(i), 5) * Math.cos(n) * Math.sin(i) + 80 * Math.pow(Math.cos(i), 7) * Math.cos(n) * Math.sin(i));
        double z = (2.0 / this.zConst) * (3 + 5 * Math.cos(i) * Math.sin(i)) * Math.sin(n);
        vectors.add(new Vector3((float) (x), (float) (y), (float) (z)));
        return vectors;
    }

    /**
     * @param n параматр угла относительно оси Y
     * @return Возвращает вектора сегмента Бутылки Клейна
     */
    public List<Vector3> verticalLines(float n) {
        List<Vector3> vectors = new ArrayList<>();
        final int N = 32;
        for (int ii = 0; ii < N; ii++) {
            double i = ii * Math.PI / N;
            double x = (2.0 / this.xConst) * Math.cos(i) * (
                    3 * Math.cos(n) - 30 * Math.sin(i) +
                            90 * Math.pow(Math.cos(i), 4) * Math.sin(i) -
                            60 * Math.pow(Math.cos(i), 6) * Math.sin(i) +
                            5 * Math.cos(i) * Math.cos(n) * Math.sin(i));
            double y = (1.0 / this.yConst) * Math.sin(i) * (
                    3 * Math.cos(n) - 3 * Math.pow(Math.cos(i), 2)*Math.cos(n) -
                    48 * Math.pow(Math.cos(i), 4) * Math.cos(n) +
                    48 * Math.pow(Math.cos(i), 6) * Math.cos(n) -
                    60 * Math.sin(i) +
                    5 * Math.cos(i) * Math.cos(n) * Math.sin(i) -
                    5 * Math.pow(Math.cos(i), 3) * Math.cos(n) * Math.sin(i) -
                    80 * Math.pow(Math.cos(i), 5) * Math.cos(n) * Math.sin(i) +
                    80 * Math.pow(Math.cos(i), 7) * Math.cos(n) * Math.sin(i));
            double z = (2.0 / this.zConst) *
                    (3 + 5 * Math.cos(i) * Math.sin(i)) *
                    Math.sin(n);

            vectors.add(new Vector3((float) (x), (float) (y), (float) (z)));
        }
        return vectors;
    }


    /**
     * @param n угловой коэффициент для окружнсти
     * @return Возвращает окружность из векторов
     */
    public List<Vector3> sfd3(float n) { //слеза
        List<Vector3> vectors = new ArrayList<>();
        for (double i = 0; i < 2 * Math.PI; i += 0.1) {
            double x = 0.5 * (1 - Math.cos(n)) * Math.sin(n) * Math.cos(i);
            double y = 0.5 * (1 - Math.cos(n)) * Math.sin(n) * Math.sin(i);
            double z = Math.cos(n);

            vectors.add(new Vector3((float) (x), (float) (y), (float) (z)));

        }
        return vectors;
    }
}