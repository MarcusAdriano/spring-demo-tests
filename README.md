# Spring with JUnit

[![CircleCI](https://circleci.com/gh/MarcusAdriano/spring-demo-tests.svg?style=shield)](https://circleci.com/gh/MarcusAdriano/spring-demo-tests)
[![codecov](https://codecov.io/gh/MarcusAdriano/spring-demo-tests/branch/master/graph/badge.svg)](https://codecov.io/gh/MarcusAdriano/spring-demo-tests)

# Considerações
### JUnit

Framework para execução de testes unitários dentro da plataforma Java.

Exemplo de uma classe em Java para resolver expressões matemáticas:
```java
public class Calculator {
  public int evaluate(String expression) {
    int sum = 0;
    for (String summand: expression.split("\\+"))
      sum += Integer.valueOf(summand);
    return sum;
  }
}
```

Teste unitário corresponde:
```java
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CalculatorTest {
  @Test
  public void evaluatesExpression() {
    Calculator calculator = new Calculator();
    int sum = calculator.evaluate("1+2+3");
    assertEquals(6, sum);
  }
}
```

Informações adicionais na [documentação](https://github.com/junit-team/junit4/wiki/Getting-started)
do JUnit.

### TDD (Test driven development)

TDD é o *Desenvolvimento orientado por testes*. Primeiro desenvolvem-se os testes
inicialmente todos irão falhar (red) e após os testes escritos, desenvolvem-se as funcionalidades, até que os testes sejam positivos (green).

Ciclo de desenvolvimento TDD: 

![ciclo tdd](https://arquivo.devmedia.com.br/marketing/img/18533/TDD.png)

Mais detalhes em [DevMedia](https://www.devmedia.com.br/test-driven-development-tdd-simples-e-pratico/18533).

### JaCoCo e Codecov

Ferramentas que possibilitam ao desenvolvedor vizualizar a cobertura dos testes, isto é, um conjunto de respostas para as
seguintes perguntas: 
* Quanto do meu código foi testado? 
* Já testei tudo?
* O que falta?

Ambos juntos fornecem: [![codecov](https://codecov.io/gh/MarcusAdriano/spring-demo-tests/branch/master/graph/badge.svg)](https://codecov.io/gh/MarcusAdriano/spring-demo-tests)
