package net.midget807.gitsnshiggles.cca.primative;

import org.ladysnake.cca.api.v3.component.Component;

public interface IntComponent extends Component {
    int getInt();
    void setInt(int value);
    void addToInt(int count);
    void incrementInt();
    void decrementInt();
}
