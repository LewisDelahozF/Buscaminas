/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author TBlacksT
 */
public final class Ventana extends javax.swing.JFrame {

    Icon icon;
    int TiempoAntiguo;
    String GanadorAntiguo;
    int tiempo;
    int filas;
    int columnas;
    int tam;
    Boton botones[][];
    Timer timer;
    int Minas;
    int NumMinas;
    int NoMinas;
    File historial;

    /**
     * Creates new form NewJFrame
     */
    public Ventana() {
        icon = new ImageIcon(getClass().getResource("/Imagenes/bandera.png"));
        initComponents();
        setLocationRelativeTo(null);
        dificultad();
        historial = new File("historial.txt");
        creararchivo();
        leerGanador();
        tiempo = 0;
        NoMinas = (filas * columnas) - NumMinas;
        botones = new Boton[filas][columnas];
        crearbotones();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempo++;
                TiempoJT.setText("Tiempo : " + tiempo);
            }
        });
    }

    public void creararchivo() {
        if (!historial.exists()) {
            try {

                historial.createNewFile();
            } catch (IOException ex) {
                System.out.println("No se creo");
            }
        }
    }

    public void dificultad() {
        String[] options = {"Facil", "Medio", "Dificil", "Extremo alv"};
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "Nivel", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        if (seleccion == 0) {
            NumMinas = 10;
            filas = 10;
            columnas = filas;
            tam = tableroPnl.getWidth() / filas;
            Minas = NumMinas;
        }
        if (seleccion == 1) {
            NumMinas = 25;
            filas = 20;
            columnas = filas;
            tam = tableroPnl.getWidth() / filas;
            Minas = NumMinas;
        }
        if (seleccion == 2) {
            NumMinas = 35;
            filas = 30;
            columnas = filas;
            tam = tableroPnl.getWidth() / filas;
            Minas = NumMinas;
        }
        if (seleccion == 3) {
            filas = 25;
            columnas = filas;
            NumMinas = columnas*filas-3;
            tam = tableroPnl.getWidth() / filas;
            Minas = NumMinas;
        }
    }

    public void leerGanador() {
        try {
            FileReader r = new FileReader("historial.txt");
            BufferedReader buffer = new BufferedReader(r);
            String Datos = buffer.readLine();
            if (Datos != null) {
                for (int i = 0; i < Datos.length(); i++) {
                    if (Datos.substring(i, i + 1).equals(" ")) {
                        GanadorAntiguo = Datos.substring(0, i);
                        TiempoAntiguo = Integer.parseInt(Datos.substring(i + 1));
                    }
                }
            } else {
                GanadorAntiguo = "";
                TiempoAntiguo = 999999999;
            }
        } catch (IOException ex) {

        }
    }

