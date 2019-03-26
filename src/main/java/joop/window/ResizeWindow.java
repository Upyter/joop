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

package joop.window;

import java.util.function.Consumer;
import javax.swing.JFrame;
import joop.shape.Shape;
import unit.area.Area;

/**
 * A window that adjusts its shape to its own size. Whether this window is
 * actually resizeable, depends on the given size.
 * <p>This class is mutable and not thread-safe of the extended class.</p>
 * @see unit.size.SoftSize
 * @see unit.size.FixSize
 * @since 0.55
 */
public class ResizeWindow extends Window {
    /**
     * Ctor.
     * @param title The title of the window.
     * @param shape The shape on the window.
     */
    public ResizeWindow(
        final String title,
        final Shape shape
    ) {
        super(title, shape);
    }

    /**
     * Ctor.
     * @param title The title of the window.
     * @param area The are of the window.
     * @param shape The shape on the window.
     */
    public ResizeWindow(
        final String title,
        final Area area,
        final Shape shape
    ) {
        super(title, area, shape, frame -> {});
    }

    /**
     * Ctor.
     * @param title The title of the window.
     * @param area The are of the window.
     * @param shape The shape on the window.
     * @param feature The feature to apply.
     */
    public ResizeWindow(
        final String title,
        final Area area,
        final Shape shape,
        final Consumer<JFrame> feature
    ) {
        super(title, area, shape, feature);
    }
}
