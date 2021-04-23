package artificial_neural_network;

import manageBytes.LoadBytes;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class ImplementationOfNeuralNetwork {

    private double[][] allTrainingSets;
    private double[][] targets;
    private double[][] allTestingSets;
    private double[][] testingTargets;
    private final int setNumb = 3;
    private final int setLength = 10000;
    private final int imageSize = 28;
    private final int trainingSetLength = (int) (setLength * 0.7);

    private Network ann;

    public ImplementationOfNeuralNetwork() {

        ann = new Network(784, 64, 32, 9, 3);
        initSets();
    }


    private void initSets() {

        byte[][][] trainingSet = new byte[setNumb][trainingSetLength][];
        byte[][][] testingSet = new byte[setNumb][setLength - trainingSetLength][];

        byte[][][] data = new byte[setNumb][][];
        data[0] = LoadBytes.readAll("resources\\cats1000.bin");
        data[1] = LoadBytes.readAll("resources\\trains1000.bin");
        data[2] = LoadBytes.readAll("resources\\rainbows1000.bin");

        for (int i = 0; i < setNumb; i++) {
            for (int j = 0; j < trainingSetLength; j++) {
                trainingSet[i][j] = data[i][j];
            }
        }
        for (int i = 0; i < setNumb; i++) {
            for (int j = 0; j < setLength - trainingSetLength; j++) {
                testingSet[i][j] = data[i][j + trainingSetLength];
            }
        }


        allTrainingSets = new double[trainingSetLength * setNumb][imageSize * imageSize];
        targets = new double[trainingSetLength * setNumb][];
        allTestingSets = new double[(setLength - trainingSetLength) * setNumb][imageSize * imageSize];
        testingTargets = new double[(setLength - trainingSetLength) * setNumb][];

        for (int i = 0; i < setNumb; i++) {
            for (int j = 0; j < trainingSetLength; j++) {
                for (int k = 0; k < imageSize * imageSize; k++) {
                    int rgb = 255 - (trainingSet[i][j][k] & 0xFF);
                    double color = (double) rgb / 255.0;
                    allTrainingSets[i * trainingSetLength + j][k] = color;

                    if (i * trainingSetLength + j < trainingSetLength) {
                        targets[i * trainingSetLength + j] = new double[]{1.0, 0.0, 0.0};
                    } else if (i * trainingSetLength + j >= trainingSetLength && i * trainingSetLength + j < trainingSetLength * 2) {
                        targets[i * trainingSetLength + j] = new double[]{0.0, 1.0, 0.0};
                    } else if (i * trainingSetLength + j >= trainingSetLength * 2 && i * trainingSetLength + j < trainingSetLength * setNumb) {
                        targets[i * trainingSetLength + j] = new double[]{0.0, 0.0, 1.0};
                    }
                }
            }
            LoadBytes.schuffleTrainingSetsAndTargets(allTestingSets, testingTargets);
        }


        for (int i = 0; i < setNumb; i++) {
            for (int j = 0; j < (setLength - trainingSetLength); j++) {
                for (int k = 0; k < imageSize * imageSize; k++) {
                    int rgb = 255 - (trainingSet[i][j][k] & 0xFF);
                    double color = (double) rgb / 255.0;
                    allTestingSets[i * (setLength - trainingSetLength) + j][k] = color;

                    if (i * (setLength - trainingSetLength) + j < (setLength - trainingSetLength)) {
                        testingTargets[i * (setLength - trainingSetLength) + j] = new double[]{1.0, 0.0, 0.0};
                    } else if (i * (setLength - trainingSetLength) + j >= (setLength - trainingSetLength) && i * (setLength - trainingSetLength) + j < (setLength - trainingSetLength) * 2) {
                        testingTargets[i * (setLength - trainingSetLength) + j] = new double[]{0.0, 1.0, 0.0};
                    } else if ((setLength - trainingSetLength) * i + j >= (setLength - trainingSetLength) * 2 && (setLength - trainingSetLength) * i + j < (setLength - trainingSetLength) * setNumb) {
                        testingTargets[i * (setLength - trainingSetLength) + j] = new double[]{0.0, 0.0, 1.0};
                    }
                }
            }
        }
    }

    public void testNetwork() {

        double guesses = 0;
        double[] guess;
        double[] target;
        for (int i = 0; i < allTestingSets.length; i++) {
            guess = ann.calculate(allTestingSets[i]);
            target = testingTargets[i];
            int highestGuess = NetworkTools.indexOfHighestValue(guess);
            int highestTarget = NetworkTools.indexOfHighestValue(target);
//            System.out.println(Arrays.toString(guess) + " " + highestGuess);
//            System.out.println(Arrays.toString(target) + " " + highestTarget);

            if (highestGuess == highestTarget) {
                guesses++;
            }
        }
        double accuracy = guesses / allTestingSets.length * 100;
        ann.printWeights();
        System.out.println("Accuracy is: " + accuracy + "%");
    }

    public void guess(BufferedImage img){

        double[] crtez = new double[imageSize * imageSize];

        for (int i =0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++){
                double color = (double) (img.getRGB(i, j) & 0xFF) / 255.0;
                crtez[i * img.getHeight() + j] = color;
            }

            double[] guess = ann.calculate(crtez);
            int highestGuess = NetworkTools.indexOfHighestValue(guess);

            if(highestGuess == 0){
                System.out.println("MACKA");
            }else if(highestGuess == 1){
                System.out.println("VOZ");

            }else {
                System.out.println("DUGA");

            }

            BufferedImage bimg = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);

            for(int g = 0; g < 28; g++){
                for (int j = 0; j < 28; j++){
                    bimg.setRGB(g, j, img.getRGB(g, j));
                }
            }

            try {
                ImageIO.write(bimg, "png", new File("C:\\Users\\DT User6\\Desktop\\slika28.png"));
            }catch (Exception e){

            }
        }

    }

    public void trainNetwork() {

        int l = 10;
        for (int j = 0; j < l; j++) {
            LoadBytes.schuffleTrainingSetsAndTargets(allTrainingSets, targets);
            for (int i = 0; i < allTrainingSets.length; i++) {
                ann.train(allTrainingSets[i], targets[i], 0.01);
//                System.out.println(Arrays.toString(allTrainingSets[i]));
            }
            System.out.println("Eppoch done " + Math.floor((double) j / l * 100) + "%");
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public void storeWeights(double[][][] weights, String filePath) {
        try {
            FileWriter fw = new FileWriter(new File("filePath"));
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 1; i < weights.length; i++){
                for (int j = 0; j < weights[i].length; j++){
                    bw.write(Arrays.toString(weights[i][j]));

                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {

        }
    }

//    public double[][][] loadWeights(String filePath){
//
//        BufferedReader br = null;
//        double[][][] weights =  new double[1][1][1];
//        String line = null;
//
//
//        try{
//            br = new BufferedReader(new FileReader(filePath));
//        }catch (Exception e){
//
//            while ((line = br.readLine()) != br != null){
//
//                System.out.println(line);
//            }
//        }
//
//
//        return weights;
//    }
}