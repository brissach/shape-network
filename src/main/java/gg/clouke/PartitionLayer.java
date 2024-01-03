package gg.clouke;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A Partition Layer performs partitioning on different nodes with the same shaping function,
 * by choosing a random node until all nodes have been shaped,
 * then choosing the best node to shape with after partitioning.
 *
 * @author Clouke
 * @since 03.01.2024 20:33
 * © shape-network - All Rights Reserved
 */
public class PartitionLayer implements Layer {

  private static final Random R = new Random();

  private final List<Neuron> nodes = new ArrayList<>();
  private final int attribute;
  private int n = 0;

  public PartitionLayer(ComposedLayer composer, int input) {
    this.attribute = composer.attribute();
    composer.layerFunctions()
      .values()
      .forEach(
        fun -> nodes.add(
          new Neuron(
            input,
            fun
          ))
      );
  }

  @Override
  public void randomize(Random r) {
    for (Neuron n : nodes)
      n.randomize(r);
  }

  @Override
  public void shape(ContextVector y) {
    List<Neuron> norm = new ArrayList<>();
    Map<ShapingFunction, List<Neuron>> partition = new HashMap<>();
    for (Neuron n : nodes) {
      boolean contains = norm.stream()
        .anyMatch(e -> e.shapingFunction()
          .equals(n.shapingFunction())
        );

      if (contains) {
        partition.computeIfAbsent(
          n.shapingFunction(), k ->
            new ArrayList<>())
          .add(n);
        continue;
      }

      norm.add(n);
    }

    if (!partition.isEmpty()) {
      partition.keySet()
        .forEach(func -> {
          List<Neuron> neurons = partition.get(func);
          boolean allShaped = neurons
            .stream()
            .allMatch(n -> n.numShapes() > 0);

          if (allShaped)
            neurons.stream()
              .max(Comparator.comparingDouble(x -> x.shapeVector().cosine(y)))
              .ifPresent(w -> w.shape(y, n));
          else {
            Neuron random = neurons.get(R.nextInt(neurons.size()));
            random.shape(y, n);
          }
        });
    }

    for (Neuron w : norm)
      w.shape(y, n);
    n++;
  }

  @Override
  public int attribute() {
    return attribute;
  }

  @Override
  public double predict(ContextVector x) {
    double weightedMean = 0.0;
    for (Neuron child : nodes)
      weightedMean += x.cosine(child.shapeVector());
    return weightedMean / nodes.size();
  }
}
