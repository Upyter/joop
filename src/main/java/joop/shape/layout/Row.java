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
import java.util.Collection;
import java.util.List;
import joop.event.mouse.Mouse;
import joop.shape.Shape;
import unit.area.Area;
import unit.area.Covered;
import unit.pos.PosOf;
import unit.pos.Sum;

/**
 * A layout that adjust its shapes to be in a row
 * (like {@link javafx.scene.layout.HBox}).
 * @since 0.52
 */
public class Row implements Shape {
    /**
     * The shapes to adjust.
     */
    private final Collection<Shape> shapes;

    private boolean adjusted = false;

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
    public Row(final Collection<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public final void draw(final Graphics graphics) {
        this.shapes.forEach(it -> it.draw(graphics));
        if (!adjusted) {
            adjust(new NoAdjustment());
        }
    }

    @Override
    public final Area adjust(final Adjustment adjustment) {
        if (!adjusted) {
            adjusted = true;
            final var areas = new ArrayList<Area>(this.shapes.size());
            final var iterator = this.shapes.iterator();
            if (iterator.hasNext()) {
                Area previous = iterator.next().adjust(adjustment);
                areas.add(previous);
                while (iterator.hasNext()) {
                    System.out.println("Row previous: " + previous);
                    final Area area = previous;
                    previous = iterator.next().adjust(
                        new PosAdjustment(
                            pos -> new Sum(
                                pos,
                                new PosOf(
                                    () -> Area.result(
                                        area,
                                        (x, y, width, height) -> x + width
                                    ),
                                    () -> 0
                                )
                            )
                        )
                    );
                    areas.add(previous);
                }
                System.out.println("Shapes: " + shapes.size());
                System.out.println("Areas: " + areas.size());
            }
            this.area = new Covered(areas);
        }
        return this.area;
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        this.shapes.forEach(shape -> shape.registerFor(mouse));
    }
}