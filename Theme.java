import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicFormattedTextFieldUI;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/*
 * Shared dark theme for every window in the app.
 *
 * The GUI classes still build their screens with the original
 * light-theme colors. Theme.apply(...) then walks the component
 * tree and converts it: a warm radial-glow background, translucent
 * "glass" cards, rounded buttons with hover states, dark inputs
 * and slim scrollbars.
 *
 * Usage: call Theme.apply(window, fullScreen) right before
 * window.setVisible(true). Dialogs that are not themed explicitly
 * (e.g. JOptionPane) are themed automatically when they open.
 */
public final class Theme {

    /*
     * PALETTE
     */

    public static final Color BASE = new Color(14, 17, 24);
    public static final Color TITLE_BAR = new Color(24, 23, 28);
    public static final Color TEXT = new Color(238, 234, 230);
    public static final Color MUTED = new Color(166, 168, 178);
    public static final Color ACCENT = new Color(222, 132, 88);
    public static final Color ACCENT_DARK = new Color(184, 96, 62);
    public static final Color ACCENT_TEXT = new Color(242, 172, 128);
    public static final Color FIELD = new Color(24, 27, 35);
    public static final Color FIELD_READONLY = new Color(19, 22, 29);
    public static final Color FIELD_BORDER = new Color(255, 255, 255, 38);
    public static final Color SELECTION = new Color(222, 132, 88, 150);

    private static final Color GLASS_FILL = new Color(255, 255, 255, 13);
    private static final Color GLASS_STROKE = new Color(255, 255, 255, 30);
    private static final Color SIDEBAR_FILL = new Color(8, 10, 15, 120);

    private static final String DONE = "theme.done";
    private static final String ROLE = "theme.role";
    private static final String KIND = "theme.kind";
    private static final String ORIGINAL_BG = "theme.originalBg";

    private static final String PLACEHOLDER = "theme.placeholder";
    private static final String HOVER = "theme.hover";

    private static boolean installed = false;
    private static String fontFamily = "SansSerif";
    private static String iconFamily = null;

    private Theme() {
    }

    /*
     * GLOBAL SETUP
     */

