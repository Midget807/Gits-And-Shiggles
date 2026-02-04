package net.midget807.gitsnshiggles.cca.primative;

import org.ladysnake.cca.api.v3.component.Component;

public interface DoubleBoolComponent extends Component {
    boolean getDoubleBool1();
    boolean getDoubleBool2();
    void setDoubleBool1(boolean value);
    void setDoubleBool2(boolean value);
}
