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

package joop.shape.gui;

import java.awt.Graphics;
import java.util.Optional;
import joop.event.Event;
import joop.event.mouse.Mouse;
import joop.shape.Pen;
import joop.shape.Shape;
import joop.shape.Text;
import joop.shape.layout.Adjustment;
import joop.shape.layout.AreaAdjustment;
import unit.area.Area;

/**
 * A shape with an attached text.
 * @since 0.42
 */
public class Labeled implements Shape {
    /**
     * The shape to be labeled.
     */
    private final Shape shape;

    /**
     * The text for the shape.
     */
    private final Shape text;

    private final Area area;

    /**
     * Ctor.
     * @param text The text to be add.
     * @param area The area of the shape.
     * @param pen The pen to create the shape.
     */
    public Labeled(
        final String text,
        final Area area,
        final Pen<Shape, Area> pen
    ) {
        this(
            pen.shape(area, ignored -> (x, y) -> { }),
            area.result((pos, size) -> new Text(text, pos)),
            area
        );
    }

    /**
     * Ctor.
     * @param text The text to be add.
     * @param area The area of the shape.
     * @param pen The pen to create the shape.
     * @param event The event for the shape created by the pen.
     */
    public Labeled(
        final String text,
        final Area area,
        final Pen<Shape, Area> pen,
        final Event event
    ) {
        this(
            pen.shape(area, ignored -> event),
            area.result((pos, size) -> new Text(text, pos)),
            area
        );
    }

    /**
     * Ctor.
     * @param shape The shape to be labeled.
     * @param text The text to be add on the shape.
     */
    private Labeled(final Shape shape, final Shape text, final Area area) {
        this.shape = shape;
        this.text = text;
        this.area = area;
    }

    @Override
    public final Optional<Shape> draw(
        final Graphics graphics, final Adjustment adjustment
    ) {
        adjustment.adjustedApply(
            this.area,
            (x, y, w, h) -> {
                this.shape.draw(
                    graphics,
                    (AreaAdjustment) (area, drawing) -> drawing.accept(
                        x, y, w, h
                    )
                );
                this.text.draw(
                    graphics,
                    (AreaAdjustment) (area, drawing) -> drawing.accept(
                        x, y, w, h
                    )
                );
            }
        );
        return Optional.of(this);
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.shape.registerFor(mouse);
    }
}
