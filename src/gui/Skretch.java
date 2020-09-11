package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;

public class Skretch extends JComponent implements MouseMotionListener {

    private final int width = 560, height = 560;
    private BufferedImage skretch;
    private Graphics2D g;
    private int currentX, currentY, oldX, oldY;
    private final int radius = 20;
//    private JFrame window;
//    private JButton button1, button2, button3, button4;

    public Skretch() {

        skretch = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = skretch.createGraphics();
        addMouseMotionListener(this);

        clear();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        currentX = e.getX();
        currentY = e.getY();

        if(g != null){

            g.setPaint(Color.black);
            //     g.drawLine(oldX, oldY, currentX, currentY);
            g.fillOval(currentX - radius / 2, currentY - radius / 2, radius, radius);

            repaint();

            oldX = currentX;
            oldY = currentY;
        }


    }

    public void mousePressed(MouseEvent e){

        oldX = e.getX();
        oldY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(skretch, 0, 0, null);

    }

    public void clear(){
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setPaint(Color.black);
        repaint();
    }

    public BufferedImage getSkretch() {
        BufferedImage bimg = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);
        g.dispose();

        g = bimg.createGraphics();
        g.drawImage(skretch, 0, 0, 28, 28, this);
        g.dispose();
        g = skretch.createGraphics();

        return bimg;
    }
}
