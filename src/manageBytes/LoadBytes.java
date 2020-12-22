package manageBytes;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class LoadBytes {

    public static byte[][] getThemAsMatrix(String filePath, int off, int length, int imageSize){

        byte[][] images = new byte[length - off][imageSize * imageSize];
        byte[] rawBytes = new byte[(length - off) * imageSize * imageSize];

        try{
            //ByteinputStream je trebalo
            FileInputStream fis = new FileInputStream(filePath);
            fis.read(rawBytes, off, length);
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i = 0; i < images.length; i++){
            for(int j = 0; j < images[0].length; j++){
                images[i][j] =  rawBytes[i * images[0].length + j];
            }
        }

        return images;
    }

    public static byte[][] reverseColorImage(byte[][] mat){
        byte[][] reverseMat = new byte[mat.length][mat[0].length];

        for(int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat[0].length; j++){
                reverseMat[i][j] = (byte)(255 - (int)mat[i][j]);
            }
        }
        return reverseMat;
    }

//    public static byte[][] getImage(byte[][] array){
//
//        int numberOfImages = 10;
//        byte[][] imageMatrix = new byte[numberOfImages * numberOfImages][numberOfImages * numberOfImages];
//
//        for(int i = 0; i < numberOfImages * numberOfImages; i++){
//            for(int j = 0; j < numberOfImages * numberOfImages; j++){
//
//                imageMatrix[j % numberOfImages + i % numberOfImages * numberOfImages][j / numberOfImages + i / numberOfImages * numberOfImages] = array[i][j];
//            }
//        }
//
//        return imageMatrix;
//    }

    public static byte[][] getImage(byte[][] array, int imageSize, int rows, int colls){

        byte[][] imageMatrix = new byte[rows * imageSize][colls * imageSize];

        for(int i = 0; i < rows * colls; i++){
            for(int j = 0; j < imageSize * imageSize; j++){

                imageMatrix[j % imageSize + i % rows * imageSize][j / imageSize + i / colls * imageSize] = array[i][j];
            }
        }

        return imageMatrix;
    }

    public static int fileSize(String path){
        int size = 0;
        try {
            FileInputStream fis = new FileInputStream(path);
            size = fis.available();
        }catch (Exception e){

        }
        return size;
    }

    public static void schuffleTrainingSetsAndTargets(double[][] matrix, double[][] mat){

        Random r = new Random();

        for(int i = 0; i < matrix.length; i++){
            int numb = r.nextInt(matrix.length);
            double[] array = matrix[i];
            double[] array2 = mat[i];
            matrix[i] = matrix[numb];
            mat[i] = mat[numb];
            matrix[numb] = array;
            mat[numb] = array2;
        }
    }

    public static byte[][] readAll(String filePath){
        byte[][] images = null;
        byte[] rawBytes = null;

        try{
            rawBytes = Files.readAllBytes(Path.of(filePath));
            images = new byte[rawBytes.length / 784][784];
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i = 0; i < images.length; i++){
            for(int j = 0; j < images[0].length; j++){
                images[i][j] =  rawBytes[i * images[0].length + j];
            }
        }

        return images;
    }


}

