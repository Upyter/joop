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
import joop.event.mouse.InputHardware;
import org.apache.commons.lang3.mutable.MutableBoolean;
import unit.area.Adjustment;
import unit.area.Area;

/**
 * A shape that can switch between two shapes. It's similar to
 * {@link unit.color.DualColorOf}.
 * <p>This class is mutable and not thread-safe due to the shape switching.</p>
 * @since 0.42
 */
public class DualShape implements Shape {
    /**
     * The first shape.
     */
    private final Shape first;

    /**
     * The second shape.
     */
    private final Shape second;

    /**
     * The toggle for the currently chosen shape. True means the first shape
     * will be shown.
     */
    private MutableBoolean current;

    /**
     * Ctor.
     * @param first The first shape.
     * @param second The second shape.
     * @param current The toggle for the currently chosen shape. True means the
     *  first shape will be shown.
     */
    public DualShape(
        final Shape first,
        final Shape second,
        final MutableBoolean current
    ) {
        this.first = first;
        this.second = second;
        this.current = current;
    }

    @Override
    public final void draw(final Graphics graphics) {
        if (this.current.isFalse()) {
            this.first.draw(graphics);
        } else {
            this.second.draw(graphics);
        }
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        this.first.adjustment(adjustment);
        return this.second.adjustment(adjustment);
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.first.registerFor(source);
    }
}
