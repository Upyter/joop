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
import joop.shape.layout.Adjustment;
import unit.area.Area;
import unit.area.AreaOf;
import unit.color.Black;
import unit.color.Color;

/**
 * A filled rectangle.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments.</p>
 * @since 0.7
 */
public class Rect implements Shape {
    /**
     * The area of the rect.
     */
    private final Area area;

    /**
     * The color of the rect.
     */
    private final Color color;

    /**
     * Ctor. Creates a black rect.
     * @param x The x coordinate of the rect.
     * @param y The y coordinate of the rect.
     * @param width The width of the rect.
     * @param height The height of the rect.
     * @checkstyle ParameterName (4 lines)
     * @checkstyle ParameterNumber (3 lines)
     */
    public Rect(
        final int x, final int y, final int width, final int height
    ) {
        this(new AreaOf(x, y, width, height), new Black());
    }

    /**
     * Ctor. Creates a black rect.
     * @param area The area of the rect.
     */
    public Rect(final Area area) {
        this(area, new Black());
    }

    /**
     * Ctor.
     * @param area The area of the rect.
     * @param color The color of the rect.
     */
    public Rect(final Area area, final Color color) {
        this.area = area;
        this.color = color;
    }

    @Override
    public final Optional<Shape> draw(
        final Graphics graphics, final Adjustment adjustment
    ) {
        graphics.setColor(this.color.result(java.awt.Color::new));
        adjustment.adjustedApply(this.area, graphics::fillRect);
        return Optional.of(this);
    }
}
