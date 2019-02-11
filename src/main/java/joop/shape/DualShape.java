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
import java.util.Optional;
import java.util.function.Function;
import joop.event.mouse.Mouse;
import joop.shape.layout.Adjustment;

/**
 * A shape that can switch between two shapes. It's similar to
 * {@link unit.color.DualColorOf}.
 * <p>This class is mutable and not thread-safe due to the shape switching.</p>
 * @since 0.42
 */
public class DualShape implements ToggleableShape {
    /**
     * The first shape.
     */
    private final Shape first;

    /**
     * The second shape.
     */
    private final Shape second;

    /**
     * The currently chosen shape.
     */
    private Shape current;

    /**
     * Ctor.
     * @param first The first shape.
     * @param second The second shape.
     */
    public DualShape(final Shape first, final Shape second) {
        this(
            toggleable -> first,
            toggleable -> second
        );
    }

    /**
     * Ctor.
     * @param first The first shape.
     * @param second The second shape.
     */
    public DualShape(
        final Function<ToggleableShape, Shape> first,
        final Function<ToggleableShape, Shape> second
    ) {
        this.first = first.apply(this);
        this.second = second.apply(this);
        this.current = this.first;
    }

    @Override
    public final Optional<Shape> draw(
        final Graphics graphics, final Adjustment adjustment
    ) {
        return this.current.draw(graphics, adjustment);
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.current.registerFor(mouse);
    }

    @Override
    public final void toggle() {
        if (this.current == this.first) {
            this.current = this.second;
        } else if (this.current == this.second) {
            this.current = this.first;
        } else {
            throw new IllegalStateException(
                String.join(
                    "",
                    "Current shape got a shape that it shouldn't be able to ",
                    " reach. Current: ",
                    this.current.toString(),
                    ", first: ",
                    this.first.toString(),
                    ", second: ",
                    this.second.toString()
                )
            );
        }
    }
}
