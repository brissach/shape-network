package gg.clouke;

import java.util.Map;

/**
 * A class for composing layers and their functions
 *
 * @author Clouke
 * @since 03.01.2024 20:32
 * © shape-network - All Rights Reserved
 */
public class ComposedLayer {

  private final int attribute;
  private final Map<Integer, ShapingFunction> layerFunctions;

  public ComposedLayer(int attribute, Map<Integer, ShapingFunction> layerFunctions) {
    this.attribute = attribute;
    this.layerFunctions = layerFunctions;
  }

  /**
   * Gets the attribute of this layer
   *
   * @return Returns the attribute of this layer
   */
  public int attribute() {
    return attribute;
  }

  /**
   * Gets the layer functions of this layer
   *
   * @return Returns the layer functions of this layer
   */
  public Map<Integer, ShapingFunction> layerFunctions() {
    return layerFunctions;
  }

}