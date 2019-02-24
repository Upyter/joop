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

package joop.shape.layout;

import java.awt.Graphics;
import java.util.Collection;
import java.util.List;
import joop.event.mouse.Mouse;
import joop.shape.Shape;

/**
 * A layout that adjust its shapes to be in a column
 * (like {@link javafx.scene.layout.VBox}).
 * @since 0.29
 */
public class Column implements Shape {
    /**
     * The shapes to adjust.
     */
    private final Collection<Shape> shapes;

    /**
     * Ctor.
     * @param shapes The shapes to adjust.
     */
    public Column(final Shape... shapes) {
        this(List.of(shapes));
    }

    /**
     * Ctor.
     * @param shapes The shapes to adjust.
     */
    public Column(final Collection<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public final void draw(final Graphics graphics) {

    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.shapes.forEach(shape -> shape.registerFor(mouse));
    }
}
