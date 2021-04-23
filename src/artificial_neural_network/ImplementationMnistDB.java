package artificial_neural_network;

import manageBytes.LoadBytes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImplementationMnistDB {

    private byte[] trainingSets;
    private byte[] trainingLabels;
    private byte[] testingSets;
    private byte[] testingLabels;

    ImplementationMnistDB() throws IOException {
        initSets();
    }

    private void initSets() throws IOException {

        testingSets = Files.readAllBytes(Paths.get("C:\\Users\\DT User6\\Documents\\NeuralNetwork\\datasets\\t10k-images.idx3-ubyte"));
        testingLabels = Files.readAllBytes(Paths.get("C:\\Users\\DT User6\\Documents\\NeuralNetwork\\datasets\\t10k-labels.idx1-ubyte"));
        trainingSets = Files.readAllBytes(Paths.get("C:\\Users\\DT User6\\Documents\\NeuralNetwork\\datasets\\train-images.idx3-ubyte"));
        trainingLabels = Files.readAllBytes(Paths.get("C:\\Users\\DT User6\\Documents\\NeuralNetwork\\datasets\\train-labels.idx1-ubyte"));

        int[] slicica = new int[28 * 28];

        int g = 0;

        for(int i = 0; i < 2051 * 28 * 28; i+= 2051){
            int rgb = 255 - (trainingSets[i] & 0xFF);
//            double color = (double) rgb / 255.0;
            slicica[g] = rgb;
            g++;
        }

        int[][] sllicica = new int [28][28];

        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 28; j++){
                sllicica[i][j] = slicica[i * 28 + j];
            }
        }

        BufferedImage img = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < 28; i++){
            for(int j = 0; j < 28; j++){
                img.setRGB(i, j, sllicica[i][j]);
            }
        }

        ImageIO.write(img, "png", new File("slicica.png"));
    }



}
