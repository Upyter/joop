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
import java.util.function.Supplier;
import joop.event.mouse.InputHardware;
import joop.shape.Shape;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.AreaOf;
import unit.area.Covered;
import unit.area.Sizeless;
import unit.area.adjustment.NoAdjustment;
import unit.area.adjustment.Short;
import unit.area.supplier.CleanHeight;
import unit.area.supplier.Height;
import unit.area.supplier.UnavailableHeight;
import unit.area.supplier.Width;
import unit.functional.Cached;
import unit.pos.SoftPos;
import unit.size.AdjustableSize;
import unit.size.SoftSize;

/**
 * A layout that adjust its shapes to be in a column
 * (like {@link javafx.scene.layout.VBox}).
 * @since 0.29
 */
public class Column implements Shape {
    /**
     * The shapes to adjust.
     */
    private final List<Shape> shapes;

    private Area area;

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
    public Column(final List<Shape> shapes) {
        this(
            new Cached<>(
                () -> {
                    final var areas = new ArrayList<Area>(shapes.size());
                    shapes.forEach(shape -> areas.add(shape.adjustment(new NoAdjustment())));
                    return Area.result(
                        new Covered(areas),
                        (x, y, w, h) -> new SoftSize(w, h)
                    );
                }
            ).value(),
            shapes
        );
    }

    public Column(final AdjustableSize size, final Shape... shapes) {
        this(
            size,
            List.of(shapes)
        );
    }

    public Column(final AdjustableSize size, final List<Shape> shapes) {
        this(
            new AreaOf(new SoftPos(), size),
            shapes
        );
    }

    public Column(
        final Area area,
        final List<Shape> shapes
    ) {
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
            shape -> shape.adjustment(new NoAdjustment())
        );
        final Supplier<Integer> heights = new CleanHeight(areas);
        final int unavailable = new UnavailableHeight(areas).get();
        final Supplier<Integer> height = new Height(this.area);
        final var sizeAdjustment = new unit.tuple.adjustment.Short<Integer, Integer>(
            width -> new Width(this.area).get(),
            integer -> {
                if (heights.get() == 0) {
                    final int empties = areas.count(
                        a -> a.result((pos, size) -> !size.isFix())
                    );
                    return (height.get() - unavailable) / empties;
                } else {
                    return (int) (integer / (double) heights.get() * Math.max(0, height.get() - unavailable));
                }
            }
        );
        this.area.adjustment(adjustment);
        this.shapes.foldLeft(
            (Area) new Sizeless(this.area),
            (previous, shape) -> shape.adjustment(
                new Short(
                    new unit.tuple.adjustment.Short<>(
                        x -> adjustment.posAdjustment().adjustedFirst(x),
                        y -> Area.result(
                            previous,
                            (x1, y1, w1, h1) -> y + y1 + Math.max(0, h1)
                        )
                    ),
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
