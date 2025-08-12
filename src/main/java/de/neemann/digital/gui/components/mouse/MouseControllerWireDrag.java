/*
 * Copyright (c) 2016 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.gui.components.mouse;

import de.neemann.digital.draw.elements.Pin;
import de.neemann.digital.draw.elements.VisualElement;
import de.neemann.digital.draw.elements.Wire;
import de.neemann.digital.draw.graphics.Style;
import de.neemann.digital.draw.graphics.Vector;
import de.neemann.digital.gui.components.CircuitComponent;
import de.neemann.digital.gui.components.modification.ModifyInsertWire;
import de.neemann.gui.Mouse;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

public class MouseControllerWireDrag extends MouseController {
    private Pin startPin;
    private Vector currentPos;

    public MouseControllerWireDrag(CircuitComponent circuitComponent, Cursor cursor) {
        super(circuitComponent, cursor);
    }

    public void activate(Pin startPin, Vector startPos) {
        super.activate();
        this.startPin = startPin;
        this.currentPos = startPos;
    }

    @Override
    public void moved(MouseEvent e) {
        currentPos = getCircuitComponent().getPosVector(e);
        getCircuitComponent().removeHighLighted();
        VisualElement ve = getCircuitComponent().getActualVisualElement();
        if (ve != null) {
            Pin endPin = ve.getPinAt(CircuitComponent.raster(currentPos));
            if (endPin != null && endPin.getDirection() != startPin.getDirection()) {
                getCircuitComponent().addHighLighted(ve);
            }
        }
        getCircuitComponent().repaint();
    }

    @Override
    public void released(MouseEvent e) {
        if (Mouse.getMouse().isPrimaryClick(e)) {
            VisualElement ve = getCircuitComponent().getActualVisualElement();
            if (ve != null) {
                Pin endPin = ve.getPinAt(CircuitComponent.raster(currentPos));
                if (endPin != null && endPin.getDirection() != startPin.getDirection()) {
                    Wire newWire = new Wire(startPin.getPos(), endPin.getPos());
                    getCircuitComponent().modify(new ModifyInsertWire(newWire));
                }
            }
        }
        getCircuitComponent().getMouseNormal().activate();
    }

    @Override
    public void drawTo(de.neemann.digital.draw.graphics.Graphic gr) {
        if (startPin != null) {
            gr.drawLine(startPin.getPos(), currentPos, Style.HIGHLIGHT);
        }
    }

    @Override
    public void escapePressed() {
        getCircuitComponent().getMouseNormal().activate();
    }
}
