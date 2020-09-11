package gui;

import artificial_neural_network.ImplementationOfNeuralNetwork;
import artificial_neural_network.Network;
import manageBytes.LoadBytes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Window extends JFrame implements ActionListener {

    private Skretch skretch;
    private JButton butt1, butt2, butt3, butt4;
    private JLabel label1, label2, label3, label4;
    private final ImplementationOfNeuralNetwork ion;

    public Window() {

        ion = new ImplementationOfNeuralNetwork();
        initGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == butt4){
            skretch.clear();
        }
        if(e.getSource() == butt2){
            ion.trainNetwork();
        }
        if(e.getSource() == butt1){
            ion.testNetwork();
        }
        if(e.getSource() == butt3){
            ion.guess(skretch.getSkretch());


        }
    }

    private void initGUI(){

        setTitle("Doodle classifier");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 640);
        setResizable(false);
        setVisible(true);
        setLayout(null);
        this.getContentPane().setBackground(new Color(11, 22, 33));
//      new Color(11, 22, 33)

        skretch = new Skretch();
        skretch.setBounds(20, 80, 420, 420);

        label1 = new JLabel("Testiranje");
        label2 = new JLabel("Treniranje");
        label3 = new JLabel("Brisanje");
        label4 = new JLabel("Predikcija");

        label1.setForeground(Color.WHITE);
        label2.setForeground(Color.WHITE);
        label3.setForeground(Color.WHITE);
        label4.setForeground(Color.WHITE);

        label1.setBounds(30, 10, 80, 40);
        label2.setBounds(150, 10, 80, 40);
        label3.setBounds(260, 10, 80, 40);
        label4.setBounds(370, 10, 80, 40);

        butt1 = new JButton("Test");
        butt2 = new JButton("Train");
        butt3 = new JButton("Predict");
        butt4 = new JButton("Clear");

        butt1.setBounds(30, 540, 80, 20);
        butt2.setBounds(140, 540, 80, 20);
        butt3.setBounds(250, 540, 80, 20);
        butt4.setBounds(360, 540, 80, 20);

        butt1.addActionListener(this);
        butt2.addActionListener(this);
        butt3.addActionListener(this);
        butt4.addActionListener(this);

        this.add(butt1);
        this.add(butt2);
        this.add(butt3);
        this.add(butt4);

        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);

        this.add(skretch);
    }
}
