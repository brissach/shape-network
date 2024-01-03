package gg.clouke;

/**
 * @author Clouke
 * @since 03.01.2024 20:39
 * © shape-network - All Rights Reserved
 */
public enum Shapes {
  MEAN((node, n, w) -> {
    ContextVector sum = node.sumVector();
    sum.merge(w);

    ContextVector shape = sum.copy();
    for (int i = 0; i < shape.dimension(); i++) {
      shape.modify(
        i,
        shape.get(i) / n
      );
    }

    return shape;
  }),
  MIN((node, n, w) -> {
    if (n == 0) // skip computation due to random initialization
      return w.copy();

    ContextVector sum = node.sumVector();
    sum.merge(w);

    ContextVector shape = sum.copy();
    for (int i = 0; i < shape.dimension(); i++) {
      shape.modify(
        i,
        Math.min(shape.get(i), w.get(i))
      );
    }

    return shape;
  }),
  MAX((node, n, w) -> {
    if (n == 0)
      return w.copy();

    ContextVector sum = node.sumVector();
    sum.merge(w);

    ContextVector shape = sum.copy();
    for (int i = 0; i < shape.dimension(); i++) {
      shape.modify(
        i,
        Math.max(shape.get(i), w.get(i))
      );
    }

    return shape;
  }),
  MAX_MEAN((node, n, w) -> {
    ContextVector sum = node.sumVector();
    sum.merge(w);

    ContextVector shape = sum.copy();
    for (int i = 0; i < shape.dimension(); i++) {
      double max = Math.max(shape.get(i), w.get(i));
      shape.modify(
        i,
        (shape.get(i) + max) / 2
      );
    }

    return shape;
  }),

  LOG_MEAN((node, n, w) -> {
    ContextVector sum = node.sumVector();
    sum.merge(w);

    ContextVector shape = sum.copy();
    for (int i = 0; i < shape.dimension(); i++) {
      shape.modify(
        i,
        Math.exp(
          Math.log(shape.get(i)) + Math.log(w.get(i)) / n
        )
      );
    }

    return shape;
  }),
  GEOMETRIC_MEAN((node, n, w) -> {
    ContextVector sum = node.sumVector();
    sum.merge(w);

    ContextVector shape = sum.copy();
    for (int i = 0; i < shape.dimension(); i++) {
      shape.modify(
        i,
        Math.pow(
          shape.get(i),
          w.get(i) / n
        )
      );
    }

    return shape;
  }),
  HARMONIC_MEAN((node, n, w) -> {
    ContextVector sum = node.sumVector();
    sum.merge(w);

    ContextVector shape = sum.copy();
    for (int i = 0; i < shape.dimension(); i++) {
      shape.modify(
        i,
        n / (
          (1 / shape.get(i)) + (1 / w.get(i))
        )
      );
    }

    return shape;
  }),
  QUADRATIC_MEAN((node, n, w) -> {
    ContextVector sum = node.sumVector();
    sum.merge(w);

    ContextVector shape = sum.copy();
    for (int i = 0; i < shape.dimension(); i++) {
      shape.modify(
        i,
        Math.sqrt(
          (shape.get(i) * shape.get(i)) + (w.get(i) * w.get(i))
        )
      );
    }

    return shape;
  });

  private final ShapingFunction function;

  Shapes(ShapingFunction function) {
    this.function = function;
  }

  public ShapingFunction function() {
    return function;
  }

}
