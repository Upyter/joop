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

package joop.shape.gui;

import java.awt.Graphics;
import java.util.Optional;
import joop.event.mouse.Mouse;
import joop.event.mouse.PressRelease;
import joop.shape.Pen;
import joop.shape.Shape;
import joop.shape.layout.Adjustment;
import unit.Overlap;
import unit.color.DualColor;
import unit.functional.Action;

/**
 * A shape that has {@link PressRelease} event attached to it.
 * <p>This class is mutable and not thread-safe, because it uses a</p>
 * @since 0.38
 */
public class Button implements Shape {
    /**
     * The shape with the attached functionality to represent the button.
     */
    private final Shape shape;

    /**
     * Ctor.
     * @param pen The pen to create the shape of the button.
     * @param overlap The area of the button.
     * @param color The colors of the button.
     * @param action The action to be applied when the button is released.
     * @param <T> The type of the area used by the pen to create the shape.
     * @checkstyle ParameterNumber (2 lines)
     */
    public <T extends Overlap> Button(
        final Pen<T> pen,
        final T overlap,
        final DualColor color,
        final Action action
    ) {
        this(
            pen.shape(
                overlap,
                color,
                new PressRelease(
                    color::toggle,
                    () -> {
                        action.run();
                        color.toggle();
                    }
                )
            )
        );
    }

    /**
     * Ctor.
     * @param shape The shape with the attached functionality to represent the
     *  button.
     */
    private Button(final Shape shape) {
        this.shape = shape;
    }

    @Override
    public final Optional<Shape> draw(
        final Graphics graphics, final Adjustment adjustment
    ) {
        return this.shape.draw(graphics, adjustment);
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.shape.registerFor(mouse);
    }
}
