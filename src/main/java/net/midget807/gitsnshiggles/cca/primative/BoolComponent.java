package net.midget807.gitsnshiggles.cca.primative;

import org.ladysnake.cca.api.v3.component.Component;

public interface BoolComponent extends Component {
    boolean getValue();
    void setValue(boolean value);
}
