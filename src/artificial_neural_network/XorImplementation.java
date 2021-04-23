package artificial_neural_network;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class XorImplementation {

    private Network net;

    XorImplementation(){
        net = new Network(2, 4, 4, 2);

        //Za inpute imamo sve moguce kombinacije xor-a
        double[][] inputi = new double[4][];
        inputi[0] = new double[]{0, 0};
        inputi[1] = new double[]{0, 1};
        inputi[2] = new double[]{1, 0};
        inputi[3] = new double[]{1, 1};

        for(int i = 0; i < 50000; i++){

            //Biramo nasumicnu kombinaciju xor-a
            Random dice = new Random();
            int a = dice.nextInt(4);

            double[] c = null;

            //Definisemo rjesenje xora, prvi clan niza predstavlja 0 a drugi 1
            if(a == 0){
                c = new double[]{1, 0};
            }else if(a == 1){
                c = new double[]{0, 1};
            }else if(a == 2){
                c = new double[]{0, 1};
            }else if(a == 3){
                c = new double[]{1, 0};
            }

            //Treniramo mrezu
            net.train(inputi[a], c, 0.1);
        }

        //Vrsimo predikciju za poslednji clan niza 1, 1 zeljeni ishod 0
        double[] b = net.calculate(inputi[3]);

        //Veci clan niza je pretpostavka mreze dakle prvi clan ako je veci predikcija je 0 a drugi ako je veci predikcija je 1
        //Da ima vise mogucih izlaznih kombinacija opet najveci izlaz je predikcija
        if(b[0] > b[1]){
            System.out.println(0);
        }else{
            System.out.println(1);
        }

        //Kako to zapravo izgleda
        System.out.println(Arrays.toString(b));
    }

    public static void main(String[] args) throws IOException {
        //Pokrenuti nekoliko puta sa mjenjanjem  double[] b = net.calculate(inputi[0-3]);
        new XorImplementation();

        new ImplementationMnistDB();
    }
}
