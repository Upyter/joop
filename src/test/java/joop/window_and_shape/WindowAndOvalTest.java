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

package joop.window_and_shape;

import java.awt.Rectangle;
import java.awt.Robot;
import joop.shape.Oval;
import joop.window.BaseWindow;
import net.avh4.util.imagecomparison.hamcrest.ImageComparisonMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import unit.area.AreaOf;
import unit.color.RGBA;
import unit.size.SizeOf;

/**
 * Tests for the combined use of {@link BaseWindow} and {@link Oval}.
 * @since 0.9
 */
public final class WindowAndOvalTest {
    /**
     * {@link BaseWindow#show()} must show a black oval for {@link BaseWindow}
     * with a black {@link Oval} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * blackOvalAndWhiteWindow.png is used as the expected image of the inner
     * area.
     * @throws Exception Because of Thread.sleep and Robot.
     */
    @Test
    public void black() throws Exception {
        // @checkstyle LocalFinalVariableName (6 lines)
        final var windowWidth = 300;
        final var windowHeight = 200;
        final var ovalX = 10;
        final var ovalY = 20;
        final var ovalWidth = 100;
        final var ovalHeight = 100;
        new BaseWindow(
            new AreaOf(
                new SizeOf(windowWidth, windowHeight)
            ),
            new Oval(
                new AreaOf(ovalX, ovalY, ovalWidth, ovalHeight)
            )
        ).show();
        final long milliseconds = 350L;
        Thread.sleep(milliseconds);
        MatcherAssert.assertThat(
            new Robot().createScreenCapture(
                new Rectangle(0, 0, windowWidth, windowHeight)
            ),
            ImageComparisonMatchers.looksLike(
                "window_and_oval/blackOvalOnWhiteWindow.png"
            )
        );
    }

    /**
     * {@link BaseWindow#show()} must show a colored oval for {@link BaseWindow}
     * with a colored {@link Oval} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * coloredOvalAndWhiteWindow.png is used as the expected image of the inner
     * area.
     * @throws Exception Because of Thread.sleep and Robot.
     */
    @Test
    public void colored() throws Exception {
        // @checkstyle LocalFinalVariableName (6 lines)
        final var windowWidth = 450;
        final var windowHeight = 300;
        final var ovalX = 40;
        final var ovalY = 30;
        final var ovalWidth = 140;
        final var ovalHeight = 150;
        final var blue = 255;
        new BaseWindow(
            new AreaOf(
                new SizeOf(windowWidth, windowHeight)
            ),
            new Oval(
                new AreaOf(ovalX, ovalY, ovalWidth, ovalHeight),
                new RGBA(0, 0, blue)
            )
        ).show();
        final long milliseconds = 350L;
        Thread.sleep(milliseconds);
        MatcherAssert.assertThat(
            new Robot().createScreenCapture(
                new Rectangle(0, 0, windowWidth, windowHeight)
            ),
            ImageComparisonMatchers.looksLike(
                "window_and_oval/coloredOvalOnWhiteWindow.png"
            )
        );
    }
}
