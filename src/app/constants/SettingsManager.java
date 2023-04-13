package app.constants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

public class SettingsManager {
    private static final String APP_ICON_URI = "iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAAXNSR0IArs4c6QAAAgJJREFUSEvtls9rE0EUxz/bldS42vSgiyhiAy5eBCOCQujB5uTN+B8E/Ae86EVq0oPQiiDoSUFqbhFEe/AHXhJFKlTE9mAMirKRILVrwSVt0ybZ3chENqbJ0m4vOWgfLOy+9+Z9Zr4z81iJHpjUAwbbkC2p/P/JNQgc99DolR/d/MgVAXKAAHXaFHB+M5AfyJSmaefi8fi6WoZhkE6nhW8EeLkRqBMiZnsTGGobFIlGo4OappHNZptuVVWJxWJkMhlKpVIREI9rJjAGzLmOdogA5I7JgcjZwO51ExNFFUVB1/WmX7yHw+E/3ysryEjk7VozVnTqzFhrAnTChbdDEiGpb/Jt6DChPnkzmZtxy3GYtdewOrJHK4t8sGtCy4QItUNS0R3B5KOBg74AIqns2OTtalf+g+oSmdqSOHlnuiDD8s7kuLKPwOmTWGOX+f75C7vGb6HO/2wVqj6+T800+Xb7LupcntfB7lXPlk1eLP/yhozIweSdPfth+BT1iasUCgX2jk5wYH6xBalPP6H8Y4HitRsMvf/IO9npWsnzismz1bI3xJWrcfQIzsN7TGdzRK5cZ2C50ipkv3nKV2OB+qUUhz7pW5erJ3uyDenc+Y2OcEJBmrzQH6Jf8tPS/pauNhoYDbvlmLFW0R3L8zKKpJR7gXzfSO9E0csuAqK9/EM/Er8BjC7lGlROE4wAAAAASUVORK5CYII=";
    
    private static final Color WHITE = new Color(255, 255, 255); // #ffffff | White
    private static final Color LIGHT = new Color(248, 249, 250); // #f8f9fa | Light
    
    private static final Color GRAY = new Color(108, 117, 125); // #6c757d | Gray
    private static final Color MEDIUM_GRAY = new Color(85, 85, 85); // #555555 | Medium Gray
    private static final Color GRAY_DARK = new Color(52, 58, 64); // #343a40 | Gray-Dark
    private static final Color DARK = new Color(13,13,14); // #343a40 | Dark
    
    private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder();
    private static final Border PANEL_PADDING_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 2);
    private static final Border RAISED_PANEL_MEDIUM_GRAY_BORDER = BorderFactory.createBevelBorder(BevelBorder.RAISED, MEDIUM_GRAY, MEDIUM_GRAY,MEDIUM_GRAY,MEDIUM_GRAY);
    private static final Border LOWERED_PANEL_GRAY_DARK_BORDER = new SoftBevelBorder(BevelBorder.LOWERED, GRAY_DARK, GRAY_DARK, GRAY_DARK, GRAY_DARK);
    private static final Border LOWERED_PANEL_GRAY_BORDER = new SoftBevelBorder(BevelBorder.LOWERED, GRAY, GRAY, GRAY, GRAY);
    
    private static final Font Consolas = new Font("Consolas", Font.PLAIN, 11);
    
    private final Cursor POINTER_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    private File DEFAULT_DIR = new File(System.getProperty("user.home"));
    
    public void setDefaultDir(File defaultDir) {
        this.DEFAULT_DIR=defaultDir;
    }
    public File getDefaultDir() {
        return DEFAULT_DIR;
    }
    
    public String getAppIconURI() {
        return APP_ICON_URI;
    }
    public Color getWhiteColor() {
        return WHITE;
    }
    public Color getLightColor() {
        return LIGHT;
    }
    public Color getGrayColor() {
        return GRAY;
    }
    public Color getMediumGrayColor() {
        return MEDIUM_GRAY;
    }
    public Color getGrayDarkColor() {
        return GRAY_DARK;
    }
    public Color getDarkColor() {
        return DARK;
    }
    
    public Border getEmptyBorder() {
        return EMPTY_BORDER;
    }
    public Border getPanelPaddingBorder() {
        return PANEL_PADDING_BORDER;
    }
    public Border getRaisedMediumGrayBorder() {
        return RAISED_PANEL_MEDIUM_GRAY_BORDER;
    }
    
    public Border getLoweredPanelGrayDarkBorder() {
        return LOWERED_PANEL_GRAY_DARK_BORDER;
    }
    public Border getLoweredPanelGrayBorder() {
        return LOWERED_PANEL_GRAY_BORDER;
    }
    
    
    public Font getConsolasFont() {
        return Consolas;
    }
    public Font getPlainFont(int size) {
        return new Font("Segoe UI Variable", Font.PLAIN, size);
    }
    public Font getBoldFont(int size) {
        return new Font("Segoe UI Variable", Font.BOLD, size);
    }
    public Font getItalicFont(int size) {
        return new Font("Segoe UI Variable", Font.ITALIC, size);
    }
    public Font getType1Font(int size) {
        return new Font("Segoe UI Variable", Font.TYPE1_FONT, size);
    }
    public Font getTrueTypeFont(int size) {
        return new Font("Segoe UI Variable", Font.TRUETYPE_FONT, size);
    }
    public Font getMonospacedFont(int size) {
        return new Font(Font.MONOSPACED, Font.PLAIN, size);
    }
    public Cursor getPointerCursor() {
        return POINTER_CURSOR;
    }
}
