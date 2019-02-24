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
import unit.area.Area;
import unit.area.AreaOf;
import unit.color.Black;
import unit.color.Color;

/**
 * A filled oval.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments.</p>
 * @since 0.9
 */
public class Oval implements Shape {
    /**
     * The area of the oval.
     */
    private final Area area;

    /**
     * The color of the oval.
     */
    private final Color color;

    /**
     * Ctor.
     * @param x The x coordinate of the oval.
     * @param y The y coordinate of the oval.
     * @param width The width of the oval.
     * @param height The height of the oval.
     * @checkstyle ParameterNumber (3 lines)
     * @checkstyle ParameterName (2 lines)
     */
    public Oval(final int x, final int y, final int width, final int height) {
        this(new AreaOf(x, y, width, height));
    }

    /**
     * Ctor.
     * @param area The area of the oval.
     */
    public Oval(final Area area) {
        this(area, new Black());
    }

    /**
     * Ctor.
     * @param area The area of the oval.
     * @param color The color of the rect.
     */
    public Oval(final Area area, final Color color) {
        this.area = area;
        this.color = color;
    }

    @Override
    public final void draw(final Graphics graphics) {
        graphics.setColor(this.color.result(java.awt.Color::new));
        Area.applyOn(this.area, graphics::fillOval);
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        // currently no implementation
    }
}
