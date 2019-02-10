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

import java.util.Objects;
import joop.event.Event;
import unit.area.Area;
import unit.area.AreaOf;
import unit.area.OverlapArea;
import unit.area.OverlapAreaOf;

/**
 * An image from the resources folder. This is a shortcut for:
 * <p>this.class.getClassLoader().getResource(path).getFile()</p>
 * @since 0.39
 */
public class ResourceImage extends Image {
    /**
     * Ctor.
     * @param path The path to the image (inside the resources folder).
     */
    public ResourceImage(final String path) {
        this(path, new AreaOf());
    }

    /**
     * Ctor.
     * @param path The path to the image (inside the resources folder).
     * @param area The area of the image.
     */
    public ResourceImage(final String path, final Area area) {
        this(path, new OverlapAreaOf(area));
    }

    /**
     * Ctor.
     * @param path The path to the image (inside the resources folder).
     * @param area The area of the image.
     */
    public ResourceImage(final String path, final OverlapArea area) {
        // @checkstyle ParameterName (1 line)
        this(path, area, (x, y) -> { });
    }

    /**
     * Ctor.
     * @param path The path to the image (inside the resources folder).
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public ResourceImage(
        final String path, final Area area, final Event event
    ) {
        this(path, new OverlapAreaOf(area), event);
    }

    /**
     * Ctor.
     * @param path The path to the image (inside the resources folder).
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public ResourceImage(
        final String path, final OverlapArea area, final Event event
    ) {
        super(
            Objects.requireNonNull(
                Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResource(path)
            ).getFile(),
            area,
            event
        );
    }
}
