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
import java.util.function.BiFunction;
import joop.event.Event;
import joop.event.mouse.Mouse;
import joop.shape.layout.Adjustment;
import unit.area.Area;
import unit.area.AreaOf;
import unit.area.OverlapArea;
import unit.area.OverlapAreaOf;
import unit.color.Black;
import unit.color.Color;
import unit.pos.Pos;
import unit.size.Size;

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
    private Adjusted area;

    /**
     * The color of the rect.
     */
    private final Color color;

    /**
     * The event of the rect.
     */
    private final Event event;

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
        this(area, color, (mouse, overlap) -> { });
    }

    /**
     * Ctor.
     * @param area The area of the rect.
     * @param color The color of the rect.
     * @param event The event of the rect.
     */
    public Rect(final Area area, final Color color, final Event event) {
        this(new OverlapAreaOf(area), color, event);
    }

    /**
     * Ctor.
     * @param area The area of the rect.
     * @param color The color of the rect.
     * @param event The event of the rect.
     */
    public Rect(final OverlapArea area, final Color color, final Event event) {
        this.area = new Adjusted(area);
        this.color = color;
        this.event = event;
    }

    @Override
    public final void draw(final Graphics graphics) {
        graphics.setColor(this.color.result(java.awt.Color::new));
        Area.applyOn(this.area, graphics::fillRect);
    }

    @Override
    public final Area adjust(final Adjustment adjustment) {
        return this.area.adjusted(adjustment);
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.event.registerFor(mouse, this.area);
    }

    private class Adjusted implements OverlapArea {
        private OverlapArea area;

        public Adjusted(OverlapArea area) {
            this.area = area;
        }

        public final OverlapArea adjusted(Adjustment adjustment) {
            this.area = new OverlapAreaOf(adjustment.adjust(this.area));
            return this.area;
        }

        @Override
        public final boolean contains(final int i, final int i1) {
            return this.area.contains(i, i1);
        }

        @Override
        public final <R> R result(final BiFunction<Pos, Size, R> biFunction) {
            return this.area.result(biFunction);
        }
    }
}
