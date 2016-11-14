package base.ch02.example4;

import base.ch02.CharacterDisplayCanvasImpl;
import base.ch02.CharacterEventHandler;
import base.ch02.ICharacterListener;
import base.ch02.ICharacterSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingTypeTester extends JFrame implements ICharacterSource {

    protected RandomCharacterGenerator producer;
    private CharacterDisplayCanvasImpl displayCanvas;
    private CharacterDisplayCanvasImpl feedbackCanvas;
    private JButton quitButton;
    private JButton startButton;
    private JButton stopButton;
    private CharacterEventHandler handler;

    public SwingTypeTester() {
        initComponents();
    }

    public static void main(String args[]) {
        new SwingTypeTester().show();
    }

    private void initComponents() {
        handler = new CharacterEventHandler();
        displayCanvas = new CharacterDisplayCanvasImpl();
        feedbackCanvas = new CharacterDisplayCanvasImpl(this);
        quitButton = new JButton("Start");
        startButton = new JButton("Stop");
        stopButton = new JButton("Quit");
        add(displayCanvas, BorderLayout.NORTH);
        add(feedbackCanvas, BorderLayout.CENTER);
        JPanel p = new JPanel();
        p.add(startButton);
        p.add(stopButton);
        p.add(quitButton);
        add(p, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                quit();
            }
        });

        feedbackCanvas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char c = ke.getKeyChar();
                if (c != KeyEvent.CHAR_UNDEFINED)
                    newCharacter((int) c);
            }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                producer = new RandomCharacterGenerator();
                displayCanvas.setCharacterSource(producer);
                producer.start();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                feedbackCanvas.setEnabled(true);
                feedbackCanvas.requestFocus();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                producer.interrupt();
                feedbackCanvas.setEnabled(false);
            }
        });
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quit();
            }
        });

        pack();
    }

    private void quit() {
        System.exit(0);
    }

    public void addCharacterListener(ICharacterListener cl) {
        handler.addCharacterListener(cl);
    }

    public void removeCharacterListener(ICharacterListener cl) {
        handler.removeCharacterListener(cl);
    }

    public void newCharacter(int c) {
        handler.fireNewCharacter(this, c);
    }

    public void nextCharacter() {
        throw new IllegalStateException("We don't produce on demand");
    }
}
