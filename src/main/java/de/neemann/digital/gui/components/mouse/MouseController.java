/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.gui.components.mouse;

import de.neemann.digital.draw.graphics.Graphic;
import de.neemann.digital.gui.components.CircuitComponent;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

public abstract class MouseController {
    private final CircuitComponent circuitComponent;
    private final Cursor mouseCursor;

    protected MouseController(CircuitComponent circuitComponent, Cursor mouseCursor) {
        this.circuitComponent = circuitComponent;
        this.mouseCursor = mouseCursor;
    }

    protected CircuitComponent getCircuitComponent() {
        return circuitComponent;
    }

    public void activate() {
        if (getCircuitComponent().getActiveMouseController() != null && getCircuitComponent().getActiveMouseController() != this)
            getCircuitComponent().getActiveMouseController().deactivate();
        getCircuitComponent().setActiveMouseController(this);
        getCircuitComponent().setShallowCopy(null);
        getCircuitComponent().getDeleteAction().setEnabled(false);
        getCircuitComponent().getCopyAction().setEnabled(false);
        getCircuitComponent().getCutAction().setEnabled(false);
        getCircuitComponent().getRotateAction().setEnabled(false);
        getCircuitComponent().setCursor(mouseCursor);
        getCircuitComponent().graphicHasChanged();
    }

    public void deactivate() {
    }

    public void clicked(MouseEvent e) {
    }

    public void pressed(MouseEvent e) {
    }

    public void released(MouseEvent e) {
    }

    public void moved(MouseEvent e) {
    }

    public boolean dragged(MouseEvent e) {
        return false;
    }

    public int drawables() {
        return 0;
    }

    public void drawTo(Graphic gr) {
    }

    public void delete() {
    }

    public void rotate() {
    }

    public void escapePressed() {
    }
}
