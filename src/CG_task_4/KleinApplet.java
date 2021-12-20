package CG_task_4;

import java.applet.Applet;
import java.awt.*;
import java.util.Date;

public class KleinApplet extends Applet implements Runnable {
    gCanvas2 tafel;
    iPanel2 instructionPanel;
    Thread runner;
    Date currentTime;

    public void init() {
        setBackground(Color.white);
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);
        GridBagConstraints c = new GridBagConstraints();
        currentTime = new Date();
        tafel = new gCanvas2();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 20.0;
        c.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(tafel, c);
        add(tafel);
        instructionPanel = new iPanel2();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 0.0;
        gridbag.setConstraints(instructionPanel, c);
        add(instructionPanel);
    }

    public void start() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    public void stop() {
        if (runner != null) {
            runner.stop();
            runner = null;
        }
    }

    public void run() {
        while (true) {
            currentTime = new Date();
            tafel.updateLocation();
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

}

class iPanel2 extends Panel {
    iPanel2() {
        setLayout(new BorderLayout());
        Label l = new Label("2-Dimensionsal Crab Moving in a Klein Bottle.", Label.CENTER);
        add("Center", l);
    }
}

class gCanvas2 extends Canvas {
    Color rectBackground, topColor, bottomColor;
    double locationx = 0.45, locationy = 0.45;
    double vx, vy;
    double crabwidth = 0.2, crabheight = 0.14;
    Point Origin;
    int xscale, yscale;

    gCanvas2() {
        setBackground(new Color(255, 255, 192));
        rectBackground = new Color(192, 255, 255);
        topColor = Color.red;
        bottomColor = Color.green;
        vx = 2 * Math.PI / 100;
        vy = 4.0 / 100;
        Origin = new Point(30, 20);
        xscale = 330;
        yscale = 250;
        repaint();
    }

    private void ConvertCoordinates(double x, double y, Point p) {
        p.x = (int) (Origin.x + xscale * x);
        p.y = (int) (Origin.y + yscale * y);
    }

    public void updateLocation() {
        Color tempColor;

        locationx = locationx + vx;
        locationy = locationy + vy;
        if (locationy < 0)
            locationy = locationy + 1.0;
        if (locationy > 1.0)
            locationy = locationy - 1.0;
        if (locationx > 1.0) {
            locationx = locationx - 1.0;
            locationy = 1.0 - locationy - crabheight;
            vy = -vy;
            tempColor = topColor;
            topColor = bottomColor;
            bottomColor = tempColor;
        }
        repaint();
    }

    public void paint(Graphics g) {
        Point Coords = new Point(0, 0);

        g.clipRect(Origin.x, Origin.y, xscale, yscale);
        g.setColor(rectBackground);
        g.fillRect(Origin.x, Origin.y, xscale, yscale);

        ConvertCoordinates(locationx, locationy, Coords);
        g.setColor(topColor);
        g.fillArc(Coords.x, Coords.y, (int) (xscale * crabwidth), (int) (yscale * crabheight), 30, 180);
        g.setColor(bottomColor);
        g.fillArc(Coords.x, Coords.y, (int) (xscale * crabwidth), (int) (yscale * crabheight), 180, 150);
        if (locationx > 1 - crabwidth) {
            ConvertCoordinates(locationx - 1, 1 - locationy - crabheight, Coords);
            g.setColor(bottomColor);
            g.fillArc(Coords.x, Coords.y, (int) (xscale * crabwidth), (int) (yscale * crabheight), 30, 180);
            g.setColor(topColor);
            g.fillArc(Coords.x, Coords.y, (int) (xscale * crabwidth), (int) (yscale * crabheight), 180, 150);
        }
        if (locationy > 1 - crabheight) {
            ConvertCoordinates(locationx, locationy - 1, Coords);
            g.setColor(topColor);
            g.fillArc(Coords.x, Coords.y, (int) (xscale * crabwidth), (int) (yscale * crabheight), 30, 180);
            g.setColor(bottomColor);
            g.fillArc(Coords.x, Coords.y, (int) (xscale * crabwidth), (int) (yscale * crabheight), 180, 150);
        }
        if ((locationx > 1 - crabwidth) && (locationy > 1 - crabheight)) {
            ConvertCoordinates(locationx - 1, locationy, Coords);
            g.setColor(bottomColor);
            g.fillArc(Coords.x, Coords.y, (int) (xscale * crabwidth), (int) (yscale * crabheight), 30, 180);
            g.setColor(topColor);
            g.fillArc(Coords.x, Coords.y, (int) (xscale * crabwidth), (int) (yscale * crabheight), 180, 150);
        }
    }
}
