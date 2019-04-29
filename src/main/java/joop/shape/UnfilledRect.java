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
import joop.event.Event;
import joop.event.mouse.InputHardware;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.OverlapArea;
import unit.area.OverlapAreaOf;
import unit.area.SoftArea;
import unit.color.Black;
import unit.color.Color;
import unit.size.Size;

/**
 * An unfilled rectangle.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments.</p>
 * @since 0.74
 */
public class UnfilledRect implements Shape {
    /**
     * The area of the rect.
     */
    private final OverlapArea area;

    /**
     * The color of the rect.
     */
    private final java.awt.Color color;

    /**
     * The event of the rect.
     */
    private final Event event;

    /**
     * Ctor. Creates a black rect with x = 0, y = 0, width = 0 and height = 0.
     * This is useful in layouts because they may handle the position
     * and size.
     */
    public UnfilledRect() {
        this(0, 0, 0, 0);
    }

    /**
     * Ctor. Creates a rect with x = 0, y = 0, width = 0 and height = 0.
     * This is useful in layouts because they may handle the position
     * and size.
     * @param color The color of the rect.
     */
    public UnfilledRect(final Color color) {
        this(new SoftArea(), color);
    }

    /**
     * Ctor. Creates a black rect.
     * @param x The x coordinate of the rect.
     * @param y The y coordinate of the rect.
     * @param width The width of the rect.
     * @param height The height of the rect.
     * @checkstyle ParameterName (4 lines)
     * @checkstyle ParameterNumber (3 lines)
     */
    public UnfilledRect(
        final int x, final int y, final int width, final int height
    ) {
        this(new SoftArea(x, y, width, height), new Black());
    }

    /**
     * Ctor. The position will be 0|0 (soft).
     * @param size The size of the rect.
     */
    public UnfilledRect(final Size size, final Color color) {
        this(new SoftArea(size), color);
    }

    /**
     * Ctor. Creates a black rect.
     * @param area The area of the rect.
     */
    public UnfilledRect(final Area area) {
        this(area, new Black());
    }

    /**
     * Ctor.
     * @param area The area of the rect.
     * @param color The color of the rect.
     */
    public UnfilledRect(final Area area, final Color color) {
        this(area, color, (mouse, overlap) -> { });
    }

    /**
     * Ctor.
     * @param area The area of the rect.
     * @param color The color of the rect.
     * @param event The event of the rect.
     */
    public UnfilledRect(final Area area, final Color color, final Event event) {
        this(new OverlapAreaOf(area), color, event);
    }

    /**
     * Ctor.
     * @param area The area of the rect.
     * @param color The color of the rect.
     * @param event The event of the rect.
     */
    public UnfilledRect(final OverlapArea area, final Color color, final Event event) {
        this.area = area;
        this.color = color.result(java.awt.Color::new);
        this.event = event;
    }

    @Override
    public final void draw(final Graphics graphics) {
        graphics.setColor(this.color);
        graphics.drawRect(
            (int) this.area.x(),
            (int) this.area.y(),
            (int) this.area.w() - 1,
            (int) this.area.h() - 1
        );
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        this.area.adjustment(adjustment);
        return this.area;
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.event.registerFor(source, this.area);
    }
}
