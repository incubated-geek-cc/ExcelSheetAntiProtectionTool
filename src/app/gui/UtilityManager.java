package app.gui;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class UtilityManager {
    //  Static: means the value is the same for every instance of the class
    private static final String LOGGER_NAME = MethodHandles.lookup().lookupClass().getName();
    private static final Logger LOGGER = Logger.getLogger(LOGGER_NAME);
    
    private final JTextArea LOG_TEXT_AREA;
    private final JScrollPane JSCROLL_PANEL_OUTPUT_LOGS;
    
    public UtilityManager(JTextArea LOG_TEXT_AREA, JScrollPane JSCROLL_PANEL_OUTPUT_LOGS) {
        this.LOG_TEXT_AREA = LOG_TEXT_AREA;
        this.JSCROLL_PANEL_OUTPUT_LOGS = JSCROLL_PANEL_OUTPUT_LOGS;
        JSCROLL_PANEL_OUTPUT_LOGS.setHorizontalScrollBar(null);
        DefaultCaret caret = (DefaultCaret) LOG_TEXT_AREA.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.ALL);
        LOGGER.addHandler(new TextAreaHandler(new TextAreaOutputStream(LOG_TEXT_AREA)));
    }
     // =========================== Log Outputs ==================================================================
    public Logger getLogger() {
        return LOGGER;
    }
    public void updateLogs() {
        JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar().setValue(JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar().getMaximum());
        JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar().paint(JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar().getGraphics());
        LOG_TEXT_AREA.scrollRectToVisible(LOG_TEXT_AREA.getVisibleRect());
        LOG_TEXT_AREA.paint(LOG_TEXT_AREA.getGraphics());
    }
    private static void addTextToOutputLogs(String logString) {
        LOGGER.info(() -> logString);
    }
    public void outputConsoleLogsBreakline(String consoleCaption) {
        String logString = "";
        int charLimit = 90;
        if (consoleCaption.length() > charLimit) {
            logString = consoleCaption.substring(0, charLimit - 4) + "…";
        } else {
            String result = "";
            if (consoleCaption.isEmpty()) {
                for (int i = 0; i < charLimit; i++) {
                    result += "=";
                }
                logString = result;
            } else {
                charLimit = (charLimit - consoleCaption.length() - 1);
                for (int i = 0; i < charLimit; i++) {
                    result += "-";
                }
                logString = consoleCaption + " " + result;
            }
        }
        logString = logString + "\n";
        addTextToOutputLogs(logString);
    }
}
