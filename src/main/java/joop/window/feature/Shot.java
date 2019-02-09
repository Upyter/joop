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

package joop.window.feature;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * An image of the window. It will be taken each time when
 * {@link #accept(JFrame)} is called.
 * <p>This class is immutable and thread-safe.</p>
 * @since 0.23
 */
public class Shot implements Consumer<JFrame> {
    /**
     * The path (name including) of the output image.
     */
    private final String path;

    /**
     * Ctor.
     * @param path The path (name including) of the output image.
     */
    public Shot(final String path) {
        this.path = path;
    }

    @Override
    public final void accept(final JFrame frame) {
        final BufferedImage screenshot;
        try {
            screenshot = new Robot().createScreenCapture(frame.getBounds());
        } catch (final AWTException exception) {
            throw new IllegalStateException(
                "Couldn't create the screenshot", exception
            );
        }
        try {
            ImageIO.write(
                screenshot,
                this.path.substring(this.path.lastIndexOf('.')),
                new File(this.path)
            );
        } catch (final IOException exception) {
            throw new UncheckedIOException(
                "Couldn't save the image.", exception
            );
        }
    }
}
