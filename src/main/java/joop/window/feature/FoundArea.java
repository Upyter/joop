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

import java.util.function.Consumer;
import javax.swing.JFrame;
import joop.shape.Shape;

/**
 * Useful for windows that can be created without specifying the area of it.
 * Centres the window and gives it the size of the shape.
 * <p>This class is mutable and not thread-safe because of {@link ShapeSized}
 * </p>
 * @see Centered
 * @see ShapeSized
 * @since 0.56
 */
public class FoundArea implements Consumer<JFrame> {
    /**
     * The feature to center the window. It has to be used after the sizing
     * because it depends on the size of the window.
     */
    private final Centered pos;

    /**
     * The feature to give the window the size of the shape.
     */
    private final ShapeSized size;

    /**
     * Ctor.
     * @param shape The shape to use the size from.
     */
    public FoundArea(final Shape shape) {
        this.pos = new Centered();
        this.size = new ShapeSized(shape);
    }

    @Override
    public final void accept(final JFrame frame) {
        this.size.accept(frame);
        this.pos.accept(frame);
    }
}
