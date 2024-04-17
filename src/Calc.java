import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calc {
    public static double output(double[] input, double[] weight, double threshold){
        double result = 0;
        if(input.length != weight.length){
            return -1;
        }
        for (int i = 0; i < input.length; i++){
            result += input[i] * weight[i];
        }
        if(result < threshold){
            return 0;
        }
        return 1;
    }
public static double[] delta(double[] weight, int desired, int actual, double[] input, double rate) {
    double[] delta = new double[weight.length];

    for (int i = 0; i < input.length; i++) {
        delta[i] = (desired - actual) * rate * input[i];
    }

    for (int i = 0; i < weight.length; i++) {
        weight[i] += delta[i];
    }

    return weight;
}
    public static double getRandomNumber(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }

    public static double[] getWeight(String path) throws IOException {
        BufferedReader brWeight = new BufferedReader(new FileReader(path));
        String line;
        line = brWeight.readLine();
        String[] weightSplit = line.split(",");
        double[] weight = new double[weightSplit.length - 1];

        return weight;
    }
}
