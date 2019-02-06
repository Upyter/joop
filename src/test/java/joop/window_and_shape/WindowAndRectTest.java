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
import joop.shape.Rect;
import joop.window.BaseWindow;
import net.avh4.util.imagecomparison.hamcrest.ImageComparisonMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import unit.area.AreaOf;
import unit.size.SizeOf;

/**
 * Tests for the combined use of {@link BaseWindow} and {@link Rect}.
 * @since 0.8
 */
public final class WindowAndRectTest {
    /**
     * {@link BaseWindow#show()} must show a black rect for {@link BaseWindow}
     * with a black {@link Rect} on it.
     * joop/src/main/java/resources/joop/window_and_shape/
     * blackRectAndWhiteWindow.png is used as the expected image of the inner
     * area.
     * @throws Exception Because of Thread.sleep and Robot.
     */
    @Test
    public void blackRect() throws Exception {
        // @checkstyle LocalFinalVariableName (6 lines)
        final var windowWidth = 500;
        final var windowHeight = 600;
        final var rectX = 50;
        final var rectY = 200;
        final var rectWidth = 100;
        final var rectHeight = 140;
        new BaseWindow(
            new AreaOf(
                new SizeOf(windowWidth, windowHeight)
            ),
            new Rect(
                new AreaOf(rectX, rectY, rectWidth, rectHeight)
            )
        ).show();
        final long milliseconds = 350L;
        Thread.sleep(milliseconds);
        MatcherAssert.assertThat(
            new Robot().createScreenCapture(
                new Rectangle(0, 0, windowWidth, windowHeight)
            ),
            ImageComparisonMatchers.looksLike(
                "window_and_rect/blackRectOnWhiteWindow.png"
            )
        );
    }
}
