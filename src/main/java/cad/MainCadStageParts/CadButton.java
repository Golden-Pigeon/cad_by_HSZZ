package main.java.cad.MainCadStageParts;

import javafx.scene.control.Button;

public class CadButton extends Button {
    String id;

    public CadButton(String n) {
        super();
        this.id = n;
    }

    public String getName() {
        return id;
    }
}
