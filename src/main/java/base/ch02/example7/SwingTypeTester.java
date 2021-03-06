package base.ch02.example7;

import base.ch02.CharacterDisplayCanvasImpl;
import base.ch02.CharacterEventHandler;
import base.ch02.ICharacterListener;
import base.ch02.ICharacterSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingTypeTester extends JFrame implements ICharacterSource {

    /**
     *
     */
    private static final long serialVersionUID = -3422487377989612404L;
    protected RandomCharacterGenerator producer;
    private AnimatedCharacterDisplayCanvas displayCanvas;
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
        displayCanvas = new AnimatedCharacterDisplayCanvas();
        feedbackCanvas = new CharacterDisplayCanvasImpl(this);
        quitButton = new JButton("Quit");
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        add(displayCanvas, BorderLayout.NORTH);
        add(feedbackCanvas, BorderLayout.CENTER);
        JPanel p = new JPanel();
        stopButton.setEnabled(false);
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
                displayCanvas.setDone(false);
                Thread t = new Thread(displayCanvas);
                t.start();
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
                producer.setDone();
                displayCanvas.setDone(true);
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
