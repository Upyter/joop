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
import joop.event.mouse.Mouse;
import joop.shape.layout.Adjustment;
import unit.area.Area;
import unit.color.Black;
import unit.color.Color;
import unit.pos.Pos;
import unit.pos.PosOf;
import unit.tuple.Tuple;

/**
 * A line.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments.</p>
 * @since 0.12
 */
public class Line implements Shape {
    /**
     * The first point of the line.
     */
    private final Pos first;

    /**
     * The second point of the line.
     */
    private final Pos second;

    /**
     * The color of the line.
     */
    private final Color color;

    /**
     * Ctor. Creates a black line.
     * @param fx First x coordinate.
     * @param fy First y coordinate.
     * @param sx Second x coordinate.
     * @param sy Second y coordinate.
     * @checkstyle ParameterNumber (3 lines)
     * @checkstyle ParameterName (2 lines)
     */
    public Line(final int fx, final int fy, final int sx, final int sy) {
        this(new PosOf(fx, fy), new PosOf(sx, sy));
    }

    /**
     * Ctor. Creates a black line.
     * @param first The first point of the line.
     * @param second The second point of the line.
     */
    public Line(final Pos first, final Pos second) {
        this(first, second, new Black());
    }

    /**
     * Ctor.
     * @param first The first point of the line.
     * @param second The second point of the line.
     * @param color The color of the rect.
     */
    public Line(final Pos first, final Pos second, final Color color) {
        this.first = first;
        this.second = second;
        this.color = color;
    }

    @Override
    public final void draw(final Graphics graphics) {
        graphics.setColor(this.color.result(java.awt.Color::new));
        Tuple.applyOn(
            this.first,
            // @checkstyle ParameterName (1 line)
            (fx, fy) -> Tuple.applyOn(
                this.second,
                // @checkstyle ParameterName (1 line)
                (sx, sy) -> graphics.drawLine(fx, fy, sx, sy)
            )
        );
    }

    @Override
    public final Area adjust(final Adjustment adjustment) {
        throw new UnsupportedOperationException("To be implemented");
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        // currently no implementation
    }
}
