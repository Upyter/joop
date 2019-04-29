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
import java.util.Objects;
import javax.imageio.ImageIO;
import joop.event.Event;
import joop.event.mouse.InputHardware;
import unit.area.Adjustment;
import unit.area.Area;
import unit.area.OverlapArea;
import unit.area.OverlapAreaOf;
import unit.area.SoftArea;
import unit.functional.Cached;
import unit.functional.Lazy;
import unit.pos.Pos;

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
     * The area of the image.
     */
    private OverlapArea area;

    /**
     * The event of the image.
     */
    private final Event event;

    /**
     * Ctor. The position will be (0|0).
     * @param path The path to the image.
     */
    public Image(final String path) {
        this(new File(path));
    }

    /**
     * Ctor.
     * @param path The path to the image.
     * @param area The area of the image.
     */
    public Image(final String path, final Area area) {
        this(new File(path), area);
    }

    /**
     * Ctor.
     * @param path The path to the image.
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public Image(final String path, final Area area, final Event event) {
        this(new File(path), area, event);
    }

    /**
     * Ctor.
     * @param path The path to the image.
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public Image(final String path, final OverlapArea area, final Event event) {
        this(new File(path), area, event);
    }

    /**
     * Ctor. The position will be (0|0).
     * @param file The image file.
     */
    public Image(final File file) {
        this(file, new SoftArea());
    }

    /**
     * Ctor.
     * @param file The image file.
     * @param area The area of the image.
     */
    public Image(final File file, final Area area) {
        // @checkstyle ParameterName (1 line)
        this(file, area, (x, y) -> { });
    }

    /**
     * Ctor.
     * @param file The image file.
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public Image(final File file, final Area area, final Event event) {
        this(
            file,
            new OverlapAreaOf(area),
            event
        );
    }

    /**
     * Ctor.
     * @param file The image file.
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public Image(final File file, final OverlapArea area, final Event event) {
        this(
            new Cached<>(
                () -> {
                    try {
                        return ImageIO.read(Objects.requireNonNull(file));
                    } catch (final IOException exception) {
                        throw new UncheckedIOException(
                            String.join(
                                "",
                                "Couldn't load the image. The given path ",
                                "seems to be wrong. Absolute Path: ",
                                file.getAbsolutePath(),
                                " Relative path: ",
                                file.getPath()
                            ),
                            exception
                        );
                    }
                }
            ),
            area,
            event
        );
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param pos The position of the image.
     */
    public Image(final BufferedImage loading, final Pos pos) {
        this(loading, new SoftArea(pos));
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param pos The position of the image.
     */
    public Image(final Lazy<BufferedImage> loading, final Pos pos) {
        this(loading, new SoftArea(pos));
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param area The area of the image.
     */
    public Image(final BufferedImage loading, final Area area) {
        // @checkstyle ParameterName (1 line)
        this(loading, area, (x, y) -> { });
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param area The area of the image.
     */
    public Image(final Lazy<BufferedImage> loading, final Area area) {
        // @checkstyle ParameterName (1 line)
        this(loading, area, (x, y) -> { });
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public Image(
        final BufferedImage loading, final Area area, final Event event
    ) {
        this(loading, new OverlapAreaOf(area), event);
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public Image(
        final Lazy<BufferedImage> loading, final Area area, final Event event
    ) {
        this(loading, new OverlapAreaOf(area), event);
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public Image(
        final BufferedImage loading, final OverlapArea area, final Event event
    ) {
        this(() -> loading, area, event);
    }

    /**
     * Ctor.
     * @param loading The loading of the image.
     * @param area The area of the image.
     * @param event The event of the image.
     */
    public Image(
        final Lazy<BufferedImage> loading,
        final OverlapArea area,
        final Event event
    ) {
        this.loading = Objects.requireNonNull(loading);
        this.area = area;
        this.event = event;
    }

    @Override
    public final void draw(final Graphics graphics) {
        graphics.drawImage(
            this.loading.value(),
            (int) this.area.x(),
            (int) this.area.y(),
            (int) this.area.w(),
            (int) this.area.h(),
            null
        );
    }

    @Override
    public final Area adjustment(final Adjustment adjustment) {
        this.area.adjustment(adjustment);
        return this.area;
    }

    @Override
    public final void registerFor(final InputHardware source) {
        this.event.registerFor(source, this.area);
    }
}
