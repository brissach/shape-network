package gg.clouke;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Clouke
 * @since 03.01.2024 20:46
 * © shape-network - All Rights Reserved
 */
public class LayerBuilder {

  private int attribute = -1;
  private transient int numNodes = 0;
  private final Map<Integer, ShapingFunction> layerFunctions = new HashMap<>();

  public LayerBuilder() {}

  LayerBuilder(int attribute, int numNodes, Map<Integer, ShapingFunction> layerFunctions) {
    this.attribute = attribute;
    this.numNodes = numNodes;
    this.layerFunctions.putAll(layerFunctions);
  }

  /**
   * Sets the attribute of this layer
   *
   * @param attribute The attribute to set
   * @return Returns this layer builder for chaining
   */
  public LayerBuilder attribute(int attribute) {
    this.attribute = attribute;
    return this;
  }

  /**
   * Adds a layer to this layer builder
   *
   * @param shapingFunction The shaping function to add
   * @return Returns this layer builder for chaining
   */
  public LayerBuilder addLayer(ShapingFunction shapingFunction) {
    layerFunctions.put(
      numNodes++,
      shapingFunction
    );
    return this;
  }

  /**
   * Adds a layer to this layer builder
   *
   * @param shape The shape to add
   * @return Returns this layer builder for chaining
   */
  public LayerBuilder addLayer(Shapes shape) {
    layerFunctions.put(
      numNodes++,
      shape.function()
    );
    return this;
  }

  /**
   * Adds a layer to this layer builder
   *
   * @param shape The shape to add
   * @param copies The number of copies to add
   * @return Returns this layer builder for chaining
   */
  public LayerBuilder addLayer(Shapes shape, int copies) {
    for (int i = 0; i < copies; i++) {
      layerFunctions.put(
        numNodes++,
        shape.function()
      );
    }
    return this;
  }

  /**
   * Builds the composed layer
   *
   * @return Returns the composed layer
   */
  public ComposedLayer build() {
    if (attribute == -1) throw new IllegalArgumentException("Attribute must be set");
    if (layerFunctions.isEmpty()) throw new IllegalArgumentException("At least one layer must be added");
    return new ComposedLayer(
      attribute,
      layerFunctions
    );
  }

  public LayerBuilder copy() {
    return new LayerBuilder(
      attribute, numNodes, layerFunctions
    );
  }

}
