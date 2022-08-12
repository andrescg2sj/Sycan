# Sycan

Symbolic Circuit Analyzer

This program allows the user to draw an electronic circuit and analyze it, calculating the tensions on each node. This is not done numerically but symbolically, providing the mathematical expressions as a function of the parameters.

### Demo

Please, see [this video](https://www.youtube.com/watch?v=dhsbcg2nKy8).

### Details

Expressions are calculated in Laplace's domain, which provides similar expressions to Fourier's domain or frequency domain. That means that, when capacitive and inductive elements are present in the circuit, the expression is a function of the *s* variable. Although Laplace's domain covers the whole complex plane, the imaginary axis provides the frequency response of the circuit, through the following equation:

*s* = *2πjf*  = *jω*

Where *f* is the frequency and *j* is the imaginary unit, i.e. *j* = sqrt(-1).
 
Although the general convention in Mathematics is to name *i* the imaginary unit, *j* is preferred in this context to avoid confusion with current intensity.

## About the current version

The program is currently still a prototype. Some of its limitations are:

* Mathematical expressions are sometimes not simplified enough. For example, you may see a product with a variable repeated, instead of a power of that variable.
* Ideal operational amplifiers are analyzed assuming negative feedback. If you analyze a circuit with positive feedback, the program may provide a meaningless result.
* When drawing the circuit, you need to connect elements through their extremes (the small circumferences). For example, you cannot connect an element to some point in the middle of a wire.

## Download and run

You can find the first release to the left of this text, or just [here](https://github.com/andrescg2sj/Sycan/releases/tag/v0.1.0).

To run in Windows, execute:

```
sycan-GUI.bat
```

To run in Linux or MacOS:

```
$ ./sycan-GUI.sh
```

Alternatively:

```
$ java -classpath Sycan-0.1-SNAPSHOT.jar org.sj.tools.sycan.circedit.MainPrg
```


## Compile from source


(requires Maven)

```
$ mvn install
```

