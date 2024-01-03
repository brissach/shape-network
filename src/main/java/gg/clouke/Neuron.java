package gg.clouke;

import java.util.Random;

/**
 * @author Clouke
 * @since 03.01.2024 20:30
 * © shape-network - All Rights Reserved
 */
public class Neuron {

  private final ContextVector sumVector = ContextVector.newVector();
  private ContextVector shapeVector = ContextVector.newVector();
  private int numShapes = 0;

  private final ShapingFunction shapingFunction;

  public Neuron(int inputSize, ShapingFunction shapingFunction) {
    this.shapingFunction = shapingFunction;
    for (int i = 0; i < inputSize; i++) {
      sumVector.addValueToVector(0.0);
      shapeVector.addValueToVector(0.0);
    }
  }

  /**
   * Randomizes the sum vector of this neuron
   *
   * @param random The random instance to use
   */
  public void randomize(Random random) {
    for (int i = 0; i < sumVector.dimension(); i++)
      sumVector.modify(
        i,
        random.nextDouble() * 2.0 - 1.0
      );
  }

  /**
   * Adds noise to the shape vector of this neuron
   *
   * @param random The random instance to use
   * @param noiseRate The noise rate to use
   */
  public void noisyShape(Random random, double noiseRate) {
    for (int i = 0; i < shapeVector.dimension(); i++) {
      double noise = random.nextDouble() * 2.0 - 1.0;
      boolean sign = random.nextBoolean();
      shapeVector.modify(
        i,
        shapeVector.get(i) + noiseRate * noise * (sign ? 1 : -1)
      );
    }
  }

  /**
   * Shapes the weights of this neuron
   *
   * @param y The context vector to shape the weights with
   * @param n The amount this neuron has been shaped
   */
  public void shape(ContextVector y, int n) {
    shapeVector = shapingFunction.apply(
      this,
      n,
      y
    );

    numShapes++;
  }

  /**
   * Gets the shape vector of this neuron
   *
   * @return Returns the shape vector of this neuron
   */
  public ContextVector shapeVector() {
    return shapeVector;
  }

  /**
   * Gets the sum vector of this neuron
   *
   * @return Returns the sum vector of this neuron
   */
  public ContextVector sumVector() {
    return sumVector;
  }

  /**
   * Gets the shaping function of this neuron
   *
   * @return Returns the shaping function of this neuron
   */
  public ShapingFunction shapingFunction() {
    return shapingFunction;
  }

  /**
   * Gets the amount this neuron has been shaped
   *
   * @return Returns the amount this neuron has been shaped
   */
  public int numShapes() {
    return numShapes;
  }

}