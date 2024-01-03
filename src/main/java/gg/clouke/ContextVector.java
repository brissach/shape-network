package gg.clouke;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Clouke
 * @since 03.01.2024 20:06
 * © shape-network - All Rights Reserved
 */
public class ContextVector {

  public static ContextVector newVector() {
    return new ContextVector();
  }

  public static ContextVector fill(int size, double value) {
    ContextVector vector = newVector();
    for (int i = 0; i < size; i++)
      vector.addValueToVector(value);
    return vector;
  }

  public static int summationOf(ContextVector... contexts) {
    int dimensions = 0;
    for (ContextVector context : contexts)
      dimensions += context.dimension();
    return dimensions;
  }

  private final List<Double> vector = new ArrayList<>();

  public ContextVector(double... values) {
    for (double value : values) vector.add(value);
  }

  public double get(int index) {
    return vector.get(index);
  }

  public Optional<Double> fold(int index) {
    return index < dimension() ? Optional.of(get(index)) : Optional.empty();
  }

  public ContextVector addValueToVector(double value) {
    vector.add(value);
    return this;
  }

  public ContextVector addValuesToVector(double... values) {
    for (double value : values)
      vector.add(value);
    return this;
  }

  public ContextVector cut(int from, int to) {
    ContextVector context = new ContextVector();
    for (int i = from; i < to; i++)
      context.addValueToVector(vector.get(i));
    return context;
  }

  public ContextVector cut(int from) {
    return cut(from, dimension());
  }

  public ContextVector modify(int index, double value) {
    vector.set(index, value);
    return this;
  }

  public ContextVector multiply(int index, double value) {
    vector.set(index, vector.get(index) * value);
    return this;
  }

  public ContextVector divide(int index, double value) {
    vector.set(index, vector.get(index) / value);
    return this;
  }

  public ContextVector add(int index, double value) {
    vector.set(index, vector.get(index) + value);
    return this;
  }

  public ContextVector subtract(int index, double value) {
    vector.set(index, vector.get(index) - value);
    return this;
  }

  public ContextVector merge(ContextVector context) {
    if (context.dimension() != dimension())
      throw new IllegalStateException("Dimension of node x and y must be equal");

    for (int i = 0; i < context.dimension(); i++)
      add(i, context.get(i));
    return this;
  }

  public ContextVector agglutinate(ContextVector context) {
    for (int index = 0; index < context.dimension(); index++) {
      double x = get(index);
      double y = context.get(index);
      double z = (x + y) / 2;
      modify(index, z);
    }
    return this;
  }

  public double cosine(ContextVector y) {
    double dotProduct = 0.0;
    double normX = 0.0;
    double normY = 0.0;

    for (int i = 0; i < dimension(); i++) {
      dotProduct += get(i) * y.get(i);
      normX += Math.pow(get(i), 2);
      normY += Math.pow(y.get(i), 2);
    }

    double magnitudeX = Math.sqrt(normX);
    double magnitudeY = Math.sqrt(normY);

    if (magnitudeX == 0 || magnitudeY == 0)
      return 0.0; // division by zero

    return dotProduct / (magnitudeX * magnitudeY);
  }

  public int dimension() {
    return vector.size();
  }

  public double[] transform() {
    int dim = dimension();
    double[] transformed = new double[dim];
    for (int i = 0; i < vector.size(); i++)
      transformed[i] = vector.get(i);
    return transformed;
  }

  public List<Double> normalize(List<Double> features) {
    List<Double> normalized = new ArrayList<>();
    for (int i = 0; i < features.size(); i++) {
      if (i >= dimension()) break;
      double feature = features.get(i);
      double x = vector.get(i);
      double normalizedFeature = (feature - x) / x;
      normalized.add(normalizedFeature);
    }
    return normalized;
  }

  @Override
  public String toString() {
    return "ContextVector{" +
      "vector=" + vector +
      '}';
  }

  public String toCSV() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < dimension(); i++) {
      builder.append(get(i));
      if (i < dimension() - 1) builder.append(",");
    }
    return builder.toString();
  }

  public ContextVector copy() {
    double[] values = transform();
    return ContextVector.newVector()
      .addValuesToVector(values);
  }

  @Override
  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = prime * result + vector.hashCode();
    result = prime * result + dimension();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof ContextVector)) return false;
    ContextVector other = (ContextVector) obj;
    return other.dimension() == dimension() && hashCode() == other.hashCode();
  }

}
