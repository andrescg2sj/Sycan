# Sycan

Symbolic Circuit Analyzer

This program allows the user to draw an electronic circuit and analyze it, calculating the tensions on each node. This is not done numerically but symbolically, providing the mathematical expressions as a function of the parameters.

Expressions are calculated in Laplace's domain, which provides similar expressions to Fourier's domain or frequency domain. That means that, when capacitive and inductive elements are present in the circuit, the expression is a function of the *s* variable. Although Laplace's domain covers the whole complex plane, the imaginary axis provides the frequency response of the circuit, through the following equation:

*s* = *2πjf*  = *jω*

Where *f* is the frequency and *j* is the imaginary unit, i.e. *j* = sqrt(-1).
 
Although the general convention in Mathematics is to name *i* the imaginary unit, *j* is preferred in this context to avoid confusion with current intensity.

## Compile

(requires Maven)

```
$ mvn compile
```

## Run GUI

```
$ ./circedit.sh
```

Or:

```
$ java -classpath target/classes org.sj.tools.sycan.circedit.MainPrg
```
