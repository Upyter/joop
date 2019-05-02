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
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import joop.event.Event;
import joop.event.mouse.InputHardware;
import joop.shape.Shape;
import joop.shape.Text;
import joop.shape.pen.Pen;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.Centered;
import unit.color.Black;
import unit.color.White;
import unit.pos.SoftPos;

/**
 * A shape with an attached text.
 * <p>This class doesn't mutate its state. Whether it is mutable or not depends
 * on the constructor arguments.</p>
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
        final int text,
        final Area area,
        final Pen<Shape, Area> pen
    ) {
        this(
            () -> text,
            area,
            pen,
            (source, overlap) -> { }
        );
    }

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
            text,
            area,
            pen,
            (source, overlap) -> { }
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
        final int text,
        final Area area,
        final Pen<Shape, Area> pen,
        final Event event
    ) {
        this(
            () -> text,
            area,
            pen,
            event
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
            () -> text,
            area,
            pen,
            event
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
        final IntSupplier text,
        final Area area,
        final Pen<Shape, Area> pen,
        final Event event
    ) {
        this(
            pen.shape(area, new White(), event),
            new Text(text, new SoftPos(area.x(), area.y())),
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
        final Supplier<String> text,
        final Area area,
        final Pen<Shape, Area> pen,
        final Event event
    ) {
        this(
            pen.shape(area, new White(), event),
            new Text(
                text,
                own -> new Centered(
                    own,
                    area
                ),
                new Black()
            ),
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
    public final void draw(final Graphics graphics) {
        final var backup = graphics.getClip();
        graphics.setClip(
            (int) this.area.x(),
            (int) this.area.y(),
            (int) this.area.w(),
            (int) this.area.h()
        );
        this.shape.draw(graphics);
        this.text.draw(graphics);
        graphics.setClip(backup);
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.shape.registerFor(source);
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        return this.shape.adjustment(adjustment);
    }
}
