import gg.clouke.ContextVector;
import gg.clouke.LayerBuilder;
import gg.clouke.Shapes;
import gg.clouke.ShapingNetwork;
import gg.clouke.ShapingNetworkBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Clouke
 * @since 03.01.2024 20:51
 * © shape-network - All Rights Reserved
 */
public class Classification {

  public static void main(String[] args) {
    ShapingNetwork ml = new ShapingNetworkBuilder()
      .connectInRange(0, 1,
        new LayerBuilder()
          .addLayer(Shapes.LOG_MEAN, 2)
      )
      .build();

    ml.initialize(3);
    ml.randomize();


    Map<Integer, List<ContextVector>> trainingData = new HashMap<>();
    List<ContextVector> zeros = Arrays.asList(
      ContextVector.newVector().addValuesToVector(0.001, 2.531, 1.523),
      ContextVector.newVector().addValuesToVector(0.009, 2.231, 1.241)
    );

    List<ContextVector> ones = Arrays.asList(
      ContextVector.newVector().addValuesToVector(1.8, 0.001, 4.9),
      ContextVector.newVector().addValuesToVector(2.5, 0.002, 4.5)
    );

    trainingData.put(0, zeros);
    trainingData.put(1, ones);


    for (int i = 0; i < 500; i++) {
      for (Map.Entry<Integer, List<ContextVector>> entry : trainingData.entrySet()) {
        for (ContextVector vector : entry.getValue()) {
          ml.feed(vector, entry.getKey());
        }
      }
    }


    System.out.println("Accuracy: " + ml.accuracy());
    for (Map.Entry<Integer, List<ContextVector>> entry : trainingData.entrySet()) {
      System.out.println("Class " + entry.getKey() + ": ");
      for (ContextVector vector : entry.getValue()) {
        double[] predict = ml.predict(vector);
        int maxIndex = 0;
        double max = -Double.MAX_VALUE;
        for (int i = 0; i < predict.length; i++) {
          if (predict[i] > max) {
            max = predict[i];
            maxIndex = i;
          }
        }

        boolean correct = maxIndex == entry.getKey();

        System.out.println("I=" + maxIndex + ", P=" + max + ", V=" + Arrays.toString(predict) + ", CORRECT=" + correct);
      }
      System.out.println();
    }

  }

}
