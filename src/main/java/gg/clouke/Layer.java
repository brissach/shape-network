package gg.clouke;

import java.util.Random;

/**
 * @author Clouke
 * @since 03.01.2024 20:28
 * © shape-network - All Rights Reserved
 */
public interface Layer {

  /**
   * Randomizes the neurons in this layer.
   *
   * @param r The random instance to use.
   */
  void randomize(Random r);

  /**
   * Shapes the neurons in this layer.
   *
   * @param y The context vector to shape the neurons with.
   */
  void shape(ContextVector y);

  /**
   * Gets the attribute target of this layer.
   *
   * @return Returns the attribute target of this layer.
   */
  int attribute();

  /**
   * Performs a prediction of each neuron within this layer,
   * and returns a mean of the predictions.
   *
   * @param x The input vector to predict.
   * @return Returns the prediction of this layer.
   */
  double predict(ContextVector x);

}
