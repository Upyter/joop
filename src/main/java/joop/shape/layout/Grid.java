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

import io.vavr.collection.List;
import java.awt.Graphics;
import joop.event.mouse.Mouse;
import joop.shape.Shape;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.AreaOf;

/**
 * A grid of shapes.
 * <p>This class is mutable and thread-safe due to adjustments.</p>
 * @since 0.62
 */
public class Grid implements Shape {
    private final Area area;
    private final List<List<Shape>> shapes;

    public Grid(final Area area, final List<List<Shape>> shapes) {
        this.area = area;
        this.shapes = shapes;
    }

    @Override
    public final void draw(final Graphics graphics) {
        this.shapes.forEach(
            list -> list.forEach(
                shape -> shape.draw(graphics)
            )
        );
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.shapes.forEach(
            list -> list.forEach(
                shape -> shape.registerFor(mouse)
            )
        );
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        return new AreaOf();
    }
}
