package gg.clouke;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A vector class, used for performing operations on vectors
 *
 * @author Clouke
 * @since 03.01.2024 20:06
 * © shape-network - All Rights Reserved
 */
public class ContextVector {

  /**
   * Creates a new vector instance
   *
   * @return Returns a new vector instance
   */
  public static ContextVector newVector() {
    return new ContextVector();
  }

  /**
   * Creates a new vector instance
   *
   * @param size The amount of values to fill the vector with
   * @param value The value to fill the vector with
   * @return Returns a new vector instance
   */
  public static ContextVector fill(int size, double value) {
    ContextVector vector = newVector();
    for (int i = 0; i < size; i++)
      vector.addValueToVector(value);
    return vector;
  }

  /**
   * Sums the dimensions of the given vectors
   *
   * @param contexts The vectors to sum the dimensions of
   * @return Returns the sum of the dimensions of the given vectors
   */
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

  /**
   * Gets the value at the given index
   *
   * @param index The index to get the value at
   * @return Returns the value at the given index
   */
  public double get(int index) {
    return vector.get(index);
  }

  /**
   * Folds the value into an optional
   *
   * @param index The index to fold the value at
   * @return Returns an optional containing the value at the given index
   */
  public Optional<Double> fold(int index) {
    return index < dimension() ? Optional.of(get(index)) : Optional.empty();
  }

  /**
   * Adds the given value to the vector
   *
   * @param value The value to add to the vector
   * @return Returns this instance for chaining
   */
  public ContextVector addValueToVector(double value) {
    vector.add(value);
    return this;
  }

  /**
   * Adds the given values to the vector
   *
   * @param values The values to add to the vector
   * @return Returns this instance for chaining
   */
  public ContextVector addValuesToVector(double... values) {
    for (double value : values)
      vector.add(value);
    return this;
  }

  /**
   * Cuts the vector from the given index to the given index
   *
   * @param from The index to start cutting from
   * @param to The index to cut to
   * @return Returns a new vector instance of the cut vector
   */
  public ContextVector cut(int from, int to) {
    ContextVector context = new ContextVector();
    for (int i = from; i < to; i++)
      context.addValueToVector(vector.get(i));
    return context;
  }

  /**
   * Cuts the vector from the given index to the end of the vector
   *
   * @param from The index to start cutting from
   * @return Returns a new vector instance of the cut vector
   */
  public ContextVector cut(int from) {
    return cut(from, dimension());
  }

  /**
   * Modifies the value at the given index
   *
   * @param index The index to modify the value at
   * @param value The value to modify the value at the given index with
   * @return Returns this instance for chaining
   */
  public ContextVector modify(int index, double value) {
    vector.set(index, value);
    return this;
  }

  /**
   * Multiplies the value at the given index with the given value
   *
   * @param index The index to multiply the value at
   * @param value The value to multiply the value at the given index with
   * @return Returns this instance for chaining
   */
  public ContextVector multiply(int index, double value) {
    vector.set(index, vector.get(index) * value);
    return this;
  }

  /**
   * Divides the value at the given index with the given value
   *
   * @param index The index to divide the value at
   * @param value The value to divide the value at the given index with
   * @return Returns this instance for chaining
   */
  public ContextVector divide(int index, double value) {
    vector.set(index, vector.get(index) / value);
    return this;
  }

  /**
   * Adds the given value to the value at the given index
   *
   * @param index The index to add the value at
   * @param value The value to add to the value at the given index
   * @return Returns this instance for chaining
   */
  public ContextVector add(int index, double value) {
    vector.set(index, vector.get(index) + value);
    return this;
  }

  /**
   * Subtracts the given value from the value at the given index
   *
   * @param index The index to subtract the value at
   * @param value The value to subtract from the value at the given index
   * @return Returns this instance for chaining
   */
  public ContextVector subtract(int index, double value) {
    vector.set(index, vector.get(index) - value);
    return this;
  }

  /**
   * Merges the given vector with this vector
   *
   * @param context The vector to merge with this vector
   * @return Returns this instance for chaining
   */
  public ContextVector merge(ContextVector context) {
    if (context.dimension() != dimension())
      throw new IllegalStateException("Dimension of node x and y must be equal");

    for (int i = 0; i < context.dimension(); i++)
      add(i, context.get(i));
    return this;
  }

  /**
   * Agglutinates the given vector with this vector
   *
   * @param context The vector to agglutinate with this vector
   * @return Returns this instance for chaining
   */
  public ContextVector agglutinate(ContextVector context) {
    for (int index = 0; index < context.dimension(); index++) {
      double x = get(index);
      double y = context.get(index);
      double z = (x + y) / 2;
      modify(index, z);
    }
    return this;
  }

  /**
   * Performs cosine similarity on the given vector
   *
   * @param y The vector to perform cosine similarity on
   * @return Returns the cosine similarity of the given vector
   */
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

  /**
   * Gets the size of the vector
   *
   * @return Returns the size of the vector
   */
  public int dimension() {
    return vector.size();
  }

  /**
   * Transforms the vector into an array
   *
   * @return Returns the transformed vector
   */
  public double[] transform() {
    int dim = dimension();
    double[] transformed = new double[dim];
    for (int i = 0; i < vector.size(); i++)
      transformed[i] = vector.get(i);
    return transformed;
  }

  /**
   * Normalizes the given features
   *
   * @param features The features to normalize
   * @return Returns the normalized features
   */
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
