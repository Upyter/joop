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

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import joop.event.mouse.Mouse;
import joop.shape.Shape;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.AreaOf;
import unit.area.Covered;
import unit.area.Sizeless;
import unit.area.adjustment.NoAdjustment;
import unit.area.adjustment.Short;
import unit.area.supplier.Height;
import unit.area.supplier.HeightSum;
import unit.area.supplier.Width;
import unit.functional.Cached;
import unit.functional.Lazy;
import unit.pos.SoftPos;
import unit.pos.YAdjustment;
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

    private Lazy<Area> area;

    private boolean adjusted = false;

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
        this.shapes = shapes;
        this.area = new Cached<>(
            () -> {
                final var areas = new ArrayList<Area>(this.shapes.size());
                this.shapes.forEach(shape -> areas.add(shape.adjustment(new NoAdjustment())));
                return Area.result(
                    new Covered(areas),
                    (x, y, w, h) -> new AreaOf(
                        new SoftPos(x, y),
                        new SoftSize(w, h)
                    )
                );
            }
        );
    }

    @Override
    public final void draw(final Graphics graphics) {
        this.shapes.forEach(it -> it.draw(graphics));
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        final var areas = new ArrayList<Area>(this.shapes.size());
        this.shapes.forEach(shape -> areas.add(shape.adjustment(new NoAdjustment())));
        this.area.value().adjustment(adjustment);
        Area previous = new Sizeless(this.area.value());
        for (Shape shape : shapes) {
            final Area pre_area = previous;
            previous = shape.adjustment(
                new Short(
                    new YAdjustment(
                        y -> Area.result(
                            pre_area,
                            (x1, y1, w1, h1) -> y + y1 + h1
                        )
                    ),
                    new unit.tuple.adjustment.Short<>(
                        width -> new Width(this.area.value()).get(),
                        integer -> {
                            final double heights = new HeightSum(areas).get();
                            return (int) (integer / heights * new Height(this.area.value()).get());
                        }
                    )
                )
            );
        }
        return this.area.value();
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.shapes.forEach(shape -> shape.registerFor(mouse));
    }
}
