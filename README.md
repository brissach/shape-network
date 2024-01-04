# Shape Network
## Build Network
```java
ShapingNetwork model = new ShapingNetworkBuilder()
  .connectInRange(0, 1, // adds the defined layers in this range of targets (0 -> 1)
    new LayerBuilder()
      .addLayer(Shapes.MEAN, 2) // shape function, amount of nodes
      // more functions
    )
    .build();
```

## Initialize Network
Initialize the network with the amount of input nodes, we will use 3 in this case
```java
model.initialize(3);
```

## Randomizing weights
```java
model.randomize();
```

## Training Data
The model will learn to classify the labels of these samples
```java
Map<Integer, List<ContextVector>> trainingData = new HashMap<>();
List<ContextVector> zeros = Arrays.asList(
  ContextVector.newVector().addValuesToVector(0.001, 2.531, 1.523),
  ContextVector.newVector().addValuesToVector(0.009, 2.231, 1.241)
);

List<ContextVector> ones = Arrays.asList(
  ContextVector.newVector().addValuesToVector(1.8, 0.001, 4.9),
  ContextVector.newVector().addValuesToVector(2.5, 0.002, 4.5)
);

trainingData.put(0, zeros); // the model will aim to classify these samples as 0
trainingData.put(1, ones); // target 1
```

## Training
```java
int epochs = 100;
for (int i = 0; i < epochs; i++) {
  for (Map.Entry<Integer, List<ContextVector>> entry : trainingData.entrySet()) {
    for (ContextVector vector : entry.getValue())
      model.feed(vector, entry.getKey()); // input data & target  
  }
}
```

## Predict
Returns an array of probabilities, whereas you can get the index of the highest probability
```java
double[] prediction = model.predict(vector);
```

Example:
```
Accuracy: 0.996959560291407
Class 0: 
Index=0, Probability=0.9998734666758533
Index=0, Probability=0.9998261862004088

Class 1: 
Index=1, Probability=0.9970322786809951
Index=1, Probability=0.996959560291407
```
