// 323274480 Michael Ifraimov
package levels;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gameanimation.AnimationRunner;
import gameanimation.Animation;
import gameanimation.KeyPressStoppableAnimation;
import gameanimation.CountdownAnimation;
import gameanimation.PauseScreen;
import gameobjects.collidables.Block;
import gameobjects.collidables.Collidable;
import gameobjects.collidables.Paddle;
import gameobjects.collidables.GameEnvironment;
import gameobjects.sprites.Ball;
import gameobjects.sprites.Velocity;
import gameobjects.sprites.ScoreIndicator;
import gameobjects.sprites.SpriteCollection;
import gameobjects.sprites.Sprite;
import geometry.Point;
import geometry.Rectangle;
import listeners.listenerclass.BallRemover;
import listeners.listenerclass.BlockRemover;
import listeners.listenerclass.Counter;
import listeners.listenerclass.ScoreTrackingListener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class GameLevel holds all collidables and sprites of the game, will be the class
 * in charge of the animation.
 * @author Michael Ifraimov
 */
public class GameLevel implements Animation {
    // Class GameLevel constants
    private static final int FRAME_SIZE = 25; // width of frame  blocks
    private static final int SCREEN_TOP_LEFT = 0; // top left point coordinate
    private static final int PADDLE_HEIGHT = 18; // height of the paddle
    private static final int DEFAULT_BALL_SIZE = 5;
    private static final int SINGLE_COUNT = 1;
    private static final int STARTING_COUNT = 0;
    private static final int COMPLETED_POINTS = 100;
    private static final int NUMBER_OF_SECONDS = 2;
    private static final int COUNT_FROM = 3;
    // Class Game member variables
    private final SpriteCollection sprites; // list of all sprites in the game
    private final GameEnvironment environment; // all collidable objects in the game
    private final Counter remainingBlocks; // number of remaining blocks in the game
    private final Counter remainingBalls; // number of remaining balls in the game
    private final Counter score; // the score count of the player
    private final AnimationRunner runner; // runs the different animations of the game
    private boolean running; // indicates if the game is running or not
    private final KeyboardSensor keyboard;
    private final LevelInformation levelInfo;
    private Paddle paddle;
    private final int screenWidth;
    private final int screenHeight;

    /**
     * Constructor.
     * @param keyboard KeyboardSensor type
     * @param runner AnimationRunner type, runs the different animations of the game
     * @param levelInfo LevelInformation type, has all the information of each level (balls, block, paddle, etc..)
     * @param score Counter type, score counter
     * @param screenWidth int type, the width of the screen
     * @param screenHeight int type, the height of the screen
     */
    public GameLevel(KeyboardSensor keyboard, AnimationRunner runner, LevelInformation levelInfo, Counter score,
                     int screenWidth, int screenHeight) {
        this.keyboard = keyboard;
        this.runner = runner;
        this.levelInfo = levelInfo;
        this.score = score;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.running = false;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter(STARTING_COUNT); // set blocks counter as 0, before adding blocks
        this.remainingBalls = new Counter(STARTING_COUNT); // set balls counter as 0, before adding balls
        this.paddle = null;
    }

