import java.awt.Color;
import java.awt.Window;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/*
 * Makes the native Windows title bar dark to match the app theme.
 *
 * Uses JNA (lib/jna-5.19.1.jar) to call DwmSetWindowAttribute:
 *  - Windows 10 (build 18985+) and 11: dark title bar mode
 *  - Windows 11 (build 22000+): custom caption, text and border colors
 *
 * Every call is best-effort. On other operating systems, older
 * Windows versions, or if JNA is missing, the title bar simply
 * stays the default color.
 */
final class DarkTitleBar {

    private static final int DWMWA_USE_IMMERSIVE_DARK_MODE = 20;
    private static final int DWMWA_USE_IMMERSIVE_DARK_MODE_OLD = 19;
    private static final int DWMWA_BORDER_COLOR = 34;
    private static final int DWMWA_CAPTION_COLOR = 35;
    private static final int DWMWA_TEXT_COLOR = 36;

    private static final int SWP_NOSIZE = 0x0001;
    private static final int SWP_NOMOVE = 0x0002;
    private static final int SWP_NOZORDER = 0x0004;
    private static final int SWP_NOACTIVATE = 0x0010;
    private static final int SWP_FRAMECHANGED = 0x0020;

    private static final boolean WINDOWS =
            System.getProperty("os.name", "").toLowerCase().startsWith("windows");

    private interface Dwmapi extends Library {
        int DwmSetWindowAttribute(Pointer hwnd, int attribute,
                                  IntByReference value, int size);
    }

    private interface User32 extends Library {
        boolean SetWindowPos(Pointer hwnd, Pointer after,
                             int x, int y, int cx, int cy, int flags);
    }

    private static Dwmapi dwmapi;
    private static User32 user32;

    private DarkTitleBar() {
    }

    /**
     * Applies the dark title bar. Call before the window is shown to
     * avoid a white flash; if it is already showing, the frame is
     * redrawn so the change appears immediately.
     */
    static void apply(Window window, Color caption, Color text, Color border) {

        if (!WINDOWS) {
            return;
        }

        try {

            if (dwmapi == null) {
                dwmapi = Native.load("dwmapi", Dwmapi.class);
                user32 = Native.load("user32", User32.class);
            }

            if (!window.isDisplayable()) {
                window.addNotify(); // creates the native window (HWND)
            }

            Pointer hwnd = Native.getWindowPointer(window);

            if (hwnd == null) {
                return;
            }

            IntByReference on = new IntByReference(1);

            if (dwmapi.DwmSetWindowAttribute(hwnd, DWMWA_USE_IMMERSIVE_DARK_MODE, on, 4) != 0) {
                dwmapi.DwmSetWindowAttribute(hwnd, DWMWA_USE_IMMERSIVE_DARK_MODE_OLD, on, 4);
            }

            // Windows 11 only; ignored (non-zero result) on Windows 10.
            dwmapi.DwmSetWindowAttribute(hwnd, DWMWA_CAPTION_COLOR, colorRef(caption), 4);
            dwmapi.DwmSetWindowAttribute(hwnd, DWMWA_TEXT_COLOR, colorRef(text), 4);
            dwmapi.DwmSetWindowAttribute(hwnd, DWMWA_BORDER_COLOR, colorRef(border), 4);

            if (window.isShowing()) {
                user32.SetWindowPos(hwnd, null, 0, 0, 0, 0,
                        SWP_NOSIZE | SWP_NOMOVE | SWP_NOZORDER
                                | SWP_NOACTIVATE | SWP_FRAMECHANGED);
            }

        } catch (Throwable ignored) {
            // Cosmetic only: never let this break the app.
        }
    }

    /** Win32 COLORREF is 0x00BBGGRR. */
    private static IntByReference colorRef(Color c) {

        return new IntByReference(
                (c.getBlue() << 16) | (c.getGreen() << 8) | c.getRed());
    }
}
