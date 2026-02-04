package net.midget807.gitsnshiggles.cca.primative;

import org.ladysnake.cca.api.v3.component.Component;

public interface HextupleIntComponent extends Component {
    int getHexValue1();
    int getHexValue2();
    int getHexValue3();
    int getHexValue4();
    int getHexValue5();
    int getHexValue6();
    void setHexValue1(int value);
    void setHexValue2(int value);
    void setHexValue3(int value);
    void setHexValue4(int value);
    void setHexValue5(int value);
    void setHexValue6(int value);
    void addToHexValue1(int count);
    void addToHexValue2(int count);
    void addToHexValue3(int count);
    void addToHexValue4(int count);
    void addToHexValue5(int count);
    void addToHexValue6(int count);
    void incrementHexValue1();
    void incrementHexValue2();
    void incrementHexValue3();
    void incrementHexValue4();
    void incrementHexValue5();
    void incrementHexValue6();
    void decrementHexValue1();
    void decrementHexValue2();
    void decrementHexValue3();
    void decrementHexValue4();
    void decrementHexValue5();
    void decrementHexValue6();
}
