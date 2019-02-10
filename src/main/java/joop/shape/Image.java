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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import javax.imageio.ImageIO;
import joop.event.mouse.Mouse;
import joop.shape.layout.Adjustment;
import unit.functional.Cached;
import unit.functional.Lazy;
import unit.pos.Pos;
import unit.pos.PosOf;
import unit.tuple.Tuple;

/**
 * An image. This class won't cache the result of the given {@link Lazy}
 * instance. Use {@link Cached} for this purpose.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments and the used constructor.
 * @since 0.22
 */
public class Image implements Shape {
    /**
     * The loading of the image.
     */
    private final Lazy<BufferedImage> loading;

    /**
     * The position of the image.
     */
    private final Pos pos;

    /**
     * Ctor. The position will be (0|0).
     * @param path The path to the image.
     */
    public Image(final String path) {
        this(
            new Cached<>(
                () -> {
                    try {
                        return ImageIO.read(new File(path));
                    } catch (final IOException exception) {
                        throw new UncheckedIOException(
                            String.join(
                                "",
                                "Couldn't load the image. The given path ",
                                "to be wrong. Path: ",
                                path
                            ),
                            exception
                        );
                    }
                }
            ),
            new PosOf()
        );
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param pos The position of the image.
     */
    public Image(final Lazy<BufferedImage> loading, final Pos pos) {
        this.loading = loading;
        this.pos = pos;
    }

    @Override
    public final Optional<Shape> draw(
        final Graphics graphics, final Adjustment adjustment
    ) {
        Tuple.applyOn(
            this.pos,
            // @checkstyle ParameterName (1 line)
            (x, y) -> graphics.drawImage(this.loading.value(), x, y, null)
        );
        return Optional.of(this);
    }

    @Override
    public final void registerFor(final Mouse mouse) {
        // currently no implementation
    }
}
