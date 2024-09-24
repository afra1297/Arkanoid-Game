// 323274480 Michael Ifraimov
package gameanimation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


/**
 * Class KeyPressStoppableAnimation decorator-class that will wrap an existing animation and
 * add a "waiting-for-key" behavior to it.
 * @author Michael Ifraimov
 */
public class KeyPressStoppableAnimation implements Animation {
    private final KeyboardSensor keyboardSensor;
    private final String key; // the key that stops the animation
    private final Animation animation; // the animation that runs and will be stopped
    private boolean stop; // boolean indicator that indicates if the animation should stop
    private boolean isAlreadyPressed; // indicator that indicates if the key was pressed previously

    /**
     * Constructor.
     * @param sensor KeyboardSensor type
     * @param key String type, the key that stops the animation
     * @param animation Animation type, the animation that runs and will be stopped
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboardSensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }
    /**
     * A method that is in charge of the animation logic.
     *
     * @param d DrawSurface type, the drawing surface of the game
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        // check if the key was pressed
        if (this.keyboardSensor.isPressed(this.key)) {
            // if the key was not pressed already - stop the animation
            if (!this.isAlreadyPressed) {
                this.stop = true;
            } else {
                this.isAlreadyPressed = false;
            }
        }
    }

    /**
     * A method that is in charge of stopping the game from running.
     *
     * @return boolean type, true if condition is met and false otherwise
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
