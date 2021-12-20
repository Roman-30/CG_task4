/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CG_task_4;

import CG_task_4.draw.IDrawer;
import CG_task_4.draw.SimpleEdgeDrawer;
import CG_task_4.math.Matrix4Factories;
import CG_task_4.models.KleinBottle;
import CG_task_4.screen.ScreenConverter;
import CG_task_4.third.Camera;
import CG_task_4.third.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
    private int velocity;
    private Timer t;
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;
    private KleinBottle bottle;
    private TextField x;
    private TextField y;
    private TextField z;
    private TextField r;
    private boolean X = false;
    private boolean Y = false;
    private boolean Z = false;

    public DrawPanel() {
        super();

        Label labelX = new Label("X");
        labelX.setBackground(Color.WHITE);
        x = new TextField();
        x.setSize(75, 35);
        this.add(labelX);
        this.add(x);

        Label labelY = new Label("Y");
        labelY.setBackground(Color.WHITE);
        y = new TextField();
        y.setSize(75, 35);
        this.add(labelY);
        this.add(y);

        Label labelZ = new Label("Z");
        labelZ.setBackground(Color.WHITE);
        z = new TextField();
        z.setSize(75, 35);
        this.add(labelZ);
        this.add(z);

        Label labelR = new Label("R");
        labelZ.setBackground(Color.WHITE);
        r = new TextField();
        r.setSize(75, 35);
        this.add(labelR);
        this.add(r);


        x.addActionListener(e -> {
            try {
                bottle.setXConst(Double.parseDouble(x.getText()));
                repaint();
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        });

        y.addActionListener(e -> {
            try {
                bottle.setYConst(Double.parseDouble(y.getText()));
                repaint();
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        });

        z.addActionListener(e -> {
            try {
                bottle.setZConst(Double.parseDouble(z.getText()));
                repaint();
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        });

        JRadioButton b1 = new JRadioButton("X");
        JRadioButton b2 = new JRadioButton("Y");
        JRadioButton b3 = new JRadioButton("Z");
        Button b = new Button("cansel");

        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b);

        ButtonGroup group = new ButtonGroup();
        group.add(b1);
        group.add(b2);
        group.add(b3);

        sc = new ScreenConverter(-4, 4, 8, 8, 4, 4);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();

//        scene.getModelsList().add(new Parallelepiped(
//                new Vector3(-0.4f, -0.4f, -0.4f),
//                new Vector3(0.4f, 0.4f, 0.4f)
//        ));
        bottle = new KleinBottle();
        scene.getModelsList().add(bottle);

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);

        double ratio = 0.1;
        velocity = 100;
        final int[] time = {0};
        t = new Timer(100, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(velocity);
                if (X) {
                    double da = 0.12;
                    double db = 1.12;
                    cam.modifyRotate(Matrix4Factories.rotationXYZ(da, Matrix4Factories.Axis.Y).mul(
                            Matrix4Factories.rotationXYZ(db, Matrix4Factories.Axis.X)
                            )
                    );
                } else if (Y) {
                    double da = 2.12;
                    double db = 1.12;
                    cam.modifyRotate(Matrix4Factories.rotationXYZ(da, Matrix4Factories.Axis.Y).mul(
                            Matrix4Factories.rotationXYZ(db, Matrix4Factories.Axis.X)
                            )
                    );
                } else if (Z) {
                    double da = 0.12;
                    double db = 1.12;
                    cam.modifyRotate(Matrix4Factories.rotationXYZ(da, Matrix4Factories.Axis.Y).mul(
                            Matrix4Factories.rotationXYZ(db, Matrix4Factories.Axis.X)
                            )
                    );
                }

                repaint();
            }
        });

        b1.addActionListener(e -> {
            X = true;
            Y = false;
            Z = false;
            t.start();
        });


        b2.addActionListener(e -> {
            X = false;
            Y = true;
            Z = false;
            t.start();
        });


        b3.addActionListener(e -> {
            X = false;
            Y = false;
            Z = true;
            t.start();
        });

        b.addActionListener(e -> {
            t.stop();
            X = false;
            Y = false;
            Z = false;
        });

        r.addActionListener(e -> {

            int n = Integer.parseInt(r.getText());
            t.setDelay(n);
            setVelocity(n);
        });
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
