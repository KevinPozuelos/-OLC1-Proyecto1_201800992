/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_olc1;
import analizadores.Arbol;
import analizadores.Excepcion;
import analizadores.ObjetoError;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author aller
 */
public class Interfaz extends javax.swing.JFrame {

    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
        GenerarCarpetas();
    }
    
    String pathActivo = null;
    String[] listadoImagenes;
    String[] nombresImagenes;
    int posicion;
    public ArrayList<Arbol> Recuperadas = new ArrayList<>();
    public ArrayList<Excepcion> errores = new ArrayList<>();
    public ArrayList<ObjetoError> validaciones = new ArrayList<>();

    public void GenerarCarpetas(){
        String arboles = "ARBOLES_201800992";
        String afnd = "AFND_201800992";
        String siguientes = "SIGUIENTES_201800992";
        String transiciones = "TRANSICIONES_201800992";
        String afd = "AFD_201800992";
        String errores = "ERRORES_201800992";
        String salidas = "SALIDAS_201800992";
        
        File tree = new File(arboles);
        if (!tree.exists()){
            if(tree.mkdirs()){
                System.out.println("Directorio creado");
            }else{
                System.out.println("Error al crear el directorio");
            }
        }
        
        File afn = new File(afnd);
        if (!afn.exists()){
            if(afn.mkdirs()){
                System.out.println("Directorio creado");
            }else{
                System.out.println("Error al crear el directorio");
            }
        }
        
        File trans = new File(transiciones);
        if (!trans.exists()){
            if(trans.mkdirs()){
                System.out.println("Directorio creado");
            }else{
                System.out.println("Error al crear el directorio");
            }
        }
        
        File follow = new File(siguientes);
        if (!follow.exists()){
            if(follow.mkdirs()){
                System.out.println("Directorio creado");
            }else{
                System.out.println("Error al crear el directorio");
            }
        }
        
        File automatas = new File(afd);
        if (!automatas.exists()){
            if(automatas.mkdirs()){
                System.out.println("Directorio creado");
            }else{
                System.out.println("Error al crear el directorio");
            }
        }
        
        File er = new File(errores);
        if (!er.exists()){
            if(er.mkdirs()){
                System.out.println("Directorio creado");
            }else{
                System.out.println("Error al crear el directorio");
            }
        }
        
        File out = new File(salidas);
        if (!out.exists()){
            if(out.mkdirs()){
                System.out.println("Directorio creado");
            }else{
                System.out.println("Error al crear el directorio");
            }
        }
    }
    
    public void GenerarJSONDeValidas() throws IOException{
        String name = "SALIDAS_201800992/Validaciones.json";
        File file = new File(name);
        if(file.exists()){
            file.delete();
        }
        
        file.createNewFile();
        
        Gson g = new Gson();
        
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("[\n");
        for(int i = 0; i < validaciones.size(); i++){
            if(i != (validaciones.size() - 1)){
                bw.write(g.toJson(validaciones.get(i)) + ",\n");
            }else{
                bw.write(g.toJson(validaciones.get(i)) + " \n");
            }
        }
        
        bw.write("]\n");
        bw.close();
        
    }
    
    public void GenerarReporteErrores() throws IOException{
        String name = "ERRORES_201800992/Errores.html";
        File file = new File(name);
        if(file.exists()){
            file.delete();
        }
        
        file.createNewFile();
        
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("<html>\n");
        bw.write("<head><title>Reporte de Errores</title></head>\n");
        bw.write("<center><h1><font color=\"navy\">Reporte De Errores</font></h1></center>\n");
        bw.write("<body bgcolor=\"#6DC36D\">\n");
        bw.write("<table border=1 class=\"default\">\n" +
            "\"<tr><td>Tipo</td><td>Descripccion</td><td>Linea</td><td>Columna</td></tr>\n");
        
        for(int i = 0; i < errores.size(); i++){
            bw.write(errores.get(i).toString()  +" \n");
        }
        
        bw.write("</table>\n");
        bw.write("</body>\n");
        bw.write("</html>\n");
        bw.close();
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("fmrInterfazPrincipal "); // NOI18N

        jButton1.setText("Abrir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Guardar como");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Nuevo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("ARCHIVO DE ENTRADA");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Arboles", "Siguientes", "Transiciones", "AFD", "AFND" }));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel3.setText("SALIDA");

        jButton5.setText("Generar Automatas");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Analizar entradas");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Anterior");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Siguiente");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Ver resultados");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addGap(316, 316, 316)
                                .addComponent(jButton9)
                                .addGap(26, 26, 26)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(336, 336, 336))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton8)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1092, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton9)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5)
                            .addComponent(jButton6)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(jButton8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void GuardarComo(){
        JFileChooser guardarComo = new JFileChooser();
        guardarComo.setApproveButtonText("Crear archivo .exp");
        guardarComo.showSaveDialog(null);
        File archivo = new File(guardarComo.getSelectedFile() + ".exp");
        pathActivo = guardarComo.getSelectedFile() + ".exp";
        try{
            BufferedWriter salida = new BufferedWriter(new FileWriter(archivo));
            salida.write(jTextArea1.getText());
            salida.close();
        }catch (Exception e){
        
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String aux = "";
        String texto = "";
        try {
            JFileChooser file = new JFileChooser(System.getProperty("user.dir"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo EXP", "exp");
            file.setFileFilter(filter);
            file.showOpenDialog(this);
            File archivo = file.getSelectedFile();
            if (archivo != null) {
                FileReader archivos = new FileReader(archivo);
                BufferedReader leer = new BufferedReader(archivos);
                while ((aux = leer.readLine()) != null) {
                    texto += aux + "\n";
                }
            leer.close();
            }
        } catch (IOException ex) {
               JOptionPane.showMessageDialog(null, "Error Importando - " + ex);
         }
        jTextArea1.setText(texto);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       if(pathActivo != null){
            File Guardar = new File(pathActivo);
            BufferedWriter salida;
            try {
                salida = new BufferedWriter(new FileWriter(Guardar));
                salida.write(jTextArea1.getText());
                salida.close();
                JOptionPane.showMessageDialog(null, "Archivo guardado correctamente");
            } catch (IOException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }else{
            GuardarComo();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        GuardarComo();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JFileChooser guardarComo = new JFileChooser();
        guardarComo.setApproveButtonText("Crear archivo .exp");
        guardarComo.showSaveDialog(null);
        File archivo = new File(guardarComo.getSelectedFile() + ".exp");
        pathActivo = guardarComo.getSelectedFile() + ".exp";
        try {
            BufferedWriter salida = new BufferedWriter(new FileWriter(archivo));
            salida.write("//Nuevo archivo .exp");
            salida.newLine();
            salida.close();
            File lectura = new File(pathActivo);
            String St = new String(Files.readAllBytes(lectura.toPath()));
            jTextArea1.setText(St);
            
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int seleccionado = jComboBox1.getSelectedIndex();
        File carpeta = null;
        String concatenar = null;
        
        switch(seleccionado){
            case 0:
                concatenar = "ARBOLES_201800992/";
                carpeta = new File("ARBOLES_201800992");
                break;
            case 1:
                concatenar = "SIGUIENTES_201800992/";
                carpeta = new File("SIGUIENTES_201800992");
                break;
            case 2:
                concatenar = "TRANSICIONES_201800992/";
                carpeta = new File("TRANSICIONES_201800992");
                break;
            case 3:
                concatenar = "AFD_201800992/";
                carpeta = new File("AFD_201800992");
                break;
            case 4:
                concatenar = "AFND_201800992/";
                carpeta = new File("AFND_201800992");
                break;
        }
        String[] listado = carpeta.list();
        listadoImagenes = new String[listado.length];
        for(int i = 0; i < listadoImagenes.length; i++){
            listadoImagenes[i] = concatenar + listado[i];
            System.out.println(listadoImagenes[i]);
        }
        nombresImagenes = listado;
        ImageIcon imagen = new ImageIcon(listadoImagenes[0]);
        posicion = 0;
        jLabel4.setText(nombresImagenes[0]);
        jLabel2.setIcon(new ImageIcon(imagen.getImage()));
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       String ST = jTextArea1.getText();
        analizadores.Sintactico pars;
        analizadores.Lexico scanner;
        scanner = new analizadores.Lexico(new StringReader(ST));
        pars = new analizadores.Sintactico(scanner);
        pars.Recuperadas = Recuperadas;
        try {
            pars.parse();
            errores.clear();
            errores.addAll(scanner.Errores);
            errores.addAll(pars.Errores);
            validaciones.clear();
            validaciones.addAll(pars.validaciones);
            if(errores.size() > 0){
                this.GenerarReporteErrores();
            }
            this.GenerarJSONDeValidas();
            String st = "";
            for (int i = 0; i < validaciones.size(); i++){
                st = st + "La expresion " + validaciones.get(i).getValor() + " es " + validaciones.get(i).getResultado()
                        + " con la expresion regular " 
                        + validaciones.get(i).getExpresionRegular() + " \n  \n";
            }
            jTextArea2.setText(st);
        } catch (Exception ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       String ST = jTextArea1.getText();
        analizadores.Sintactico pars;
        analizadores.Lexico scanner;
        scanner = new analizadores.Lexico(new StringReader(ST));
        pars = new analizadores.Sintactico(scanner);
        try {
            pars.parse();
            Recuperadas.clear();
            Recuperadas.addAll(pars.Regex);
            errores.clear();
            errores.addAll(scanner.Errores);
            errores.addAll(pars.Errores);
            if(errores.size() > 0){
                this.GenerarReporteErrores();
            }
        } catch (Exception ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        posicion--;
        if (posicion == -1){
            posicion = listadoImagenes.length - 1;
        }
        ImageIcon imagen = new ImageIcon(listadoImagenes[posicion]);
        jLabel4.setText(nombresImagenes[posicion]);
        jLabel2.setIcon(new ImageIcon(imagen.getImage()));

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        posicion++;
        if (posicion == listadoImagenes.length){
            posicion = 0;
        }
        ImageIcon imagen = new ImageIcon(listadoImagenes[posicion]);
        jLabel4.setText(nombresImagenes[posicion]);
        jLabel2.setIcon(new ImageIcon(imagen.getImage()));
    }//GEN-LAST:event_jButton8ActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