    /**
     * Getter method to get the remaining blocks in the level.
     * @return int type, number of remaining blocks
     */
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }

    /**
     * Getter method to get the remaining balls in the level.
     * @return int type, number of remaining balls
     */
    public int getRemainingBalls() {
        return this.remainingBalls.getValue();
    }

    /**
     * Add Collidable object to the game environment list.
     * @param c Collidable type, collidable object in the game
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.environment.addCollidable(c);
        }
    }

    /**
     * Removes Collidable object from the game environment list.
     * @param c Collidable type, collidable object that will be removed
     */
    public void removeCollidable(Collidable c) {
        if (c != null) {
            this.environment.removeCollidable(c);
        }

    }

    /**
     * Add Sprite object to the game sprites list.
     * @param s Sprite type, sprite object in the game
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            this.sprites.addSprite(s);
        }
    }

    /**
     * Removes Sprite object from the game sprites list.
     * @param s Sprite type, sprite object that will be removed
     */
    public void removeSprite(Sprite s) {
        if (s != null) {
            this.sprites.removeSprite(s);
        }
    }

    /**
     * Add ScoreIndicator object to the game.
     */
    public void addScoreIndicator() {
        Rectangle borderRectangle = new Rectangle(SCREEN_TOP_LEFT, SCREEN_TOP_LEFT, FRAME_SIZE, this.screenWidth,
                Color.WHITE);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score, this.levelInfo.levelName(), borderRectangle);
        scoreIndicator.addToGame(this);
    }


    /**
     * Creates a list of 4 Block types which wille be the frame of the game.
     * @return Array List of Block types
     */
    public List<Block> frameBlocks() {
        Color color = Color.cyan;
        // list of 4 blocks - the frame blocks
        List<Block> frameList = new ArrayList<>();
        // top block of the frame
        Block topBlock = new Block(new Rectangle(SCREEN_TOP_LEFT, SCREEN_TOP_LEFT + FRAME_SIZE,
                FRAME_SIZE, this.screenWidth, color), true);
        // left block of the frame
        Block leftBlock = new Block(new Rectangle(SCREEN_TOP_LEFT, SCREEN_TOP_LEFT + FRAME_SIZE,
                this.screenHeight, FRAME_SIZE, color), true);
        // bottom block of the frame - if a ball hits it, the ball is removed
        Block bottomBlock = new Block(new Rectangle(SCREEN_TOP_LEFT,
                this.screenHeight, 0, this.screenWidth, color), true);
        // set bottom block as "death block"
        bottomBlock.addHitListener(new BallRemover(this, this.remainingBalls));
        // right block of the frame
        Block rightBlock = new Block(new Rectangle(this.screenWidth - FRAME_SIZE,
                SCREEN_TOP_LEFT + FRAME_SIZE, this.screenHeight, FRAME_SIZE, color), true);
        frameList.add(topBlock);
        frameList.add(leftBlock);
        frameList.add(bottomBlock);
        frameList.add(rightBlock);
        return frameList;
    }

    /**
     * Creates frame blocks and adds them to the game.
     */
    public void buildFrame() {
        List<Block> frame = this.frameBlocks(); // frame blocks creation
        // add each block to the game
        for (Block block : frame) {
            block.addToGame(this);
        }
    }

    /**
     * Adds the level balls to the game.
     */
    public void initializeBalls() {
        /*
         * the number of velocities in initialBallVelocities is equal to the number of balls,
         * using a loop the method iterates over each velocity, creates a ball and adds it to the game
         */
        for (Velocity velocity : this.levelInfo.initialBallVelocities()) {
            // ball initial location coordinates
            double centerX = this.screenWidth / 2.0;
            double centerY = this.screenHeight - FRAME_SIZE - PADDLE_HEIGHT - DEFAULT_BALL_SIZE;
            Point centerPoint = new Point(centerX, centerY);
            Ball ball = new Ball(centerPoint, DEFAULT_BALL_SIZE, Color.BLUE, this.environment);
            ball.setVelocity(velocity);
            this.remainingBalls.increase(SINGLE_COUNT);
            ball.addToGame(this);
        }
    }

    /**
     * Creates a paddle and adds it to the game.
     */
    public void initializePaddle() {
        // screen frame
        Rectangle frame = new Rectangle(SCREEN_TOP_LEFT, SCREEN_TOP_LEFT,
                this.screenHeight, this.screenWidth);
        // point the paddle will start from (bottom, middle of the screen)
        Point paddleStart = new Point((this.screenWidth / 2.0) - (this.levelInfo.paddleWidth() / 2.0),
                this.screenHeight - FRAME_SIZE - PADDLE_HEIGHT);
        if (this.paddle != null) {
            this.paddle.getCollisionRectangle().setUpperLeft(paddleStart);
            return;
        }
        // Create a paddle and add it to the game
        Paddle paddle = new Paddle(this.keyboard, new Rectangle(paddleStart,
                this.levelInfo.paddleWidth(), PADDLE_HEIGHT), frame, this.levelInfo.paddleSpeed());
        paddle.setColor(Color.RED);
        paddle.addToGame(this);
        this.paddle = paddle;
    }

    /**
     * Adds all the block from the levelInfo to the game.
     */
    public void initializeBlocks() {
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        // adds every block to the game, add hit listeners to the blocks and updates blocks count
        for (Block block : this.levelInfo.blocks()) {
            block.addToGame(this);
            this.remainingBlocks.increase(SINGLE_COUNT);
            block.addHitListener(blockRemover);
            block.addHitListener(scoreTrackingListener);
        }
    }
    /**
     * Initializes a new game: creates the Blocks, the Paddle, the balls and the Score Indicator and
     * adds them to the game.
     */
    public void initialize() {
        this.buildFrame();
        this.initializeBlocks();
        this.initializeBalls();
        this.initializePaddle();
        this.addScoreIndicator();
    }

    /**
     * Runs the game.
     */
    public void run() {
        this.runner.run(new CountdownAnimation(NUMBER_OF_SECONDS, COUNT_FROM, this.sprites, this.levelInfo));
        this.running = true;
        this.runner.run(this);
    }

    /**
     * A method that is in charge of the game's logic.
     *
     * @param d DrawSurface type, the drawing surface of the game
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        this.levelInfo.drawBackground(d);
        // check if there are no more blocks to hit
        if (this.remainingBlocks.getValue() == STARTING_COUNT) {
            this.score.increase(COMPLETED_POINTS); // add 100 points - level completed
            this.running = false;
        }
        // check if there are no balls remaining
        if (this.remainingBalls.getValue() == STARTING_COUNT) {
            this.running = false;
        }
        // check if pause key - "p" was pressed
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen()));
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
    }

    /**
     * A method that is in charge of stopping the game from running.
     *
     * @return boolean type, true if condition is met and false otherwise
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }
}

