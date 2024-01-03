package gg.clouke;

/**
 * @author Clouke
 * @since 03.01.2024 20:30
 * © shape-network - All Rights Reserved
 */
@FunctionalInterface
public interface ShapingFunction {
  /**
   * Performs the shaping function on the given node, n and w
   *
   * @param node The node to perform the shaping function on
   * @param n The amount this neuron has been shaped
   * @param w The context vector to shape the neuron with
   * @return Returns the shaped context vector
   */
  ContextVector apply(Neuron node, int n, ContextVector w);
}
