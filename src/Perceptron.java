import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Perceptron {


    public static void main(String[] args) throws IOException {
        String train = args[0];
        String test = args[1];
        double rate = Double.parseDouble(args[2]);
        double epoch = Double.parseDouble(args[3]);
        double threshold = 1;
        double count = 0;


        double[] weight = Calc.getWeight(train);

        for(int i = 0; i < weight.length; i++){
            weight[i] = Calc.getRandomNumber(0.1, 0.5);
        }


        BufferedReader brTrain = new BufferedReader(new FileReader(train));
        Map<String, Integer> map = new HashMap<>();
        int value = 1;

        String line;


        while (epoch > count){
            int countLines = 0;
            int countPerc = 0;
            while ((line = brTrain.readLine()) != null) {
                countLines++;
                String[] splitLine = line.split(",");
                double[] values = new double[splitLine.length - 1];
                String label = splitLine[splitLine.length - 1];

                for (int i = 0; i < values.length; i++) {
                    values[i] = Double.parseDouble(splitLine[i]);
                }

                if(label.equals("0")){
                    map.put(label, 0);
                }else if (label.equals("1")){
                    map.put(label, 1);
                }

                if (!map.containsKey(label)) {
                    map.put(label, value);
                    value = 0;
                }

                int acutall = (int) Calc.output(values, weight, threshold);

//                System.out.println("Output: " + acutall + " File: " + label + " Mapped " + map.get(label));

                if(acutall == map.get(label)){
                    countPerc++;
                }

                double[] newWeight = Calc.delta(weight, map.get(label), acutall, values, rate);

                for (int i = 0; i < newWeight.length; i++) {
                    weight[i] = newWeight[i];
                }

            }
            System.out.println("Accuracy: " + countPerc*100/countLines + "%");
            System.out.println("================================");
            count++;
            brTrain = new BufferedReader(new FileReader(args[0]));
        }
        System.out.println("Weight vector is trained");
        String option;

        Scanner scanner = new Scanner(System.in);
        System.out.println("test/custom");
        option = scanner.next();

        if(option.equals("test")){
            BufferedReader brTest =  new BufferedReader(new FileReader(test));

            while((line = brTest.readLine()) != null){
                String[] splitLine = line.split(",");
                double[] values = new double[splitLine.length - 1];
                String label = splitLine[splitLine.length - 1];

                for (int i = 0; i < values.length; i++) {
                    values[i] = Double.parseDouble(splitLine[i]);
                }

                int acutall = (int) Calc.output(values, weight, threshold);

                System.out.println(label + " output: " + acutall);
            }
        }
        else if(option.equals("custom")){
            System.out.println("Provide vector for example 'double,double,double,double'");
            option = scanner.next();

                String[] split = option.split(",");
                double[] values = new double[split.length];

                for(int i = 0; i < values.length; i++) {
                    values[i] = Double.parseDouble(split[i]);
                }

                int acutall = (int) Calc.output(values, weight, threshold);
                String outputLabel = "";

                for(Map.Entry<String, Integer> entry : map.entrySet()){
                    if(entry.getValue().equals(acutall)){
                        outputLabel = entry.getKey();
                    }
                }

                System.out.println("Output: " + acutall + ", label: " + outputLabel);
        }else{
            System.out.println("You have provided wrong option.");
        }

    }
}