    public static synchronized void install() {

        if (installed) {
            return;
        }

        installed = true;

        UIManager.put("swing.boldMetal", Boolean.FALSE);

        for (String name : GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames()) {

            if (name.equals("Segoe UI")) {
                fontFamily = name;
            }

            if (name.equals("Segoe Fluent Icons")
                    || (iconFamily == null && name.equals("Segoe MDL2 Assets"))) {
                iconFamily = name;
            }
        }

        ColorUIResource base = new ColorUIResource(BASE);
        ColorUIResource text = new ColorUIResource(TEXT);
        ColorUIResource field = new ColorUIResource(FIELD);
        ColorUIResource selection = new ColorUIResource(ACCENT_DARK);

        String[] backgrounds = {
                "Panel.background", "OptionPane.background",
                "Viewport.background", "ScrollPane.background",
                "CheckBox.background", "RadioButton.background",
                "Spinner.background", "Button.background"
        };

        for (String key : backgrounds) {
            UIManager.put(key, base);
        }

        String[] foregrounds = {
                "Label.foreground", "OptionPane.messageForeground",
                "CheckBox.foreground", "RadioButton.foreground",
                "Button.foreground", "TextField.foreground",
                "PasswordField.foreground", "TextArea.foreground",
                "FormattedTextField.foreground", "ComboBox.foreground",
                "List.foreground", "TitledBorder.titleColor"
        };

        for (String key : foregrounds) {
            UIManager.put(key, text);
        }

        String[] fields = {
                "TextField.background", "PasswordField.background",
                "TextArea.background", "FormattedTextField.background",
                "ComboBox.background", "List.background"
        };

        for (String key : fields) {
            UIManager.put(key, field);
        }

        UIManager.put("TextField.caretForeground", new ColorUIResource(ACCENT_TEXT));
        UIManager.put("TextArea.caretForeground", new ColorUIResource(ACCENT_TEXT));
        UIManager.put("PasswordField.caretForeground", new ColorUIResource(ACCENT_TEXT));
        UIManager.put("ComboBox.selectionBackground", selection);
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.WHITE));
        UIManager.put("List.selectionBackground", selection);
        UIManager.put("List.selectionForeground", new ColorUIResource(Color.WHITE));
        UIManager.put("ToolTip.background", new ColorUIResource(FIELD));
        UIManager.put("ToolTip.foreground", text);
        UIManager.put("PopupMenu.border",
                BorderFactory.createLineBorder(new Color(60, 64, 74)));

        UIManager.put("OptionPane.messageFont",
                new FontUIResource(uiFont(Font.PLAIN, 14)));
        UIManager.put("OptionPane.buttonFont",
                new FontUIResource(uiFont(Font.BOLD, 13)));

        UIManager.put("OptionPane.questionIcon", new DialogIcon("?", ACCENT));
        UIManager.put("OptionPane.informationIcon", new DialogIcon("i", new Color(96, 156, 230)));
        UIManager.put("OptionPane.warningIcon", new DialogIcon("!", new Color(232, 176, 72)));
        UIManager.put("OptionPane.errorIcon", new DialogIcon("×", new Color(226, 92, 86)));

        /*
         * Theme any window that opens without an explicit
         * Theme.apply call (JOptionPane dialogs, etc.).
         */
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {

            if (event.getID() == WindowEvent.WINDOW_OPENED
                    && (event.getSource() instanceof JFrame
                    || event.getSource() instanceof JDialog)) {

                Window window = (Window) event.getSource();

                if (!(((RootPaneContainer) window).getContentPane()
                        instanceof GradientPanel)) {

                    apply(window, false);

                    /*
                     * The window was sized before restyling (new fonts,
                     * padding). Re-pack so content isn't clipped; e.g. a
                     * word-wrapped JTextArea is measured at zero width on
                     * the first pack and ends up laid out off-screen.
                     */
                    if (window instanceof JDialog) {
                        window.pack();
                        window.setLocationRelativeTo(window.getOwner());
                    }

                    window.repaint();
                }
            }

        }, AWTEvent.WINDOW_EVENT_MASK);
    }

    /*
     * APPLY THEME TO A WINDOW
     */

    public static void apply(Window window, boolean fullScreen) {

        install();

        if (!(window instanceof RootPaneContainer)) {
            return;
        }

        RootPaneContainer rpc = (RootPaneContainer) window;
        Container old = rpc.getContentPane();

        if (!(old instanceof GradientPanel)) {

            GradientPanel gradient = new GradientPanel();

            if (old instanceof JComponent) {
                ((JComponent) old).setOpaque(false);
            }

            gradient.add(old, BorderLayout.CENTER);
            rpc.setContentPane(gradient);

            style(old, false, null);
        }

        if (window instanceof Frame) {

            Frame frame = (Frame) window;

            frame.setResizable(true);

            if (fullScreen) {
                frame.setMinimumSize(new Dimension(1000, 640));
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            }

        } else if (window instanceof Dialog) {

            ((Dialog) window).setResizable(true);
        }

        try {
            DarkTitleBar.apply(window, TITLE_BAR, TEXT, new Color(44, 44, 52));
        } catch (Throwable ignored) {
            // JNA not on the classpath: keep the default title bar.
        }
    }

    /*
     * LAYOUT HELPERS
     */

    /** Centers content at a fixed size inside a full-screen window. */
    public static JPanel centered(JComponent content, Dimension size) {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        content.setPreferredSize(size);
        wrapper.add(content);

        return wrapper;
    }

    /** Centers a vertical BoxLayout panel's children vertically. */
    public static void centerVertically(JPanel boxPanel) {

        boxPanel.add(Box.createVerticalGlue(), 0);
        boxPanel.add(Box.createVerticalGlue());
    }

    /** Scales every font inside a container (for full-screen layouts). */
    public static void scaleFonts(Container container, float factor) {

        for (Component child : container.getComponents()) {

            Font font = child.getFont();

            if (font != null) {
                child.setFont(font.deriveFont(font.getSize2D() * factor));
            }

            if (child instanceof Container) {
                scaleFonts((Container) child, factor);
            }
        }
    }

    /** Shows hint text inside an empty input. */
    public static void placeholder(JTextComponent field, String hint) {

        field.putClientProperty(PLACEHOLDER, hint);
    }

    /**
     * A row of glass stat tiles. Values are re-read every time the
     * window regains focus, so counts stay current after the user
     * adds questions, answers or reviews in another window.
     */
    public static JPanel statRow(Window window, String[] labels, Supplier<int[]> values) {

        JPanel row = new JPanel(new GridLayout(1, labels.length, 18, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(860, 120));
        row.setPreferredSize(new Dimension(860, 120));

        JLabel[] numbers = new JLabel[labels.length];

        for (int i = 0; i < labels.length; i++) {

            JPanel tile = new JPanel(new BorderLayout(0, 4));
            role(tile, "stat");
            tile.setBorder(new EmptyBorder(18, 22, 18, 22));

            numbers[i] = new JLabel("0");
            numbers[i].setFont(uiFont(Font.BOLD, 34));
            numbers[i].setForeground(TEXT);

            JLabel caption = new JLabel(labels[i]);
            caption.setFont(uiFont(Font.PLAIN, 14));
            caption.setForeground(MUTED);

            tile.add(numbers[i], BorderLayout.CENTER);
            tile.add(caption, BorderLayout.SOUTH);
            row.add(tile);
        }

        Runnable refresh = () -> {

            try {

                int[] v = values.get();

                for (int i = 0; i < numbers.length && i < v.length; i++) {
                    numbers[i].setText(String.valueOf(v[i]));
                }

            } catch (RuntimeException ignored) {
                // Stats are informational; never block the dashboard.
            }
        };

        refresh.run();

        window.addWindowFocusListener(new WindowAdapter() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
                refresh.run();
            }
        });

        return row;
    }

    /** Marks a panel as a glass card ("card"), hero or sidebar. */
    public static void role(JComponent component, String role) {

        component.putClientProperty(ROLE, role);
    }

    /** Forces a button style: "primary", "secondary", "nav" or "link". */
    public static void kind(AbstractButton button, String kind) {

        button.putClientProperty(KIND, kind);
    }

    /*
     * RECURSIVE STYLING
     */

    private static void style(Component c, boolean inNav, Color cardBg) {

        if (c instanceof JComponent) {

            JComponent jc = (JComponent) c;

            if (Boolean.TRUE.equals(jc.getClientProperty(DONE))) {
                return;
            }

            jc.putClientProperty(DONE, Boolean.TRUE);
            jc.putClientProperty(ORIGINAL_BG, jc.getBackground());
        }

        c.setFont(modernFont(c.getFont()));

        if (c instanceof JButton && c.getClass() == JButton.class) {

            styleButton((JButton) c, inNav);
            return;
        }

        if (c instanceof JComboBox) {
            styleCombo((JComboBox<?>) c);
            return;
        }

        if (c instanceof JScrollBar) {
            return;
        }

        if (c instanceof javax.swing.text.JTextComponent) {
            styleText((javax.swing.text.JTextComponent) c);
            return;
        }

        if (c instanceof JLabel) {
            styleLabel((JLabel) c);
            return;
        }

        if (c instanceof JCheckBox || c instanceof JRadioButton) {

            AbstractButton b = (AbstractButton) c;
            b.setOpaque(false);
            b.setFocusPainted(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setIconTextGap(8);

            if (c instanceof JCheckBox) {
                b.setIcon(new CheckIcon(false));
                b.setSelectedIcon(new CheckIcon(true));
                b.setRolloverIcon(new CheckIcon(false));
                b.setRolloverSelectedIcon(new CheckIcon(true));
            }

            remapForeground(b);
            return;
        }

        if (c instanceof JList) {

            JList<?> list = (JList<?>) c;
            list.setBackground(FIELD);
            list.setForeground(TEXT);
            list.setSelectionBackground(ACCENT_DARK);
            list.setSelectionForeground(Color.WHITE);
            list.setFixedCellHeight(-1);

            if (list.getCellRenderer() instanceof DefaultListCellRenderer) {
                list.setCellRenderer(new RowRenderer());
            }
            list.setBorder(new EmptyBorder(6, 6, 6, 6));
            return;
        }

        if (c instanceof JSeparator) {
            c.setForeground(GLASS_STROKE);
            ((JComponent) c).setOpaque(false);
            return;
        }

        if (c instanceof JScrollPane) {
            styleScrollPane((JScrollPane) c, inNav, cardBg);
            return;
        }

        if (c instanceof JSpinner) {

            JSpinner spinner = (JSpinner) c;
            spinner.setBorder(new FieldBorder(2, 4, FIELD, 14));
            spinner.setOpaque(false);
            JComponent editor = spinner.getEditor();

            if (editor instanceof JSpinner.DefaultEditor) {

                JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
                styleText(tf);
                tf.setBorder(new EmptyBorder(4, 8, 4, 8));
            }

            return;
        }

        if (c instanceof JPanel) {

            JPanel panel = (JPanel) c;
            String role = resolvePanelRole(panel, cardBg);

            switch (role) {

                case "card":
                    makeGlass(panel, GLASS_FILL, GLASS_STROKE, 20, false);
                    cardBg = (Color) panel.getClientProperty(ORIGINAL_BG);
                    break;

                case "darkcard":
                    makeGlass(panel, new Color(0, 0, 0, 70), GLASS_STROKE, 20, false);
                    cardBg = (Color) panel.getClientProperty(ORIGINAL_BG);
                    break;

                case "tinted":
                    makeGlass(panel, tint(panel.getBackground()), GLASS_STROKE, 16, false);
                    cardBg = (Color) panel.getClientProperty(ORIGINAL_BG);
                    break;

                case "stat":
                    panel.setOpaque(false);
                    panel.setBorder(new StatBorder(insetsOf(panel)));
                    cardBg = (Color) panel.getClientProperty(ORIGINAL_BG);
                    break;

                case "hero":
                    panel.setOpaque(false);
                    panel.setBorder(new HeroBorder(insetsOf(panel)));
                    cardBg = (Color) panel.getClientProperty(ORIGINAL_BG);
                    break;

                case "sidebar":
                    makeGlass(panel, SIDEBAR_FILL, GLASS_STROKE, 0, true);
                    inNav = true;
                    break;

                default:
                    panel.setOpaque(false);
                    break;
            }
        }

        if (c instanceof JComponent && !(c instanceof JPanel)) {

            JComponent jc = (JComponent) c;

            if (jc.isOpaque() && !(c instanceof JViewport)) {
                jc.setOpaque(false);
            }
        }

        if (c instanceof Container) {
            styleChildren((Container) c, inNav, cardBg);
        }
    }

    private static void styleChildren(Container container, boolean inNav, Color cardBg) {

        for (Component child : container.getComponents()) {
            style(child, inNav, cardBg);
        }

        final boolean nav = inNav;
        final Color bg = cardBg;

        container.addContainerListener(new ContainerAdapter() {

            @Override
            public void componentAdded(ContainerEvent e) {
                style(e.getChild(), nav, bg);
            }
        });
    }

    private static String resolvePanelRole(JPanel panel, Color cardBg) {

        Object explicit = panel.getClientProperty(ROLE);

        if (explicit instanceof String) {
            return (String) explicit;
        }

        if (!panel.isOpaque()) {
            return "transparent";
        }

        Color bg = panel.getBackground();

        if (bg == null || bg instanceof UIResource) {
            return "transparent";
        }

        if (cardBg != null && bg.equals(cardBg)) {
            return "transparent";
        }

        float[] hsb = Color.RGBtoHSB(bg.getRed(), bg.getGreen(), bg.getBlue(), null);

        boolean white = bg.getRed() >= 250 && bg.getGreen() >= 250 && bg.getBlue() >= 250;

        if (hsb[1] < 0.15f && hsb[2] > 0.85f) {

            if (cardBg == null && (white || hasLineBorder(panel.getBorder()))) {
                return "card";
            }

            return "transparent";
        }

        if (hsb[1] >= 0.3f && hsb[2] > 0.3f && hsb[2] <= 0.9f) {
            return "sidebar";
        }

        if (hsb[2] < 0.35f) {
            return "darkcard";
        }

        return cardBg == null ? "tinted" : "transparent";
    }

    private static boolean hasLineBorder(Border border) {

        if (border instanceof LineBorder) {
            return true;
        }

        if (border instanceof CompoundBorder) {

            CompoundBorder cb = (CompoundBorder) border;
            return hasLineBorder(cb.getOutsideBorder())
                    || hasLineBorder(cb.getInsideBorder());
        }

        return false;
    }

    private static Insets insetsOf(JComponent c) {

        Border border = c.getBorder();

        if (border == null) {
            return new Insets(0, 0, 0, 0);
        }

        return border.getBorderInsets(c);
    }

    private static void makeGlass(JPanel panel, Color fill, Color stroke,
                                  int radius, boolean sidebar) {

        Insets insets = insetsOf(panel);

        if (!sidebar && insets.top < 12) {
            insets = new Insets(
                    Math.max(insets.top, 16), Math.max(insets.left, 18),
                    Math.max(insets.bottom, 16), Math.max(insets.right, 18));
        }

        panel.setOpaque(false);
        panel.setBorder(new GlassBorder(insets, fill, stroke, radius, sidebar));
    }

    private static Color tint(Color bg) {

        float[] hsb = Color.RGBtoHSB(bg.getRed(), bg.getGreen(), bg.getBlue(), null);
        Color tinted = Color.getHSBColor(hsb[0], Math.min(1f, hsb[1] + 0.4f), 0.55f);

        return new Color(tinted.getRed(), tinted.getGreen(), tinted.getBlue(), 45);
    }

    /*
     * COMPONENT STYLES
     */

    /**
     * Segoe UI as a composite font, so symbols it lacks (e.g. the
     * check mark) fall back to other system fonts instead of a box.
     */
    static Font uiFont(int style, int size) {

        // Note: this is a FontUIResource. setUI() replaces UIResource
        // fonts, so callers re-apply the font after every setUI().
        return javax.swing.text.StyleContext.getDefaultStyleContext()
                .getFont(fontFamily, style, size);
    }

    private static Font modernFont(Font font) {

        if (font == null) {
            return null;
        }

        String family = font.getFamily();

        if (family.equals("SansSerif") || family.equals("Dialog")
                || family.equals("Arial") || font.getName().equals("SansSerif")
                || font.getName().equals("Dialog")) {

            return uiFont(font.getStyle(), font.getSize());
        }

        return font;
    }

    private static void styleLabel(JLabel label) {

        label.setOpaque(false);
        remapForeground(label);
    }

    private static void remapForeground(JComponent c) {

        c.setForeground(mapForeground(c.getForeground()));

        PropertyChangeListener listener = e -> {

            Object value = e.getNewValue();

            if (value instanceof Color) {

                Color mapped = mapForeground((Color) value);

                if (!mapped.equals(value)) {
                    c.setForeground(mapped);
                }
            }
        };

        c.addPropertyChangeListener("foreground", listener);
    }

    /** Converts a light-theme text color to its dark-theme equivalent. */
    static Color mapForeground(Color c) {

        if (c == null || c instanceof UIResource) {
            return TEXT;
        }

        if (c.equals(TEXT) || c.equals(MUTED) || c.equals(ACCENT_TEXT)) {
            return c;
        }

        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);

        if (hsb[2] < 0.40f) {
            return TEXT;
        }

        if (hsb[1] < 0.40f) {

            if (hsb[2] < 0.38f) {
                return TEXT;
            }

            if (hsb[2] < 0.75f) {
                return MUTED;
            }

            return c;
        }

        if (hsb[0] > 0.55f && hsb[0] < 0.70f) {
            return ACCENT_TEXT;
        }

        return Color.getHSBColor(hsb[0], Math.min(hsb[1], 0.55f), Math.max(hsb[2], 0.92f));
    }

    private static void styleText(javax.swing.text.JTextComponent text) {

        boolean editable = text.isEditable();
        boolean inScroll = text.getParent() instanceof JViewport;
        boolean inSpinner = SwingUtilities.getAncestorOfClass(JSpinner.class, text) != null;
        boolean rounded = !inScroll && !inSpinner;

        Font textFont = text.getFont();

        if (rounded) {

            if (text.getClass() == JPasswordField.class) {
                text.setUI(new RoundPasswordUI());
            } else if (text.getClass() == JFormattedTextField.class) {
                text.setUI(new RoundFormattedUI());
            } else if (text.getClass() == JTextField.class) {
                text.setUI(new RoundFieldUI());
            } else {
                rounded = false;
            }

            text.setFont(textFont);
        }

        text.putClientProperty("caretWidth", 2);
        text.setOpaque(!rounded && !inScroll);
        text.setBackground(editable ? FIELD : FIELD_READONLY);
        text.setForeground(TEXT);
        text.setCaretColor(ACCENT_TEXT);
        text.setSelectionColor(SELECTION);
        text.setSelectedTextColor(Color.WHITE);
        text.setFont(modernFont(text.getFont()));

        text.addPropertyChangeListener("editable", e ->
                text.setBackground(text.isEditable() ? FIELD : FIELD_READONLY));

        if (inScroll) {

            text.setBorder(new EmptyBorder(10, 12, 10, 12));

            /*
             * Read-only text areas would otherwise stay scrolled to
             * the bottom after setText(), hiding the first lines.
             */
            text.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

                private void top() {
                    SwingUtilities.invokeLater(() -> {
                        if (!text.isEditable()) {
                            text.setCaretPosition(0);
                        }
                    });
                }

                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    top();
                }

                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    top();
                }

                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                }
            });

            if (!editable) {
                SwingUtilities.invokeLater(() -> text.setCaretPosition(0));
            }

        } else {

            FieldBorder border = rounded ? new FieldBorder() : new FieldBorder(6, 12, null, 0);
            text.setBorder(border);

            text.addFocusListener(new FocusAdapter() {

                @Override
                public void focusGained(FocusEvent e) {
                    border.focused = true;
                    text.repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    border.focused = false;
                    text.repaint();
                }
            });
        }
    }

    private static void styleScrollPane(JScrollPane scroll, boolean inNav, Color cardBg) {

        scroll.setOpaque(false);
        scroll.setViewportBorder(null);

        JViewport viewport = scroll.getViewport();
        Component view = viewport.getView();

        if (view instanceof javax.swing.text.JTextComponent || view instanceof JList) {

            boolean readOnly = view instanceof JTextComponent
                    && !((JTextComponent) view).isEditable();

            FieldBorder border = new FieldBorder(5, 5,
                    readOnly ? FIELD_READONLY : FIELD, 14);
            scroll.setBorder(border);
            viewport.setOpaque(false);

            if (view instanceof JTextComponent) {

                view.addFocusListener(new FocusAdapter() {

                    @Override
                    public void focusGained(FocusEvent e) {
                        border.focused = ((JTextComponent) view).isEditable();
                        scroll.repaint();
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        border.focused = false;
                        scroll.repaint();
                    }
                });
            }

        } else {

            viewport.setOpaque(false);
            scroll.setBorder(BorderFactory.createEmptyBorder());
        }

        styleScrollBar(scroll.getVerticalScrollBar());
        styleScrollBar(scroll.getHorizontalScrollBar());

        JPanel corner = new JPanel();
        corner.setOpaque(false);
        scroll.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, corner);

        if (view != null) {
            style(view, inNav, cardBg);

            if (view instanceof JTextComponent || view instanceof JList) {
                ((JComponent) view).setOpaque(false);
            }
        }

        viewport.addChangeListener(e -> {

            Component current = viewport.getView();

            if (current instanceof JComponent
                    && !Boolean.TRUE.equals(((JComponent) current).getClientProperty(DONE))) {

                style(current, inNav, cardBg);
            }
        });
    }

    private static void styleScrollBar(JScrollBar bar) {

        bar.setUI(new SlimScrollBarUI());
        bar.setOpaque(false);
        bar.setUnitIncrement(16);

        if (bar.getOrientation() == JScrollBar.VERTICAL) {
            bar.setPreferredSize(new Dimension(10, 0));
        } else {
            bar.setPreferredSize(new Dimension(0, 10));
        }
    }

    private static void styleCombo(JComboBox<?> combo) {

        Font comboFont = combo.getFont();
        combo.setUI(new FlatComboUI());
        combo.setFont(comboFont);
        combo.setBackground(FIELD);
        combo.setForeground(TEXT);
        FieldBorder border = new FieldBorder(4, 10, null, 14);
        combo.setBorder(border);

        combo.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                border.focused = true;
                combo.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                border.focused = false;
                combo.repaint();
            }
        });
        combo.setFont(modernFont(combo.getFont()));
        combo.setOpaque(false);
    }

    private static void styleButton(JButton button, boolean inNav) {

        if (inNav && button.getClientProperty(KIND) == null) {
            button.putClientProperty(KIND, "nav");
        }

        Font buttonFont = button.getFont();
        button.setUI(new ModernButtonUI());
        button.setFont(buttonFont);

        if ("nav".equals(button.getClientProperty(KIND))) {

            Font f = button.getFont();
            button.setFont(uiFont(Font.BOLD, Math.min(f.getSize(), 15)));
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
            button.setPreferredSize(new Dimension(200, 46));
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorder(new EmptyBorder(0, iconFamily != null ? 50 : 18, 0, 12));
        }

        /*
         * HTML text is rendered from the foreground color,
         * not by paintText, so remap it directly.
         */
        String text = button.getText();

        if (text != null && text.toLowerCase().startsWith("<html")) {

            String kind = ModernButtonUI.kindOf(button);
            button.setForeground(kind.equals("primary") ? Color.WHITE : TEXT);
        }
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Insets insets = insetsOf(button);

        if (insets.left < 14 && !"nav".equals(button.getClientProperty(KIND))) {
            button.setBorder(new EmptyBorder(
                    Math.max(insets.top, 8), 18,
                    Math.max(insets.bottom, 8), 18));
        }
    }

    /*
     * BACKGROUND PANEL
     */

    static final class GradientPanel extends JPanel {

        private BufferedImage cache;

        GradientPanel() {
            super(new BorderLayout());
            setOpaque(true);
            setBackground(BASE);
        }

        @Override
        protected void paintComponent(Graphics g) {

            int w = getWidth();
            int h = getHeight();

            if (w <= 0 || h <= 0) {
                return;
            }

            if (cache == null || cache.getWidth() != w || cache.getHeight() != h) {
                cache = render(w, h);
            }

            g.drawImage(cache, 0, 0, null);
        }

        private static BufferedImage render(int w, int h) {

            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = img.createGraphics();

            g2.setRenderingHint(RenderingHints.KEY_DITHERING,
                    RenderingHints.VALUE_DITHER_ENABLE);
            g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_QUALITY);

            g2.setColor(BASE);
            g2.fillRect(0, 0, w, h);

            /*
             * Warm glow bleeding in from the upper-left,
             * fading through charcoal into deep navy.
             */
            float radius = (float) Math.max(h * 0.98, w * 0.58);
            Point2D center = new Point2D.Float(-w * 0.06f, h * 0.30f);

            RadialGradientPaint glow = new RadialGradientPaint(
                    center, radius,
                    new float[] {0f, 0.30f, 0.62f, 0.86f, 1f},
                    new Color[] {
                            new Color(96, 68, 56),
                            new Color(72, 59, 54),
                            new Color(44, 42, 44),
                            new Color(22, 24, 31),
                            BASE
                    });

            g2.setPaint(glow);
            g2.fillRect(0, 0, w, h);

            /*
             * Subtle film grain to hide gradient banding.
             */
            java.util.Random random = new java.util.Random(7);
            int[] row = new int[w];

            for (int y = 0; y < h; y++) {

                img.getRGB(0, y, w, 1, row, 0, w);

                for (int x = 0; x < w; x++) {

                    int n = random.nextInt(5) - 2;
                    int p = row[x];
                    int r = clamp(((p >> 16) & 0xff) + n);
                    int gr = clamp(((p >> 8) & 0xff) + n);
                    int b = clamp((p & 0xff) + n);
                    row[x] = (r << 16) | (gr << 8) | b;
                }

                img.setRGB(0, y, w, 1, row, 0, w);
            }

            g2.dispose();
            return img;
        }

        private static int clamp(int v) {
            return Math.max(0, Math.min(255, v));
        }
    }

    /*
     * BORDERS
     */

    /** Paints a translucent rounded "glass" fill behind a panel. */
    static final class GlassBorder extends AbstractBorder {

        private final Insets insets;
        private final Color fill;
        private final Color stroke;
        private final int radius;
        private final boolean sidebar;

        GlassBorder(Insets insets, Color fill, Color stroke, int radius, boolean sidebar) {
            this.insets = insets;
            this.fill = fill;
            this.stroke = stroke;
            this.radius = radius;
            this.sidebar = sidebar;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (sidebar) {

                g2.setColor(fill);
                g2.fillRect(x, y, w, h);
                g2.setColor(stroke);
                g2.fillRect(x + w - 1, y, 1, h);

            } else {

                RoundRectangle2D shape = new RoundRectangle2D.Float(
                        x + 0.5f, y + 0.5f, w - 1f, h - 1f, radius, radius);

                g2.setColor(new Color(0, 0, 0, 40));
                g2.fill(new RoundRectangle2D.Float(
                        x + 1f, y + 3f, w - 2f, h - 3f, radius, radius));

                g2.setColor(fill);
                g2.fill(shape);
                g2.setColor(stroke);
                g2.draw(shape);
            }

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets target) {
            target.set(insets.top, insets.left, insets.bottom, insets.right);
            return target;
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    /** Warm accent panel used for the login branding side. */
    static final class HeroBorder extends AbstractBorder {

        private final Insets insets;

        HeroBorder(Insets insets) {
            this.insets = insets;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setPaint(new GradientPaint(
                    x, y, new Color(222, 132, 88, 70),
                    x + w, y + h, new Color(120, 70, 60, 20)));

            g2.fill(new RoundRectangle2D.Float(x, y, w, h, 20, 20));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets target) {
            target.set(insets.top, insets.left, insets.bottom, insets.right);
            return target;
        }
    }

    /**
     * Rounded outline for inputs. Optionally fills the rounded area
     * (used by scroll panes and combos, whose content is painted by
     * children after the border). Shows an accent focus ring.
     */
    static final class FieldBorder extends AbstractBorder {

        boolean focused = false;
        private final int padV;
        private final int padH;
        private final Color fill;
        private final int arc;

        FieldBorder() {
            this(6, 14, null, 14);
        }

        FieldBorder(int padV, int padH, Color fill, int arc) {
            this.padV = padV;
            this.padH = padH;
            this.fill = fill;
            this.arc = arc;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (fill != null) {
                g2.setColor(fill);
                g2.fillRoundRect(x, y, w - 1, h - 1, arc, arc);
            }

            if (focused) {

                g2.setColor(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 55));
                g2.setStroke(new BasicStroke(3f));
                g2.drawRoundRect(x + 1, y + 1, w - 3, h - 3, arc, arc);
                g2.setStroke(new BasicStroke(1f));
                g2.setColor(ACCENT);

            } else {

                g2.setColor(FIELD_BORDER);
            }

            g2.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(padV, padH, padV, padH);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets target) {
            target.set(padV, padH, padV, padH);
            return target;
        }
    }

    /** Glass tile with a short accent bar, for dashboard stats. */
    static final class StatBorder extends AbstractBorder {

        private final Insets insets;

        StatBorder(Insets insets) {
            this.insets = insets;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setPaint(new GradientPaint(x, y, new Color(255, 255, 255, 20),
                    x, y + h, new Color(255, 255, 255, 8)));
            g2.fillRoundRect(x, y, w - 1, h - 1, 18, 18);
            g2.setColor(GLASS_STROKE);
            g2.drawRoundRect(x, y, w - 1, h - 1, 18, 18);
            g2.setColor(ACCENT);
            g2.fillRoundRect(x + insets.left, y + 12, 28, 3, 3, 3);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(insets.top, insets.left, insets.bottom, insets.right);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets target) {
            target.set(insets.top, insets.left, insets.bottom, insets.right);
            return target;
        }
    }

    /*
     * TEXT FIELDS (rounded fill + placeholder)
     */

    private static void paintFieldBackground(Graphics g, JTextComponent c) {

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c.isEditable() && c.isEnabled() ? FIELD : FIELD_READONLY);
        g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 14, 14);
        g2.dispose();
    }

    private static void paintPlaceholder(Graphics g, JTextComponent c) {

        Object hint = c.getClientProperty(PLACEHOLDER);

        if (!(hint instanceof String) || c.getDocument().getLength() > 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        applyTextHints(g2);
        g2.setFont(c.getFont());
        g2.setColor(new Color(130, 132, 142));

        Insets in = c.getInsets();
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth((String) hint);
        int x = in.left;

        if (c instanceof JTextField) {

            int align = ((JTextField) c).getHorizontalAlignment();

            if (align == JTextField.CENTER) {
                x = (c.getWidth() - textWidth) / 2;
            } else if (align == JTextField.RIGHT || align == JTextField.TRAILING) {
                x = c.getWidth() - in.right - textWidth;
            }
        }

        int y = (c.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString((String) hint, x, y);
        g2.dispose();
    }

    static final class RoundFieldUI extends BasicTextFieldUI {

        @Override
        protected void paintSafely(Graphics g) {
            paintFieldBackground(g, getComponent());
            super.paintSafely(g);
            paintPlaceholder(g, getComponent());
        }
    }

    static final class RoundPasswordUI extends BasicPasswordFieldUI {

        @Override
        protected void paintSafely(Graphics g) {
            paintFieldBackground(g, getComponent());
            super.paintSafely(g);
            paintPlaceholder(g, getComponent());
        }
    }

    static final class RoundFormattedUI extends BasicFormattedTextFieldUI {

        @Override
        protected void paintSafely(Graphics g) {
            paintFieldBackground(g, getComponent());
            super.paintSafely(g);
            paintPlaceholder(g, getComponent());
        }
    }

    /** Flat circular icon for JOptionPane dialogs. */
    static final class DialogIcon implements Icon, UIResource {

        private final String glyph;
        private final Color color;

        DialogIcon(String glyph, Color color) {
            this.glyph = glyph;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 45));
            g2.fillOval(x, y, 36, 36);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(x + 1, y + 1, 34, 34);

            applyTextHints(g2);
            g2.setFont(uiFont(Font.BOLD, 20));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(glyph,
                    x + (36 - fm.stringWidth(glyph)) / 2,
                    y + (36 - fm.getHeight()) / 2 + fm.getAscent());
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return 36;
        }

        @Override
        public int getIconHeight() {
            return 36;
        }
    }

    /** Rounded checkbox with an accent check mark. */
    static final class CheckIcon implements Icon {

        private final boolean checked;

        CheckIcon(boolean checked) {
            this.checked = checked;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int s = 16;

            if (checked) {

                g2.setColor(ACCENT);
                g2.fillRoundRect(x, y, s, s, 6, 6);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(x + 4, y + 8, x + 7, y + 11);
                g2.drawLine(x + 7, y + 11, x + 12, y + 5);

            } else {

                g2.setColor(FIELD);
                g2.fillRoundRect(x, y, s, s, 6, 6);
                g2.setColor(new Color(255, 255, 255, 70));
                g2.drawRoundRect(x, y, s - 1, s - 1, 6, 6);
            }

            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return 16;
        }

        @Override
        public int getIconHeight() {
            return 16;
        }
    }

    /** List rows with padding and a rounded accent selection. */
    static final class RowRenderer extends DefaultListCellRenderer {

        private boolean selected;

        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean focus) {

            super.getListCellRendererComponent(list, value, index, isSelected, false);

            selected = isSelected;
            setOpaque(false);
            setForeground(isSelected ? Color.WHITE : TEXT);
            setBorder(new EmptyBorder(9, 12, 9, 12));
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {

            if (selected) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT_DARK);
                g2.fillRoundRect(0, 1, getWidth(), getHeight() - 2, 10, 10);
                g2.dispose();
            }

            super.paintComponent(g);
        }
    }

    /** Uses the OS font smoothing (ClearType on Windows) when available. */
    static void applyTextHints(Graphics2D g2) {

        Object hints = Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

        if (hints instanceof Map) {
            g2.addRenderingHints((Map<?, ?>) hints);
        } else {
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    }

    /*
     * SIDEBAR ICONS (Segoe Fluent Icons / Segoe MDL2 Assets)
     */

    private static String iconFor(String text) {

        if (text == null) {
            return null;
        }

        switch (text.trim().toLowerCase()) {
            case "ask question": return "";
            case "view questions": return "";
            case "add answer": return "";
            case "unresolved questions": return "";
            case "mark solved": return "";
            case "view users": return "";
            case "invite user": return "";
            case "assign role": return "";
            case "remove role": return "";
            case "remove user": return "";
            case "review answers": return "";
            case "view reviews": return "";
            case "logout": return "";
            default: return null;
        }
    }

    /*
     * BUTTONS
     */

    static final class ModernButtonUI extends BasicButtonUI {

        @Override
        public void installUI(JComponent c) {

            super.installUI(c);

            AbstractButton b = (AbstractButton) c;
            b.putClientProperty(HOVER, 0f);

            /*
             * Smoothly animate hover in and out.
             */
            Timer timer = new Timer(15, null);

            timer.addActionListener(e -> {

                float target = b.getModel().isRollover() && b.isEnabled() ? 1f : 0f;
                float current = hoverOf(b);
                float next = current + Math.signum(target - current) * 0.12f;

                if (Math.abs(target - next) < 0.12f) {
                    next = target;
                    timer.stop();
                }

                b.putClientProperty(HOVER, next);
                b.repaint();
            });

            b.getModel().addChangeListener(e -> {

                float target = b.getModel().isRollover() ? 1f : 0f;

                if (hoverOf(b) != target && !timer.isRunning()) {
                    timer.start();
                }
            });
        }

        private static float hoverOf(AbstractButton b) {

            Object v = b.getClientProperty(HOVER);
            return v instanceof Float ? (Float) v : 0f;
        }

        private static Color mix(Color a, Color b, float t) {

            return new Color(
                    Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                    Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                    Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t),
                    Math.round(a.getAlpha() + (b.getAlpha() - a.getAlpha()) * t));
        }

        @Override
        public void paint(Graphics g, JComponent c) {

            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();
            String kind = kindOf(b);
            float t = b.isEnabled() ? hoverOf(b) : 0f;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = c.getWidth();
            int h = c.getHeight();
            int arc = 14;

            boolean pressed = model.isArmed() && model.isPressed();

            switch (kind) {

                case "primary": {

                    Color top = mix(ACCENT, new Color(240, 160, 115), t);
                    Color bottom = mix(ACCENT_DARK, new Color(204, 114, 76), t);

                    if (pressed) {
                        top = ACCENT_DARK;
                        bottom = ACCENT_DARK.darker();
                    }

                    if (!b.isEnabled()) {
                        top = new Color(90, 80, 76);
                        bottom = new Color(70, 64, 62);
                    }

                    // soft glow that grows on hover
                    g2.setColor(new Color(222, 132, 88, Math.round(28 * t)));
                    g2.fillRoundRect(0, 0, w, h, arc + 6, arc + 6);
                    g2.setColor(new Color(0, 0, 0, 55));
                    g2.fillRoundRect(1, 3, w - 2, h - 3, arc, arc);
                    g2.setPaint(new GradientPaint(0, 0, top, 0, h, bottom));
                    g2.fillRoundRect(1, 1, w - 2, h - 4, arc, arc);

                    // top highlight
                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.drawLine(arc / 2, 2, w - arc / 2, 2);
                    break;
                }

                case "nav": {

                    float a = pressed ? 1.3f : t;

                    if (a > 0f) {

                        g2.setColor(new Color(255, 255, 255, Math.min(255, Math.round(18 * a))));
                        g2.fillRoundRect(0, 0, w, h, arc, arc);
                        g2.setColor(ACCENT);
                        int barH = Math.round(h * 0.5f * Math.min(1f, a));
                        g2.fillRoundRect(0, (h - barH) / 2, 4, barH, 4, 4);
                    }

                    if (iconFamily != null) {

                        String icon = iconFor(b.getText());

                        if (icon != null) {

                            Font iconFont = new Font(iconFamily, Font.PLAIN, 17);
                            applyTextHints(g2);
                            g2.setFont(iconFont);
                            g2.setColor(mix(MUTED, ACCENT_TEXT, t));
                            FontMetrics fm = g2.getFontMetrics();
                            g2.drawString(icon, 18, (h - fm.getHeight()) / 2 + fm.getAscent() + 2);
                        }
                    }

                    break;
                }

                case "link":
                    break;

                default: {

                    int fillA = pressed ? 36 : Math.round(12 + 14 * t);
                    int strokeA = Math.round(36 + 34 * t);

                    g2.setColor(new Color(255, 255, 255, fillA));
                    g2.fillRoundRect(0, 0, w - 1, h - 1, arc, arc);
                    g2.setColor(new Color(255, 255, 255, strokeA));
                    g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
                    break;
                }
            }

            g2.dispose();

            super.paint(g, c);
        }

        @Override
        protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {

            Graphics2D g2 = (Graphics2D) g.create();
            applyTextHints(g2);

            String kind = kindOf(b);
            float t = hoverOf(b);
            Color color;

            if (!b.isEnabled()) {
                color = MUTED;
            } else if (kind.equals("primary")) {
                color = Color.WHITE;
            } else if (kind.equals("link")) {
                color = mix(ACCENT_TEXT, new Color(255, 205, 170), t);
            } else if (kind.equals("nav")) {
                color = mix(new Color(206, 202, 198), Color.WHITE, t);
            } else {
                color = TEXT;
            }

            FontMetrics fm = g2.getFontMetrics(b.getFont());
            g2.setFont(b.getFont());
            g2.setColor(color);
            g2.drawString(text, textRect.x, textRect.y + fm.getAscent());

            if (kind.equals("link") && t > 0.5f) {

                int baseline = textRect.y + fm.getAscent() + 2;
                g2.fillRect(textRect.x, baseline, textRect.width, 1);
            }

            g2.dispose();
        }

        @Override
        protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect,
                                  Rectangle textRect, Rectangle iconRect) {
        }

        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b) {
        }

        private static String kindOf(AbstractButton b) {

            Object explicit = b.getClientProperty(KIND);

            if (explicit instanceof String) {
                return (String) explicit;
            }

            if (b instanceof JButton && ((JButton) b).isDefaultButton()) {
                return "primary";
            }

            Color bg = b.getBackground();

            if (bg == null || bg instanceof UIResource) {
                return "secondary";
            }

            float[] hsb = Color.RGBtoHSB(bg.getRed(), bg.getGreen(), bg.getBlue(), null);

            if (hsb[2] > 0.85f && hsb[1] < 0.15f) {

                Color fg = b.getForeground();
                float[] fgHsb = Color.RGBtoHSB(fg.getRed(), fg.getGreen(), fg.getBlue(), null);

                return fgHsb[1] > 0.3f && fgHsb[2] > 0.5f ? "link" : "secondary";
            }

            if (hsb[1] > 0.35f && hsb[2] > 0.45f) {
                return "primary";
            }

            return "secondary";
        }
    }

    /*
     * SCROLLBARS
     */

    static final class SlimScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            trackColor = new Color(0, 0, 0, 0);
            thumbColor = new Color(255, 255, 255, 55);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return zeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return zeroButton();
        }

        private static JButton zeroButton() {

            JButton b = new JButton() {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(0, 0);
                }
            };

            b.setBorder(BorderFactory.createEmptyBorder());
            return b;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle r) {

            if (r.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isThumbRollover()
                    ? new Color(255, 255, 255, 95) : thumbColor);
            g2.fillRoundRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4, 8, 8);
            g2.dispose();
        }
    }

    /*
     * COMBO BOX
     */

    static final class FlatComboUI extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {

            JButton arrow = new JButton() {

                @Override
                public void paint(Graphics g) {

                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(MUTED);
                    g2.setStroke(new BasicStroke(1.8f,
                            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                    int cx = getWidth() / 2;
                    int cy = getHeight() / 2;

                    g2.drawLine(cx - 4, cy - 2, cx, cy + 2);
                    g2.drawLine(cx, cy + 2, cx + 4, cy - 2);
                    g2.dispose();
                }
            };

            arrow.setOpaque(false);
            arrow.setBorder(BorderFactory.createEmptyBorder());
            arrow.setPreferredSize(new Dimension(28, 28));
            return arrow;
        }

        @Override
        public void paint(Graphics g, JComponent c) {

            // Rounded fill first; the border (stroke only) is painted afterwards.
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(FIELD);
            g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 14, 14);
            g2.dispose();

            super.paint(g, c);
        }

        @Override
        public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
            // No solid selection block when focused; the value stays readable.
            super.paintCurrentValue(g, bounds, false);
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            g.setColor(FIELD);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        @Override
        protected ListCellRenderer<Object> createRenderer() {

            return new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(
                        JList<?> list, Object value, int index,
                        boolean selected, boolean focus) {

                    super.getListCellRendererComponent(list, value, index, selected, focus);

                    setBorder(new EmptyBorder(6, 8, 6, 8));
                    setBackground(selected && index >= 0 ? ACCENT_DARK : FIELD);
                    setForeground(selected && index >= 0 ? Color.WHITE : TEXT);
                    return this;
                }
            };
        }
    }
}
