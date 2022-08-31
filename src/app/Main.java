package app;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main extends JPanel {
    
    private static JLabel jLabelTipsNInfoTitle;
    private static JLabel jLabelUploadListTitle;
    private static JLabel jLabelOutputFileLogsTitle;
    private static JLabel jLabelEndNote;
    private static JLabel jLabelHyperlink;
    
    private static JFrame APP_FRAME;
    private static final String LOGGER_NAME = MethodHandles.lookup().lookupClass().getName();
    private static final Logger LOGGER = Logger.getLogger(LOGGER_NAME);

    private static final String PROFILE_LINK = "https://medium.com/@geek-cc";
    
    private static final String TITLE_COLON = ":";
    private static final String BULLET_POINT = "  ⮞ ";
    
    // OUTPUT LOGS
    private static JTextArea LOG_TEXT_AREA;
    private static JScrollPane JSCROLL_PANEL_OUTPUT_LOGS;
    
    private JFileChooser FILE_CHOOSER;
    private JFileChooser saveFileChooser;
    
    private static JLabel USER_NOTICE_1;
    private static JLabel USER_NOTICE_2;
    private static JLabel USER_NOTICE_3;
    
    private static JButton jButtonSelectInputFiles;
    private static JButton jButtonResetAll;
    private static JButton jButtonRemoveSelectedFiles;
    private static JButton jButtonRunProcess;
    
    // LIST OF FILE ITEMS - INPUT FILES TO PROCESS INTO OUTPUT
    private static DefaultListModel jListInputFilesSelectedModel = new DefaultListModel<>();
    private static JList<String> jListInputFilesSelected;
    private static JScrollPane jScrollPane1FileListItems;
    
    private static ArrayList<File> INPUT_FILES = new ArrayList<File>();
    private static ArrayList<File> OUTPUT_FILES = new ArrayList<File>();
    private static File outputArchiveZip = null;
    
    private static Font labelFont = new Font("Arial Nova LightFreesiaUPC", Font.BOLD, 12);
    private static Font logFont = new Font("Consolas", Font.ROMAN_BASELINE, 11);
    private static Font btnFont = new Font("Arial Nova LightFreesiaUPC", Font.PLAIN, 11);    
    private static Font tipFont = new Font("Arial Nova LightFreesiaUPC", Font.TYPE1_FONT, 11);
    
    private static Border etchedBorder = new EtchedBorder(EtchedBorder.RAISED);
    
    public Main() {
        LOGGER.setUseParentHandlers(false);
        LOGGER.setLevel(Level.ALL);
        LOGGER.addHandler(new TextAreaHandler(new TextAreaOutputStream(LOG_TEXT_AREA)));
        
        // OUTPUT LOGS
        LOG_TEXT_AREA.setEditable(false);
        LOG_TEXT_AREA.setWrapStyleWord(true);
        JSCROLL_PANEL_OUTPUT_LOGS = new JScrollPane(LOG_TEXT_AREA);
        JSCROLL_PANEL_OUTPUT_LOGS.setBorder(etchedBorder);
        JSCROLL_PANEL_OUTPUT_LOGS.setHorizontalScrollBar(null);
        
        DefaultCaret caret = (DefaultCaret) LOG_TEXT_AREA.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        // INPUT FILES SELECTED
        jLabelOutputFileLogsTitle = new JLabel("📋 Output Logs"+TITLE_COLON);
        jLabelOutputFileLogsTitle.setFont(labelFont);
        
        jLabelUploadListTitle = new JLabel("📤 List Of File Uploads"+TITLE_COLON);
        jLabelUploadListTitle.setFont(labelFont);
        
        jLabelTipsNInfoTitle = new JLabel("🗦💡🗧Tips & Guidelines"+TITLE_COLON);
        jLabelTipsNInfoTitle.setFont(labelFont);
        
        jListInputFilesSelected = new JList<>(jListInputFilesSelectedModel);
        jListInputFilesSelected.setFont(labelFont);
        jScrollPane1FileListItems = new JScrollPane(jListInputFilesSelected);
        jScrollPane1FileListItems.setBorder(etchedBorder);
        
        // LABEL NOTE
        USER_NOTICE_1 = new JLabel(BULLET_POINT+"Select multiple files to process ≥1 file in a single upload");
        USER_NOTICE_1.setFont(tipFont);
        
        USER_NOTICE_2 = new JLabel(BULLET_POINT+"Only files with extension '.xls' and '.xlsx' can be processed");
        USER_NOTICE_2.setFont(tipFont);
        
        USER_NOTICE_3 = new JLabel(BULLET_POINT+"All processed files are archived into a single ZIP file for output");
        USER_NOTICE_3.setFont(tipFont);
        
        jLabelEndNote = new JLabel("📌 Created by ξ(🎀˶❛◡❛) 🇹🇭🇪 🇷🇮🇧🇧🇴🇳 🇬🇮🇷🇱— More at"+TITLE_COLON);
        jLabelEndNote.putClientProperty("FlatLaf.styleClass", "default");
        jLabelEndNote.setForeground(Color.WHITE);
        
        
        jLabelHyperlink = new JLabel(" "+PROFILE_LINK);
        jLabelHyperlink.putClientProperty("FlatLaf.styleClass", "default");
        jLabelHyperlink.setForeground(new Color(207, 120, 149));
        jLabelHyperlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Border btnBorder = BorderFactory.createEtchedBorder();
        jLabelHyperlink.setBorder(btnBorder);
        
        // ACTIONABLE BUTTONS
        jButtonSelectInputFiles = new JButton("➕ Add File(s)");
        jButtonSelectInputFiles.setFont(btnFont);
        jButtonSelectInputFiles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        jButtonRemoveSelectedFiles = new JButton("✘ Remove File(s)");
        jButtonRemoveSelectedFiles.setFont(btnFont);
        jButtonRemoveSelectedFiles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        jButtonResetAll = new JButton("🔄 Reset All");
        jButtonResetAll.setFont(btnFont);
        jButtonResetAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        jButtonRunProcess = new JButton("Run »");
        jButtonRunProcess.setFont(btnFont);
        jButtonRunProcess.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonRunProcess.setEnabled(false);
        
        // ADD COMPONENTS
        add(USER_NOTICE_1);
        add(USER_NOTICE_2);
        add(USER_NOTICE_3);
        
        add(jButtonSelectInputFiles);
        add(jButtonRemoveSelectedFiles);
        add(jScrollPane1FileListItems);
        add(jButtonRunProcess);
        add(jButtonResetAll);
        add(jLabelUploadListTitle);
        add(jLabelTipsNInfoTitle);
        add(jLabelOutputFileLogsTitle);
        add(jLabelEndNote);
        add(jLabelHyperlink);
        add(JSCROLL_PANEL_OUTPUT_LOGS);
       

        //set components properties
        jButtonSelectInputFiles.setToolTipText("📖 Appends selected files to list for processing");
        jButtonRemoveSelectedFiles.setToolTipText("📖 Removes any file(s) selected in the list");
        jButtonResetAll.setToolTipText("📖 Resets application to its default state");
        jListInputFilesSelected.setToolTipText("📖 Displays list of selected file(s) for processing");
        
        //adjust size and set layout
        setPreferredSize (new Dimension (629, 365));
        setLayout(null);
        
        //add components
        add(jButtonRemoveSelectedFiles);
        add(jButtonResetAll);
        add(jScrollPane1FileListItems);
        add(jButtonRunProcess);
        add(jLabelEndNote);
        add(jLabelHyperlink);
        
        // SET COMPONENT BOUNDS (for Absolute Positioning only)
        jLabelOutputFileLogsTitle.setBounds(20, 285+5, 775, 30);
        JSCROLL_PANEL_OUTPUT_LOGS.setBounds(20, 315, 775, 220);
        
        jLabelEndNote.setBounds(20, 315+220+5, 270, 30);
        jLabelHyperlink.setBounds(20+265, 315+220+5, 180, 30);
        
        
        int btnHeight=30;
        int btnWidth=116;
        
        int gapBetweenBtn=5;
        
        int startTopBtnXPos=120;
        int topmostVerticalMargin=15;
        
       
        jButtonSelectInputFiles.setBounds(startTopBtnXPos+3*btnWidth+3*gapBetweenBtn+25+10+39, topmostVerticalMargin, btnWidth, btnHeight);
        jButtonRemoveSelectedFiles.setBounds(startTopBtnXPos+4*btnWidth+4*gapBetweenBtn+25+10+39, topmostVerticalMargin, btnWidth, btnHeight);
        
        int startBottomBtnXPos=20;
        int bottomMostVerticalMargin=215;
        
        jLabelUploadListTitle.setBounds(startBottomBtnXPos+(775/2), topmostVerticalMargin+10, (775/2)-btnWidth-btnWidth, btnHeight);
        jLabelTipsNInfoTitle.setBounds(startBottomBtnXPos, topmostVerticalMargin+10, (775/2)-btnWidth-btnWidth, btnHeight);
        
        jButtonRunProcess.setBounds(startBottomBtnXPos, bottomMostVerticalMargin, btnWidth, btnHeight);
        jButtonResetAll.setBounds(startTopBtnXPos+4*btnWidth+4*gapBetweenBtn+btnWidth-45, bottomMostVerticalMargin, btnWidth, btnHeight);
        
        USER_NOTICE_1.setBounds(startBottomBtnXPos, 50+gapBetweenBtn, (startTopBtnXPos+4*btnWidth+4*gapBetweenBtn)-startBottomBtnXPos+gapBetweenBtn, btnHeight);
        USER_NOTICE_2.setBounds(startBottomBtnXPos, 50+btnHeight+gapBetweenBtn, (startTopBtnXPos+4*btnWidth+4*gapBetweenBtn)-startBottomBtnXPos+gapBetweenBtn, btnHeight);
        USER_NOTICE_3.setBounds(startBottomBtnXPos, 50+btnHeight+btnHeight+gapBetweenBtn, (startTopBtnXPos+4*btnWidth+4*gapBetweenBtn)-startBottomBtnXPos+gapBetweenBtn, btnHeight);
        
        jScrollPane1FileListItems.setBounds(startBottomBtnXPos+(775/2), 50, 775/2, 160); 
        
        jButtonSelectInputFiles.addActionListener((ActionEvent evt) -> {
            selectInputFilesAction(evt);
        });
        jButtonResetAll.addActionListener((ActionEvent evt) -> {
            resetAllAction(evt);
        });
        jButtonRunProcess.addActionListener((ActionEvent evt) -> {
            runAntiProtectionAction(evt);
        });
        jButtonRemoveSelectedFiles.addActionListener((ActionEvent evt) -> {
            removeListItemAction(evt);
            if (INPUT_FILES.isEmpty()) {
                jButtonRunProcess.setEnabled(false);
            }
        });
        
        jLabelHyperlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(PROFILE_LINK));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    
    private void selectInputFilesAction(ActionEvent e) {
        FILE_CHOOSER = new JFileChooser();
        FILE_CHOOSER.setDialogTitle("Select Input File(s)");
        FILE_CHOOSER.setMultiSelectionEnabled(true);
        FILE_CHOOSER.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (.xls,.xlsx)", "xls", "xlsx");
        FILE_CHOOSER.addChoosableFileFilter(filter);
        
        int option = FILE_CHOOSER.showOpenDialog(APP_FRAME);
        if (option == JFileChooser.APPROVE_OPTION) {
            jListInputFilesSelectedModel = (DefaultListModel) jListInputFilesSelected.getModel();
            File[] selectedFiles = FILE_CHOOSER.getSelectedFiles();
            for (File selectedFile : selectedFiles) { // FOR-EACH FILE
                String selectedFilePath = selectedFile.getAbsolutePath();
                jListInputFilesSelectedModel.addElement(selectedFilePath);
                INPUT_FILES.add(selectedFile);
            }
            if (INPUT_FILES.size() > 0) {
                jButtonRunProcess.setEnabled(true);
            }
        }
    }
    
    private void removeListItemAction(ActionEvent e) {
        jListInputFilesSelectedModel = (DefaultListModel) jListInputFilesSelected.getModel();
        int[] selectedInputFiles = jListInputFilesSelected.getSelectedIndices();
        for (int i : selectedInputFiles) {
            jListInputFilesSelectedModel.remove(i);
            INPUT_FILES.remove(i);
        }
    }
    
    private void resetAllAction(ActionEvent e) {
        jButtonSelectInputFiles.setEnabled(true);
        jButtonRunProcess.setEnabled(false);

        jListInputFilesSelectedModel.clear();
        INPUT_FILES.clear();
        OUTPUT_FILES.clear();
        
        LOGGER.info(() -> "Reset application state.");
        outputConsoleLogsBreakline("");
        updateLogs();
    }
    
    // LOGIC OF FILE PROCESSING HERE:
    // UTILITY FUNCTION FOR PROCESS
    private void runAntiProtectionAction(ActionEvent e) {
        jButtonSelectInputFiles.setEnabled(false);
        jButtonResetAll.setEnabled(false);
        jButtonRemoveSelectedFiles.setEnabled(false);
        
        // ================================================= READ IN FILES ================================
        outputConsoleLogsBreakline("");
        LOGGER.info(() -> "Processing Excel Workbooks:");
        outputConsoleLogsBreakline("");
        updateLogs();
        
        for (File inputFile : INPUT_FILES) { // for-each INPUT EXCEL FILE
            unprotectExcelFile(inputFile);
        }
        LOGGER.info(() -> "Excel Workbooks completed.");
        LOGGER.info(() -> "Proceeding to generate ZIP archieve.");
        outputConsoleLogsBreakline("");
        updateLogs();

        outputArchiveZip = new File("processedFileOutputs_" + getCurrentTimeStamp() + ".zip");
        try (FileOutputStream fos = new FileOutputStream(outputArchiveZip)) {
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (File fileToZip : OUTPUT_FILES) { // for-each INPUT EXCEL FILE
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
                
                outputConsoleLogsBreakline(fileToZip.getName() + " has been added into output ZIP archive.");
                updateLogs();
            }
            zipOut.close();
            
            saveFileChooser = new JFileChooser();
            saveFileChooser.setDialogTitle("Save Output As...");
            saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

            saveFileChooser.setSelectedFile(outputArchiveZip);
            saveFileChooser.setFileFilter(new FileNameExtensionFilter("ZIP (*.zip)", "zip"));

            int option = saveFileChooser.showSaveDialog(APP_FRAME);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = saveFileChooser.getSelectedFile();
                if (selectedFile != null) {
                    if (!selectedFile.getName().toLowerCase().endsWith(".zip")) {
                        selectedFile = new File(selectedFile.getParentFile(), selectedFile.getName() + ".zip");
                    }
                    copy(outputArchiveZip, selectedFile);
                    outputConsoleLogsBreakline("");
                    outputConsoleLogsBreakline("Overall Status: Success");
                    outputConsoleLogsBreakline("Output ZIP arhive has been saved as: " +  outputArchiveZip.getName());
                    outputConsoleLogsBreakline("");
                    updateLogs();
                    if(!outputArchiveZip.getAbsolutePath().equalsIgnoreCase(selectedFile.getAbsolutePath())) {
                        outputArchiveZip.delete();
                    }
                    Desktop.getDesktop().open(selectedFile);
                }
            }
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            updateLogs();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            updateLogs();
        } finally {
            jButtonRunProcess.setEnabled(true);
            jButtonRemoveSelectedFiles.setEnabled(true);
            jButtonResetAll.setEnabled(true);
            jButtonSelectInputFiles.setEnabled(true);

            for(File f:OUTPUT_FILES) {
                f.delete();
            }
        }
    }
    
    private void copy(File src, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);
            // buffer size 1K
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }
    
    private void unprotectExcelFile(File targetExcelFile) {
        try {           
            String wbName = targetExcelFile.getName();
            String fileExt = wbName.substring(wbName.lastIndexOf("."));
            String wbTitle = wbName.substring(0,wbName.lastIndexOf("."));
            
            outputConsoleLogsBreakline("");
            LOGGER.info(() -> "Currently processing: ");
            LOGGER.info(() -> "Excel Workbook Name: "+wbTitle);
            LOGGER.info(() -> "File Extension: "+fileExt);
            
            Workbook workbook = null;
            if(fileExt.equals(".xlsx")) {
                workbook=new XSSFWorkbook(new FileInputStream(targetExcelFile));
            } else if(fileExt.equals(".xls")) {
                workbook=new HSSFWorkbook(new FileInputStream(targetExcelFile));
            }
            int noOfSheets = workbook.getNumberOfSheets();
            LOGGER.info(() -> "Total No. Of Spreadsheets: "+noOfSheets);
            outputConsoleLogsBreakline("");
            updateLogs();
            
            outputConsoleLogsBreakline("");
            LOGGER.info(() -> "Processing Excel Spreadsheets:");
            outputConsoleLogsBreakline("");
            updateLogs();
            for (int s = 0; s < noOfSheets; s++) {
                Sheet sheet = workbook.getSheetAt(s);
                final String sheetName=sheet.getSheetName();
                final int sheetIndex = s;
                workbook.setSheetHidden(s, false);
                sheet.setFitToPage(true);
                sheet.setZoom(70);
                boolean isProtected=sheet.getProtect();
                
                LOGGER.info(() -> "Sheet Index: "+sheetIndex);
                LOGGER.info(() -> "Sheet Name: "+sheetName);
                LOGGER.info(() -> "Sheet Protection Status: "+isProtected);
                outputConsoleLogsBreakline("");
                updateLogs();
                
                if(isProtected) {
                    sheet.protectSheet(null);
                    LOGGER.info(() -> "Sheet Protection has been set to null.");
                    outputConsoleLogsBreakline("");
                    updateLogs();
                }
                LOGGER.info(() -> "Status: Refreshing Excel Formulas.");
                outputConsoleLogsBreakline("");
                updateLogs();
                try {
                    XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                    outputConsoleLogsBreakline("");
                }
                LOGGER.info(() -> "Workbook formulas are refreshed.");
                outputConsoleLogsBreakline("");
                updateLogs();
            }
            
            File outputExcelFile = new File(wbTitle+"_(Unprotected)"+fileExt);
            try ( // Output new unprotected file
                FileOutputStream out = new FileOutputStream(outputExcelFile)) {
                workbook.write(out);
                out.flush();
                out.close();
            }
            OUTPUT_FILES.add(outputExcelFile);   
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            outputConsoleLogsBreakline("");
            updateLogs();
        }
    }
    
    private static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("(dd-MMM-yyyy_hhmmaa)");
        Date date = new Date();
        String timestamp = sdf.format(date);
        return timestamp;
    }
    
    private static void updateLogs() {
        JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar().setValue(JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar().getMaximum());
        JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar().paint(JSCROLL_PANEL_OUTPUT_LOGS.getVerticalScrollBar().getGraphics());
        LOG_TEXT_AREA.scrollRectToVisible(LOG_TEXT_AREA.getVisibleRect());
        LOG_TEXT_AREA.paint(LOG_TEXT_AREA.getGraphics());
    }
    
    private static void addTextToOutputLogs(String logString) {
        LOGGER.info(() -> logString);
    }

    public static void outputConsoleLogsBreakline(String consoleCaption) {
        String logString = "";

        int charLimit = 180;
        if (consoleCaption.length() > charLimit) {
            logString = consoleCaption.substring(0, charLimit - 4) + " ...";
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
    
    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String iconURI = "iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAAXNSR0IArs4c6QAAAmhJREFUSEvdlj1oU1EUx391KFKFVMyS+E2F0MlKHEQHU3SNKDiUTA0SIg6i4qSFJOAg2tCKS5Klig2hS7UkFApCFB0UEmtRKQQTTcGopNiU9EMrBTm3ac3Xs4naDN7lvfvefed3/ve8/3mviQaMpgYwqAXSChwALMAHYAJ4VU9yv4NI8D6gWwKazWYSiQT5fF6mArMDj2uBaUE6gKjJZGp1OBxYLCJiZWQyGQKBAJFIRKaXgP71QNUgomDcarXudblcxONxQqGQUmEwGJQim81GNBrF4/FIfFEjSckWyvltIFcMrgZxG41GVzAYVNl6vd6KRE0mE36/n+GhAb6l7rFbD7MLMPQMprIK0Flct2qQnNvt1knWTqdTcyfsXce5fvItzcufefgCDrXBTj1cCEDoqarZwVVF5RCRPR4Oh9W+y1FrjFyFI+0rd9uc0GuH04dhdh7Ml5WytXqVQ6TC0VgsplRIPbRG9r52uQtqRoBTsuqPILoWeOfXhvQMgn+MJwVvVUAuijdqUfKyD3bpq4M6e+BNGnn13OVK1FadObaFG7138Pl8JJNJzXQ79ixx/oQyZskIjMG1QXVpX8G0JUrcR9txPerfz2adYT1/qfs/FnPMZVMsf59X81sP4OawOpVucHc1SHFN6oZIkIWvaXKfplS8HWfVoaILbAREjFjS0/4asjjzkZlMqljJv4cszU0znZ7cWIgUP5t6/R9AZLu+vJ9kNA7nfEqQNMaSL2dx4bt1LQxc6drOpuatNflEFum3wfOJNAOjlf6o5hO5Jm3g12ewZpRaKOZbM2Dxo7X8SNSHqrK6IZCfN5DxGp+5ZtgAAAAASUVORK5CYII=";
                    APP_FRAME = new JFrame(" 🚫🔒 𝗘𝘅𝗰𝗲𝗹 𝗔𝗻𝘁𝗶-𝗣𝗿𝗼𝘁𝗲𝗰𝘁𝗶𝗼𝗻 🛠𝗧𝗼𝗼𝗹 ∷ ❝𝗧𝗵𝗲 𝗣𝗲𝗿𝗳𝗲𝗰𝘁 🔑𝗞𝗲𝘆 𝗙𝗼𝗿 𝗨𝗻𝗹𝗼𝗰𝗸𝗶𝗻𝗴 𝗬𝗼𝘂𝗿 𝗦𝗽𝗿𝗲𝗮𝗱𝘀𝗵𝗲𝗲𝘁𝘀! ❞");
                    APP_FRAME.getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(70,73,75));
                    
                    byte[] decodedBytes = Base64.getDecoder().decode(iconURI);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
                    ImageIcon icon = new ImageIcon(image);
                    APP_FRAME.setIconImage(icon.getImage());
                    
                    LOG_TEXT_AREA = new JTextArea();
                    LOG_TEXT_AREA.setFont(logFont);
                    
                    APP_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    APP_FRAME.getContentPane().add(new Main());
                    APP_FRAME.pack();
                    
                    // set to center and middle of screen
                    int frameWidth = 835;
                    int frameHeight = 625;
                    APP_FRAME.setSize(frameWidth, frameHeight);
                    APP_FRAME.setLocationRelativeTo(null);
                    APP_FRAME.setVisible(true);
                    
                    LOGGER.info(() -> " Welcome to Excel Anti-Protection Tool! ");
                    outputConsoleLogsBreakline("");
                    updateLogs();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                    outputConsoleLogsBreakline("");
                }
            }
        });
    }
}