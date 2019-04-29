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
import java.util.ArrayList;
import joop.event.mouse.InputHardware;
import joop.shape.Shape;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.AreaAdjustment;
import unit.area.Covered;
import unit.area.NoAdjustment;
import unit.area.Sizeless;
import unit.area.SoftArea;
import unit.functional.Cached;
import unit.size.Size;
import unit.size.SizeAdjustment;
import unit.size.SoftSize;
import unit.size.raw.AvailableSize;
import unit.size.raw.UnavailableSize;

/**
 * A row of shapes.
 * <p>This class is mutable and not thread-safe due to adjustments.</p>
 * @since 0.62
 */
public class Row implements Shape {
    /**
     * The shapes to adjust.
     */
    private final List<Shape> shapes;

    private Area area;

    /**
     * Ctor.
     * @param shapes The shapes to adjust.
     */
    public Row(final Shape... shapes) {
        this(List.of(shapes));
    }

    /**
     * Ctor.
     * @param shapes The shapes to adjust.
     */
    public Row(final List<Shape> shapes) {
        this(
            new Cached<>(
                () -> {
                    final var areas = new ArrayList<Area>(shapes.size());
                    shapes.forEach(
                        shape -> areas.add(
                            shape.adjustment(NoAdjustment.instance())
                        )
                    );
                    final Area covered = new Covered(areas);
                    return new SoftSize(covered.w(), covered.h());
                }
            ).value(),
            shapes
        );
    }

    public Row(final Size size, final Shape... shapes) {
        this(
            new SoftArea(size),
            List.of(shapes)
        );
    }

    public Row(final Size size, final List<Shape> shapes) {
        this(
            new SoftArea(size),
            shapes
        );
    }

    public Row(final Area area, final List<Shape> shapes) {
        this.area = area;
        this.shapes = shapes;
    }

    @Override
    public final void draw(final Graphics graphics) {
        this.shapes.forEach(it -> it.draw(graphics));
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        final var areas = this.shapes.map(
            shape -> shape.adjustment(NoAdjustment.instance())
        );
        final var available = new AvailableSize(areas);
        final var unavailable = new UnavailableSize(areas).w();
        final var sizeAdjustment = new SizeAdjustment(
            width -> {
                if (available.w() == 0.0) {
                    final int empties = areas.count(
                        a -> !a.cleanW().isFix()
                    );
                    return (this.area.w() - unavailable) / empties;
                } else {
                    return width.cleanValue() / available.w() * Math.max(0.0, this.area.w() - unavailable);
                }
            },
            height -> this.area.h()
        );
        this.area.adjustment(adjustment);
        this.shapes.foldLeft(
            (Area) new Sizeless(this.area),
            (previous, shape) -> shape.adjustment(
                new AreaAdjustment(
                    (x, y, w, h) -> x.cleanValue() + previous.x() + Math.max(0.0, previous.w()),
                    adjustment::adjustedY,
                    sizeAdjustment
                )
            )
        );
        return this.area;
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.shapes.forEach(shape -> shape.registerFor(source));
    }
}
