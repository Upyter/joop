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

package joop.window.feature;

import java.awt.Dimension;
import java.util.function.Consumer;
import javax.swing.JFrame;
import joop.shape.Shape;
import unit.area.Adjustment;
import unit.area.adjustment.NoAdjustment;

/**
 * Used to give a window the size of the given shape.
 * <p>This class is mutable and not thread-safe because
 * {@link Shape#adjustment(Adjustment)} is used.</p>
 * @since 0.56
 */
public class ShapeSized implements Consumer<JFrame> {
    /**
     * The shape to use the size from.
     */
    private final Shape shape;

    /**
     * Ctor.
     * @param shape The shape to use the size from.
     */
    public ShapeSized(final Shape shape) {
        this.shape = shape;
    }

    @Override
    public final void accept(final JFrame frame) {
        final var area = this.shape.adjustment(NoAdjustment.cached());
        frame.getContentPane().setPreferredSize(
            new Dimension((int) area.w(), (int) area.h())
        );
        frame.pack();
    }
}
