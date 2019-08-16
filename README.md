# javaluator
A java infix evaluator based on "Shunting Yard" algorithm.

Evaluate an arithmetic expression is as simple as:
```
Double result = new DoubleEvaluator().evaluate("(e^3-1)*sin(pi/4)*ln(pi^2)").
```
But Javaluator provides you with much more ...

Key features:
- Functions support, including variable argument count.
- Constants support: e, pi, etc ...
- Variables support: Example sin(x) for x = pi/4.
- Highly configurable: Reduce the built-in operator or function sets, define your own brackets.
- Extensible: Define your own grammar, or extends the existing one.
- Localizable: Define localized names for functions or constants.
- Syntax check
- Small footprint: 26kB, no additionnal library required.
- Fully documented

Please visit the project site to learn more : http://javaluator.fathzer.com

If you use Javaluator in your product, we will be happy to add a link to your product in the Javaluator web site
