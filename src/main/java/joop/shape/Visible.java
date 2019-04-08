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
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import joop.event.mouse.InputHardware;
import org.apache.commons.lang3.mutable.MutableBoolean;
import unit.area.Adjustment;
import unit.area.Area;

/**
 * A decorator for shapes to control their visibility.
 * <p>Whether this class is mutable or not depends on the given constructor
 * arguments.</p>
 * @since 0.70
 */
public class Visible implements Shape {
    /**
     * The shape to make visible (or not).
     */
    private final Shape shape;

    /**
     * The visibility modification.
     */
    private final BooleanSupplier visibility;

    /**
     * Ctor.
     * @param function The function to create a shape based on a MutableBoolean
     *  for the visibility modification.
     */
    public Visible(final Function<MutableBoolean, Shape> function) {
        this(
            function,
            new MutableBoolean(true)
        );
    }

    /**
     * Ctor.
     * @param function The function to create a shape based on a MutableBoolean
     *  for the visibility modification.
     * @param mutable The mutable boolean to manipulate the visibility.
     */
    private Visible(
        final Function<MutableBoolean, Shape> function,
        final MutableBoolean mutable
    ) {
        this(
            function.apply(mutable),
            mutable::booleanValue
        );
    }

    /**
     * Ctor.
     * @param shape The shape to make visible (or not).
     * @param visibility The visibility modification.
     */
    public Visible(final Shape shape, final BooleanSupplier visibility) {
        this.shape = shape;
        this.visibility = visibility;
    }

    @Override
    public final void draw(final Graphics graphics) {
        if (this.visibility.getAsBoolean()) {
            this.shape.draw(graphics);
        }
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.shape.registerFor(source);
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        return this.shape.adjustment(adjustment);
    }
}
