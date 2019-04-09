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

import io.vavr.collection.List;
import joop.event.mouse.Press;
import joop.shape.gui.Labeled;
import joop.shape.layout.Column;
import unit.area.Area;
import unit.area.AreaOf;
import unit.color.RGBA;
import unit.pos.SoftPos;
import unit.size.AdjustableSize;
import unit.size.SoftSize;

/**
 * A textual list.
 * <p>This class is mutable and not thread-safe due to the manipulation of the
 * visibility.</p>
 * @since 0.70
 */
public class ListView extends Column {
    /**
     * Ctor.
     * @param size The size of the list view.
     * @param texts The texts to be shown in the list.
     */
    public ListView(final AdjustableSize size , final String... texts) {
        this(size, List.of(texts));
    }

    /**
     * Ctor.
     * @param area The area of the list view.
     * @param texts The texts to be shown in the list.
     */
    public ListView(final Area area, final String... texts) {
        this(area, List.of(texts));
    }

    /**
     * Ctor.
     * @param texts The texts to be shown in the list.
     */
    public ListView(final String... texts) {
        this(List.of(texts));
    }

    /**
     * Ctor.
     * @param texts The texts to be shown in the list.
     */
    public ListView(final List<String> texts) {
        super(
            texts.map(
                text -> new Labeled(
                    text,
                    new AreaOf(
                        new SoftPos(),
                        new SoftSize()
                    ),
                    (area, color, event) -> new Visible(
                        visibility -> new Rect(
                            area,
                            new RGBA(130, 130, 130),
                            new Press(
                                () -> visibility.setValue(
                                    !visibility.getValue()
                                )
                            )
                        )
                    )
                )
            )
        );
    }

    /**
     * Ctor.
     * @param size The size of the list view.
     * @param texts The texts to be shown in the list.
     */
    public ListView(final AdjustableSize size, final List<String> texts) {
        this(
            new AreaOf(new SoftPos(), size),
            texts
        );
    }

    /**
     * Ctor.
     * @param area The area of the list view.
     * @param texts The texts to be shown in the list.
     */
    public ListView(final Area area, final List<String> texts) {
        super(
            area,
            texts.map(
                text -> new Labeled(
                    text,
                    new AreaOf(
                        new SoftPos(),
                        new SoftSize()
                    ),
                    (area1, color, event) -> new Visible(
                        visibility -> new Rect(
                            area1,
                            new RGBA(130, 130, 130),
                            new Press(
                                () -> visibility.setValue(
                                    !visibility.getValue()
                                )
                            )
                        )
                    )
                )
            )
        );
    }
}
