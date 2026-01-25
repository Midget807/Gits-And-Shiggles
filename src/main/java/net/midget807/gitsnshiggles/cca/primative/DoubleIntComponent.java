package net.midget807.gitsnshiggles.cca.primative;

import org.ladysnake.cca.api.v3.component.Component;

public interface DoubleIntComponent extends Component {
    int getValue1();
    int getValue2();
    void setValue1(int value);
    void setValue2(int value);
    void addToValue1(int count);
    void addToValue2(int count);
    void incrementValue1();
    void incrementValue2();
    void decrementValue1();
    void decrementValue2();
}
