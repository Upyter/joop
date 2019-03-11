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
import joop.event.mouse.Mouse;
import joop.event.mouse.PressRelease;
import joop.shape.DualShape;
import joop.shape.Pen;
import joop.shape.ResourceImage;
import joop.shape.Shape;
import joop.shape.ToggleableShape;
import unit.Overlap;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.AreaOf;
import unit.area.OverlapAreaOf;
import unit.functional.Action;
import unit.functional.Toggleable;
import unit.size.AdjustableSize;

/**
 * A shape that has {@link PressRelease} event attached to it.
 * <p>This class is mutable and not thread-safe, because it uses a
 * {@link Toggleable} to change between the pressed and released state.</p>
 * @since 0.38
 */
public class Button implements Shape {
    /**
     * The shape with the attached functionality to represent the button.
     */
    private final Shape shape;

    /**
     * Ctor. Uses two images for this button. The position will be (0|0).
     * @param size The size of the button.
     * @param action The action to be applied when the button is released.
     */
    public Button(final AdjustableSize size, final Action action) {
        this(
            new AreaOf(size),
            action
        );
    }

    /**
     * Ctor. Uses two images for this button.
     * @param area The area of the button.
     * @param action The action to be applied when the button is released.
     */
    public Button(final Area area, final Action action) {
        this(
            // @checkstyle ParameterName (1 line)
            (area1, event) -> new DualShape(
                toggleable -> new ResourceImage(
                    "gui/button/releasedButtonImage.png",
                    area1,
                    event.apply(toggleable)
                ),
                toggleable -> new ResourceImage(
                    "gui/button/pressedButtonImage.png",
                    area1,
                    event.apply(toggleable)
                )
            ),
            new OverlapAreaOf(area),
            action
        );
    }

    /**
     * Ctor.
     * @param pen The pen to create the shape (that must be toggleable) of the
     *  button.
     * @param overlap The area of the button.
     * @param action The action to be applied when the button is released.
     * @param <S> The type of the shape.
     * @param <A> The type of the area used by the pen to create the shape.
     */
    private <S extends ToggleableShape, A extends Overlap> Button(
        final Pen<S, A> pen, final A overlap, final Action action
    ) {
        this(
            pen.shape(
                overlap,
                toggle -> new PressRelease(
                    toggle::toggle,
                    () -> {
                        action.run();
                        toggle.toggle();
                    }
                )
            )
        );
    }

    /**
     * Ctor.
     * @param pen The pen to create the shape of the button.
     * @param overlap The area of the button.
     * @param toggleable The toggleing which will happen when the button is
     *  pressed or released.
     * @param action The action to be applied when the button is released.
     * @param <T> The type of the area used by the pen to create the shape.
     * @checkstyle ParameterNumber (2 lines)
     */
    public <T extends Overlap> Button(
        final Pen<Shape, T> pen,
        final T overlap,
        final Toggleable toggleable,
        final Action action
    ) {
        this(
            pen.shape(
                overlap,
                ignored -> new PressRelease(
                    toggleable::toggle,
                    () -> {
                        action.run();
                        toggleable.toggle();
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
    public final void draw(final Graphics graphics) {
        this.shape.draw(graphics);
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.shape.registerFor(mouse);
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        return this.shape.adjustment(adjustment);
    }
}
