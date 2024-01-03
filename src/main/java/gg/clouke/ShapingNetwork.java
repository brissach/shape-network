package gg.clouke;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author Clouke
 * @since 03.01.2024 20:42
 * © shape-network - All Rights Reserved
 */
public class ShapingNetwork extends AbstractShapingNetwork {

  private final List<ComposedLayer> composers;
  private int epochs;
  private double loss;
  private boolean initialized;

  public ShapingNetwork(List<ComposedLayer> composers) {
    this.composers = composers;
  }

  public void train(double[][] x, double[][] y) {
    ContextVector[] vectors = new ContextVector[x.length];
    for (int i = 0; i < x.length; i++)
      vectors[i] = ContextVector.newVector().addValuesToVector(x[i]);

    for (int i = 0; i < vectors.length; i++)
      feed(vectors[i], (int) y[i][0]);
  }

  public void feed(ContextVector vector, int target) {
    initialize(vector.dimension());

    if (target >= layers.size())
      throw new IllegalArgumentException("Target is out of bounds");

    layers.stream()
      .filter(layer ->
        layer.attribute() == target)
      .forEach(layer ->
        layer.shape(vector)
      );

    double[] probabilities = predict(vector);
    double max = -Double.MAX_VALUE;
    for (double probability : probabilities)
      max = Math.max(
        max,
        probability
      );

    loss = 1.0 - max;
    epochs++;
  }

  public void randomize(Random random)
    throws IllegalStateException, NullPointerException {

    Objects.requireNonNull(random, "Random cannot be null");
    if (!initialized)
      throw new IllegalStateException("Network is not initialized");

    layers.forEach(layer ->
      layer.randomize(random)
    );
  }

  public void randomize() {
    randomize(new Random());
  }

  public double accuracy() {
    return 1.0 - loss;
  }

  public int epochs() {
    return epochs;
  }

  public void initialize(int input) {
    init: {
      if (initialized)
        break init;

      initializeLayers(
        new ArrayList<>(composers),
        input
      );
      composers.clear();
      initialized = true;
    }
  }

}
