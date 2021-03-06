package base.ch05.example1;

import base.ch05.CharacterEvent;
import base.ch05.ICharacterListener;
import base.ch05.ICharacterSource;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ScoreLabel extends JLabel implements ICharacterListener {
    private AtomicInteger score = new AtomicInteger(0);
    private AtomicInteger char2type = new AtomicInteger(-1);
    private AtomicReference<ICharacterSource> generator = null;
    private AtomicReference<ICharacterSource> typist = null;

    public ScoreLabel(ICharacterSource generator, ICharacterSource typist) {
        this.generator = new AtomicReference(generator);
        this.typist = new AtomicReference(typist);

        if (generator != null)
            generator.addCharacterListener(this);
        if (typist != null)
            typist.addCharacterListener(this);
    }

    public ScoreLabel() {
        this(null, null);
    }

    public void resetGenerator(ICharacterSource newGenerator) {
        ICharacterSource oldGenerator;

        if (newGenerator != null)
            newGenerator.addCharacterListener(this);

        oldGenerator = generator.getAndSet(newGenerator);
        if (oldGenerator != null)
            oldGenerator.removeCharacterListener(this);
    }

    public void resetTypist(ICharacterSource newTypist) {
        ICharacterSource oldTypist;

        if (newTypist != null)
            newTypist.addCharacterListener(this);

        oldTypist = typist.getAndSet(newTypist);
        if (oldTypist != null)
            oldTypist.removeCharacterListener(this);
    }

    public void resetScore() {
        score.set(0);
        char2type.set(-1);
        setScore();
    }

    private void setScore() {
        // This method will be explained later in chapter 7
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setText(Integer.toString(score.get()));
            }
        });
    }

    public void newCharacter(CharacterEvent ce) {
        int oldChar2type;

        // Previous character not typed correctly - 1 point penalty
        if (ce.source == generator.get()) {
            oldChar2type = char2type.getAndSet(ce.character);

            if (oldChar2type != -1) {
                score.decrementAndGet();
                setScore();
            }
        }
        // If character is extraneous - 1 point penalty
        // If character does not match - 1 point penalty
        else if (ce.source == typist.get()) {
            while (true) {
                oldChar2type = char2type.get();

                if (oldChar2type != ce.character) {
                    score.decrementAndGet();
                    break;
                } else if (char2type.compareAndSet(oldChar2type, -1)) {
                    score.incrementAndGet();
                    break;
                }
            }

            setScore();
        }
    }
}
