/*
 * Copyright 2019 Upyter
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package joop.shape;

import java.awt.Graphics;
import joop.event.mouse.Mouse;
import unit.area.Adjustment;
import unit.area.Area;

/**
 * A shape that can draw itself.
 * @since 0.6
 */
public interface Shape {
    /**
     * Draws the shape.
     * @param graphics The Graphics object to draw the shape.
     */
    void draw(Graphics graphics);

    /**
     * Registers itself on the mouse. This is necessary for shapes with events.
     * @param mouse The mouse to register on.
     */
    void registerFor(Mouse mouse);

    /**
     * Adjust the shape (eventually).
     * @param adjustment The adjustment to make.
     * @return The resulting area of the adjustment.
     */
    Area adjustment(Adjustment adjustment);
}
