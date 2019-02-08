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

package joop.window;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import net.avh4.util.imagecomparison.hamcrest.ImageComparisonMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import unit.area.Area;
import unit.area.AreaOf;

/**
 * Tests for {@link BaseWindow}.
 * @since 0.4
 */
public final class BaseWindowTest {
    /**
     * {@link BaseWindow#show()} must create a window with the specified
     * internal area.
     * joop/src/main/java/resources/joop/window/whiteWindow453_642.png is used
     * as the expected image of the inner area.
     * @throws Exception When the {@link Robot} fails to create the screenshot.
     */
    @Test
    public void whiteWindow() throws Exception {
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 524;
        final var y = 78;
        final var width = 453;
        final var height = 642;
        new BaseWindow(
            new AreaOf(x, y, width, height)
        ).show();
        final long milliseconds = 350L;
        Thread.sleep(milliseconds);
        MatcherAssert.assertThat(
            new Robot().createScreenCapture(
                new Rectangle(x, y, width, height)
            ),
            ImageComparisonMatchers.looksLike("whiteWindow453_642.png")
        );
    }

    /**
     * {@link BaseWindow#BaseWindow(Area, Collection, Consumer)} must give its
     * frame to the given consumer.
     */
    @Test
    public void givesFrameToFeature() {
        final var width = 800;
        final var height = 122;
        final List<Dimension> sizes = new ArrayList<>(1);
        final var window = new BaseWindow(
            new AreaOf(width, height),
            List.of(),
            frame -> sizes.add(frame.getContentPane().getSize())
        );
        MatcherAssert.assertThat(
            sizes,
            Matchers.empty()
        );
        window.show();
        MatcherAssert.assertThat(
            sizes.get(0),
            Matchers.equalTo(new Dimension(width, height))
        );
    }
}
