import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import java.awt.*;

public class Frame extends javax.swing.JFrame{

    private File selectedFile = null;
    Lector lecture = new Lector();
    public Frame() {
        initComponents();
        PrintStream printStream = new PrintStream(new CustomOutputStream(TxtMensaje));
        System.setOut(printStream);
    }

    private void initComponents() {
        setTitle("Compilador");
        setResizable(false);
        jScrollPane1 = new javax.swing.JScrollPane();
        TxtCodigo = new javax.swing.JTextArea();
        BtnNuevo = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCompilar = new javax.swing.JButton();
        btnEjecutar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TxtMensaje = new javax.swing.JTextArea();
        labEstatusSin = new javax.swing.JLabel();
        labEstatusLexico = new javax.swing.JLabel();
        labEstatusSem = new javax.swing.JLabel();
        labEstatusLexicoR = new javax.swing.JLabel();
        labEstatusSemR = new javax.swing.JLabel();
        labEstatusSinR = new javax.swing.JLabel();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new java.awt.Color(183, 172, 159));

        TxtCodigo.setColumns(20);
        TxtCodigo.setRows(5);
        TxtCodigo.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        TxtCodigo.setSelectionColor(new java.awt.Color(224, 62, 82));
        TxtCodigo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        TxtCodigo.setEditable(false);
        jScrollPane1.setViewportView(TxtCodigo);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(jScrollPane1, BorderLayout.CENTER);

        JTextArea lineNumbers = new JTextArea("1");
        lineNumbers.setFont(new Font("Monospaced", Font.PLAIN, 12));
        lineNumbers.setBackground(Color.LIGHT_GRAY);
        lineNumbers.setEditable(false);
        jScrollPane1.setRowHeaderView(lineNumbers);