//    public void reiniciar() {
//
//
//    }
    public void crearbotones() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Boton boton = new Boton("Boton " + i + " , " + j, i, j, this);
                botones[i][j] = boton;
                tableroPnl.add(boton);
                boton.setBounds(j * tam, i * tam, tam + 5, tam + 5);
            }
        }
        int f = 0, c = 0;
        //Colando minas
        for (int i = 0; i < NumMinas; i++) {
            do {
                f = (int) (Math.random() * filas); // Aleatorio entre 0 y filas
                c = (int) (Math.random() * columnas); // Aleatorio entre 0 y columnas

            } while (botones[f][c].getValor() == -1);
            botones[f][c].setValor(-1);
        }
        contar();

    }

    public void mostrarBoton(int f, int c) {
        int valor = botones[f][c].getValor();
        botones[f][c].setPresionado(true);

        NoMinas();
        if (valor != 0) {
            botones[f][c].setText("" + valor);
        }

        if (valor == 0) {
            if ((f - 1) >= 0 && (c - 1) >= 0 && !botones[f - 1][c - 1].isPresionado()) {
                mostrarBoton(f - 1, c - 1);
            }
            if ((f - 1) >= 0 && !botones[f - 1][c].isPresionado()) {
                mostrarBoton(f - 1, c);
            }
            if ((f - 1) >= 0 && (c + 1) < columnas && !botones[f - 1][c + 1].isPresionado()) {
                mostrarBoton(f - 1, c + 1);
            }
            if ((c - 1) >= 0 && !botones[f][c - 1].isPresionado()) {
                mostrarBoton(f, c - 1);
            }
            if ((c + 1) < columnas && !botones[f][c + 1].isPresionado()) {
                mostrarBoton(f, c + 1);
            }
            if ((f + 1) < filas && (c - 1) >= 0 && !botones[f + 1][c - 1].isPresionado()) {
                mostrarBoton(f + 1, c - 1);
            }
            if ((f + 1) < filas && !botones[f + 1][c].isPresionado()) {
                mostrarBoton(f + 1, c);
            }
            if ((f + 1) < filas && (c + 1) < columnas && !botones[f + 1][c + 1].isPresionado()) {
                mostrarBoton(f + 1, c + 1);
            }

        }
        botones[f][c].setEnabled(false);

    }

    public void apagartimer() {
        timer.stop();
    }

    public void ContadorMinas(int nuevaMina) {
        Minas = Minas + nuevaMina;
        MinasJt.setText("Minas : " + Minas);
        NoMinas();
    }

    public void NoMinas() {
        NoMinas = (filas * columnas) - NumMinas;
        OtroJt.setText("Fichas : " + NoMinas);
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (botones[j][i].isPresionado()) {
                    NoMinas--;
                }
            }
        }
        OtroJt.setText("Fichas : " + NoMinas);
        if (NoMinas == 0 && Minas == 0) {
            timer.stop();

            JOptionPane.showMessageDialog(null, "Ganaste");
            if (TiempoAntiguo > tiempo) {
                String NuevoGanador = JOptionPane.showInputDialog("Escribe tu nombre");
                try {
                    FileWriter fw = new FileWriter("historial.txt");
                    fw.append(NuevoGanador + " " + tiempo);
                    fw.close();
                    actualizar();
                } catch (IOException ex) {
                }
            }

        }
    }

    public void DestaparMinas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (botones[i][j].getValor() == -1) {
                    botones[i][j].MostrarMina();
                }
                botones[i][j].setPresionado(true);
                botones[i][j].setEnabled(false);
            }

        }
    }

    public void actualizar() {
        MinasJt.setText("Minas : " + Minas);
        OtroJt.setText("Fichas : " + NoMinas);
        Ganador.setText("Ganador : " + GanadorAntiguo);
        if (TiempoAntiguo != 999999999) {
            TiempoRecord.setText("Tiempo Record : " + TiempoAntiguo + " seg");
        }

    }

    public void contar() {
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (botones[f][c].getValor() == -1) {
                    if (f - 1 >= 0 && botones[f - 1][c].getValor() != -1) {
                        botones[f - 1][c].setValor(botones[f - 1][c].getValor() + 1);
                    }
                    if (f + 1 < filas && botones[f + 1][c].getValor() != -1) {
                        botones[f + 1][c].setValor(botones[f + 1][c].getValor() + 1);
                    }
                    if (c - 1 >= 0 && botones[f][c - 1].getValor() != -1) {
                        botones[f][c - 1].setValor(botones[f][c - 1].getValor() + 1);
                    }
                    if (c + 1 < columnas && botones[f][c + 1].getValor() != -1) {
                        botones[f][c + 1].setValor(botones[f][c + 1].getValor() + 1);
                    }
                    if (f - 1 >= 0 && c - 1 >= 0 && botones[f - 1][c - 1].getValor() != -1) {
                        botones[f - 1][c - 1].setValor(botones[f - 1][c - 1].getValor() + 1);
                    }
                    if (f - 1 >= 0 && c + 1 < columnas && botones[f - 1][c + 1].getValor() != -1) {
                        botones[f - 1][c + 1].setValor(botones[f - 1][c + 1].getValor() + 1);
                    }
                    if (f + 1 < filas && c - 1 >= 0 && botones[f + 1][c - 1].getValor() != -1) {
                        botones[f + 1][c - 1].setValor(botones[f + 1][c - 1].getValor() + 1);
                    }
                    if (f + 1 < filas && c + 1 < columnas && botones[f + 1][c + 1].getValor() != -1) {
                        botones[f + 1][c + 1].setValor(botones[f + 1][c + 1].getValor() + 1);
                    }

                }

            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableroPnl = new javax.swing.JPanel();
        TiempoJT = new javax.swing.JTextField();
        MinasJt = new javax.swing.JTextField();
        OtroJt = new javax.swing.JTextField();
        Logo = new javax.swing.JPanel();
        Ganador = new javax.swing.JTextField();
        TiempoRecord = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 700));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tableroPnl.setPreferredSize(new java.awt.Dimension(700, 700));

        javax.swing.GroupLayout tableroPnlLayout = new javax.swing.GroupLayout(tableroPnl);
        tableroPnl.setLayout(tableroPnlLayout);
        tableroPnlLayout.setHorizontalGroup(
            tableroPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );
        tableroPnlLayout.setVerticalGroup(
            tableroPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        TiempoJT.setEditable(false);
        TiempoJT.setText("Tiempo : 0");
        TiempoJT.setFocusable(false);

        MinasJt.setEditable(false);
        MinasJt.setText("Minas : 0");
        MinasJt.setFocusable(false);

        OtroJt.setEditable(false);
        OtroJt.setText("Fichas: 0");
        OtroJt.setFocusable(false);

        Logo.setPreferredSize(new java.awt.Dimension(166, 166));

        javax.swing.GroupLayout LogoLayout = new javax.swing.GroupLayout(Logo);
        Logo.setLayout(LogoLayout);
        LogoLayout.setHorizontalGroup(
            LogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
        );
        LogoLayout.setVerticalGroup(
            LogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
        );

        Ganador.setEditable(false);
        Ganador.setText("Ganador:");
        Ganador.setFocusable(false);

        TiempoRecord.setEditable(false);
        TiempoRecord.setText("Tiempo Record:");
        TiempoRecord.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableroPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TiempoJT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MinasJt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OtroJt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Logo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ganador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TiempoRecord, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(TiempoJT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MinasJt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OtroJt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ganador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TiempoRecord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableroPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        timer.start();
        Imagen imagen = new Imagen();
        Logo.add(imagen);
        Logo.repaint();

        actualizar();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Ganador;
    private javax.swing.JPanel Logo;
    private javax.swing.JTextField MinasJt;
    private javax.swing.JTextField OtroJt;
    private javax.swing.JTextField TiempoJT;
    private javax.swing.JTextField TiempoRecord;
    private javax.swing.JPanel tableroPnl;
    // End of variables declaration//GEN-END:variables
}
