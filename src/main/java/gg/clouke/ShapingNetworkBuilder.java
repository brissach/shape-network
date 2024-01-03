package gg.clouke;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for {@link ShapingNetwork}
 *
 * @author Clouke
 * @since 03.01.2024 20:46
 * © shape-network - All Rights Reserved
 */
public class ShapingNetworkBuilder {

  private final List<ComposedLayer> layers = new ArrayList<>();

  /**
   * Connects the given layer to this network
   *
   * @param layerBuilder The layer to connect
   * @return Returns this network builder for chaining
   */
  public ShapingNetworkBuilder connect(LayerBuilder layerBuilder) {
    layers.add(layerBuilder.build());
    return this;
  }

  /**
   * Connects the given layer to this network
   *
   * @param start The start of the range to connect
   * @param end The end of the range to connect
   * @param layerBuilder The layer to connect
   * @return Returns this network builder for chaining
   */
  public ShapingNetworkBuilder connectInRange(int start, int end, LayerBuilder layerBuilder) {
    for (int i = start; i <= end; i++) {
      layers.add(
        layerBuilder.copy()
          .attribute(i)
          .build()
      );
    }
    return this;
  }

  /**
   * Builds the network
   *
   * @return Returns the built network
   */
  public ShapingNetwork build() {
    return new ShapingNetwork(layers);
  }

}