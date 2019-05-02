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
import java.util.ArrayList;
import java.util.List;
import joop.event.mouse.InputHardware;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.Covered;
import unit.area.MixArea;
import unit.area.NoAdjustment;
import unit.functional.Cached;
import unit.functional.Lazy;
import unit.pos.SoftPos;
import unit.scalar.WrapScalar;
import unit.size.MixSize;

/**
 * A collection of shapes.
 * <p>This class is mutable and not thread-safe because the area will be
 * calculated and cached.</p>
 * @since 0.76
 */
public class Shapes implements Shape {
    /**
     * The shapes.
     */
    private final Iterable<Shape> shapes;

    /**
     * The area of the shapes. It's the area that the shapes cover.
     */
    private final Lazy<Area> area;

    /**
     * Ctor.
     * @param shapes The shapes to contain.
     */
    public Shapes(final Shape... shapes) {
        this(List.of(shapes));
    }

    /**
     * Ctor.
     * @param shapes The shapes to contain.
     */
    public Shapes(final Iterable<Shape> shapes) {
        this.shapes = shapes;
        this.area = new Cached<>(
            () -> {
                final var areas = new ArrayList<Area>();
                shapes.forEach(
                    shape -> areas.add(
                        shape.adjustment(NoAdjustment.instance())
                    )
                );
                boolean anyWSoft = false;
                boolean anyHSoft = false;
                for (final Area area : areas) {
                    if (!area.cleanW().isFix()) {
                        anyWSoft = true;
                    }
                    if (!area.cleanH().isFix()) {
                        anyHSoft = true;
                    }
                }
                final var covered = new Covered(areas);
                final var width = covered.w();
                final var height = covered.h();
                return new MixArea(
                    new SoftPos(
                        covered.x(),
                        covered.y()
                    ),
                    new MixSize(
                        new WrapScalar(!anyWSoft, () -> width),
                        new WrapScalar(!anyHSoft, () -> height)
                    )
                );
            }
        );
    }

    @Override
    public final void draw(final Graphics graphics) {
        this.shapes.forEach(shape -> shape.draw(graphics));
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.shapes.forEach(shape -> shape.registerFor(source));
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        this.shapes.forEach(shape -> shape.adjustment(adjustment));
        return this.area.value();
    }
}
