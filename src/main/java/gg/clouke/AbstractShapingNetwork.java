package gg.clouke;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for the shaping network
 *
 * @author Clouke
 * @since 03.01.2024 20:42
 * © shape-network - All Rights Reserved
 */
public class AbstractShapingNetwork {

  protected final List<Layer> layers = new ArrayList<>();

  public AbstractShapingNetwork(List<Layer> layers) {
    this.layers.addAll(layers);
  }

  public AbstractShapingNetwork() {}

  protected void initializeLayers(List<ComposedLayer> temp, int input) {
    for (ComposedLayer composer : temp)
      layers.add(new PartitionLayer(composer, input));
  }

  /**
   * Performs a prediction on the given input vector and returns the probabilities for each layer
   *
   * @param vector The input vector to predict the probabilities of
   * @return Returns an array of probabilities for each layer
   */
  public double[] predict(ContextVector vector) {
    double[] probabilities = new double[layers.size()];
    int space = layers.size();
    for (int i = 0; i < space; i++) {
      Layer layer = layers.get(i);
      double score = layer.predict(vector);
      probabilities[i] = score;
    }

    return probabilities;
  }

}