        TxtCodigo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            private void updateLineNumbers() {
                SwingUtilities.invokeLater(() -> {
                    int lineCount = TxtCodigo.getLineCount();
                    StringBuilder numbersText = new StringBuilder();
                    for (int i = 1; i <= lineCount; i++) {
                        if (i > 1) {
                            numbersText.append("\n");
                        }
                        numbersText.append(i);
                    }
                    lineNumbers.setText(numbersText.toString());
                });
            }
        });

        

        BtnNuevo.setBackground(new java.awt.Color(243, 232, 205));
        BtnNuevo.setForeground(new java.awt.Color(51, 51, 51));
        BtnNuevo.setText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(243, 232, 205));
        btnBuscar.setForeground(new java.awt.Color(51, 51, 51));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(243, 232, 205));
        btnGuardar.setForeground(new java.awt.Color(51, 51, 51));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCompilar.setBackground(new java.awt.Color(243, 232, 205));
        btnCompilar.setForeground(new java.awt.Color(51, 51, 51));
        btnCompilar.setText("Compilar");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        btnEjecutar.setBackground(new java.awt.Color(243, 232, 205));
        btnEjecutar.setForeground(new java.awt.Color(51, 51, 51));
        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(98, 83, 73));

        TxtMensaje.setColumns(20);
        TxtMensaje.setRows(5);
        TxtMensaje.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        TxtMensaje.setSelectionColor(new java.awt.Color(0, 165, 181));
        TxtMensaje.setFont(new Font("Monospaced", Font.PLAIN, 12));
        TxtMensaje.setEditable(false);
        jScrollPane3.setViewportView(TxtMensaje);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        labEstatusSin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labEstatusSin.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusSin.setText("Sintactico");

        labEstatusLexico.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labEstatusLexico.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusLexico.setText("Lexico: ");

        labEstatusSem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labEstatusSem.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusSem.setText("Semantico");

        labEstatusLexicoR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labEstatusLexicoR.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusLexicoR.setText("---------");

        labEstatusSemR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labEstatusSemR.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusSemR.setText("---------");

        labEstatusSinR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labEstatusSinR.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusSinR.setText("---------");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCompilar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEjecutar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(labEstatusLexico)
                        .addGap(18, 18, 18)
                        .addComponent(labEstatusLexicoR)
                        .addGap(37, 37, 37)
                        .addComponent(labEstatusSin)
                        .addGap(18, 18, 18)
                        .addComponent(labEstatusSinR)
                        .addGap(36, 36, 36)
                        .addComponent(labEstatusSem)
                        .addGap(18, 18, 18)
                        .addComponent(labEstatusSemR)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCompilar)
                        .addComponent(btnEjecutar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar)
                        .addComponent(btnBuscar)
                        .addComponent(BtnNuevo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labEstatusSin)
                    .addComponent(labEstatusLexico)
                    .addComponent(labEstatusSem)
                    .addComponent(labEstatusLexicoR)
                    .addComponent(labEstatusSemR)
                    .addComponent(labEstatusSinR))
                .addGap(11, 11, 11)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>  
    
    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                           
        if(selectedFile != null)
        {
            int confirmResult = JOptionPane.showConfirmDialog(this, "Deseas guardar el archivo?", "Confirmar sobrescritura", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                btnGuardarActionPerformed(evt);
            }
        }
        TxtCodigo.setText("");
        TxtMensaje.setText(""); 
        labEstatusLexicoR.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusLexicoR.setText("---------");
        labEstatusSemR.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusSemR.setText("---------");
        labEstatusSinR.setForeground(new java.awt.Color(0, 0, 0));
        labEstatusSinR.setText("---------");
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Archivos de Texto (.txt)", "txt");
        fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try {
                    if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                        selectedFile = new File(selectedFile.getParentFile(), selectedFile.getName() + ".txt");
                    }

                    if (selectedFile.exists()) {
                        int confirmResult = JOptionPane.showConfirmDialog(this, "El archivo ya existe. ¿Desea sobrescribirlo?", "Confirmar sobrescritura", JOptionPane.YES_NO_OPTION);
                        if (confirmResult == JOptionPane.NO_OPTION) {
                            return;
                        }
                        selectedFile = fileChooser.getSelectedFile();
                    }

                    FileWriter fileWriter = new FileWriter(selectedFile, false);
                    fileWriter.close();
                    lecture.setArchivo(selectedFile);
                    TxtCodigo.setEditable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }                                     

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {      
        if(selectedFile != null)
        {
            int confirmResult = JOptionPane.showConfirmDialog(this, "Deseas guardar el archivo?", "Confirmar sobrescritura", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                btnGuardarActionPerformed(evt);
            }
        }                                    
        JFileChooser fileChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Archivos de Texto (.txt)", "txt");
        fileChooser.setFileFilter(filter);
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            lecture.setArchivo(selectedFile);
            try {
                String content = new String(Files.readAllBytes(selectedFile.toPath()));
                TxtCodigo.setText(content);
                TxtCodigo.setEditable(true);
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }                                         

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        if (selectedFile != null) {
            try {
                FileWriter fileWriter = new FileWriter(selectedFile, false); // Utiliza 'false' para sobrescribir el archivo
                String content = TxtCodigo.getText(); // Obtiene el contenido del TxtCodigo
                fileWriter.write(content); // Escribe el contenido en el archivo
                fileWriter.close(); // Cierra el archivo
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un archivo primero", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                          

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        TxtMensaje.setText("");
        System.out.println("Comienzo de Analizador Lexico\n");
        boolean band = lecture.Lectura();
        if(band){
            labEstatusLexicoR.setForeground(new java.awt.Color(247, 36, 36));
            labEstatusLexicoR.setText("----X----");
        }
        else{
            labEstatusLexicoR.setForeground(new java.awt.Color(122, 240, 53));
            labEstatusLexicoR.setText("----O----");
        }
        System.out.println("Analizador Lexico  terminado");
        System.out.println("*----------------------------------------*\n");
        System.out.println("Comienzo de analisis Sintatico");
        System.out.println("*----------------------------------------*\n");
        Sintatico St = new Sintatico(lecture.Inicio,this);
        St.pawn();
        labEstatusSinR.setForeground(new java.awt.Color(122, 240, 53));
        labEstatusSinR.setText("----O----");
        labEstatusSemR.setForeground(new java.awt.Color(122, 240, 53));
        labEstatusSemR.setText("----O----");
    }                                           

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    public class CustomOutputStream extends OutputStream {
        private JTextArea textArea;
    
        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }
    
        @Override
        public void write(int b) throws IOException {
            // Redirige la salida al JTextArea
            textArea.append(String.valueOf((char) b));
            // Mueve el cursor del JTextArea al final
            textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        }

    public JLabel getLabelSin() {
            return labEstatusSinR;
        }

    public JLabel getLabelSem() {
            return labEstatusSemR;
        }

        
    // Variables declaration - do not modify                     
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JTextArea TxtCodigo;
    private javax.swing.JTextArea TxtMensaje;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labEstatusLexico;
    private javax.swing.JLabel labEstatusLexicoR;
    private javax.swing.JLabel labEstatusSem;
    private javax.swing.JLabel labEstatusSemR;
    private javax.swing.JLabel labEstatusSin;
    private javax.swing.JLabel labEstatusSinR;
    // End of variables declaration
}
