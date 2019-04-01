/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boton;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Estudiante
 */
public class Boton extends JButton implements MouseInputListener {

    Ventana ventana;
    boolean presionado;
    int f;
    int c;
    String nombre;
    boolean banderabo, sw;
    int valor;
    Icon icon, icon2;

    public Boton(String nombre, int f, int c, Ventana ventana) {
        this.nombre = nombre;
        this.presionado = false;
        this.ventana = ventana;
        this.f = f;
        this.c = c;
        banderabo = true;
        sw = true;
        this.valor = 0;
        icon = new ImageIcon(getClass().getResource("/Imagenes/bandera.png"));
        icon2 = new ImageIcon(getClass().getResource("/Imagenes/mina.png"));
        setBorder(null);
        this.setBorderPainted(false);
        //this.setContentAreaFilled(false);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addMouseListener(this);
    }

    public int getValor() {
        return valor;
    }

    public void MostrarMina() {
        setIcon(icon2);
        setText(null);
        setEnabled(false);

    }

    public boolean isPresionado() {
        return presionado;
    }

    public void setPresionado(boolean presionado) {
        this.presionado = presionado;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!presionado) {
            if (banderabo && sw) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setIcon(icon);
                    setText(null);
                    banderabo = false;
                    setEnabled(false);
                    ventana.ContadorMinas(-1);

                } else if (valor == -1) {
                    setIcon(icon2);
                    setText(null);
                    ventana.apagartimer();
                    ventana.DestaparMinas();
                    JOptionPane.showMessageDialog(ventana, "Perdiste");
//                    String[] options = {"Reiniciar", "Salir"};
//                    int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una opcion", "¡ALERTA", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon2, options, options[0]);
//                    if(seleccion==0){
//                        ventana.reiniciar();
//                    }

                } else if (valor == 0) {
                    sw = false;
                    presionado = true;
                    setEnabled(false);
                    ventana.NoMinas();
                    ventana.mostrarBoton(f, c);

                } else {
                    sw = false;
                    presionado = true;
                    setEnabled(false);
                    setIcon(null);
                    setText("" + valor);
                    ventana.NoMinas();

                }
            } else if (e.getButton() == MouseEvent.BUTTON3 && sw) {
                setEnabled(true);
                banderabo = true;
                setIcon(null);
                ventana.ContadorMinas(1);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
