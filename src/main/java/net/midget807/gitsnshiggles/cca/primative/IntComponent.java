package net.midget807.gitsnshiggles.cca.primative;

import org.ladysnake.cca.api.v3.component.Component;

public interface IntComponent extends Component {
    int getValue();
    void setValue(int value);
    void addToValue(int count);
    void incrementValue();
    void decrementValue();
}
