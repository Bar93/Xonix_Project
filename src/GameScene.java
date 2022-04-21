import javax.sound.midi.Soundbank;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.util.Random;

public class GameScene extends JPanel {
    private Player player;
    private Enemy enemy;
    private Brick[][] bord;
    ImageIcon finishImage;


    public static final int GAME_SCENE_WIDTH = 600;
    public static final int GAME_SCENE_HEIGHT = 600;
    public static final int GAME_SPEED = 10;
    public static final int PLAYER_SPEED = 100;
    public static final int ENEMY_SPEED = 80;
    public static final int ROW = 60;
    public static final int COL = 60;
    public static final int UP_LEFT = 1;
    public static final int UP_RIGHT = 2;
    public static final int DOWN_LEFT = 3;
    public static final int DOWN_RIGHT = 4;
    public static final int LEFT_UP = 5;
    public static final int RIGHT_UP = 6;
    public static final int LEFT_DOWN = 7;
    public static final int RIGHT_DOWN = 8;
    public static final double LEVEL_ONE = 60;

    public GameScene() {
        this.setBounds(0, 41, GAME_SCENE_WIDTH, GAME_SCENE_HEIGHT);
        this.setBackground(Color.RED);
        Random rnd = new Random();
        int randomX = rnd.nextInt(20, GameScene.GAME_SCENE_HEIGHT-20);
        int randomY = rnd.nextInt(20, GameScene.GAME_SCENE_WIDTH-20);
        this.player = new Player(Brick.PLAYER_BRICK, 0, 40);
        this.createMatrix();
        this.enemy = new Enemy(randomX, randomY);
        this.movePlayer();
        this.mainGameLoop();
        this.moveEnemy();
        Move movement = new Move(this.player);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(movement);
        playSound();
    }

    private void movePlayer () {
        new Thread(() -> {
            while (true) {
                switch (this.player.getDirection()) {
                    case Player.LEFT:
                        this.player.moveLeft();
                        break;
                    case Player.RIGHT:
                        this.player.moveRight();
                        break;
                    case Player.UP:
                        this.player.moveUp();
                        break;
                    case Player.DOWN:
                        this.player.moveDown();
                        break;
                }
                repaint();
                try {
                    Thread.sleep(PLAYER_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBord(g);
        this.player.paint(g, this.player.getX(), this.player.getY());
        this.enemy.paint(g);
        this.player.setNinja( new ImageIcon("ninja.png"));
        this.player.getNinja().paintIcon(this,g,this.player.getX()-10,this.player.getY()-15);
        this.enemy.setShurikan(new ImageIcon("shriken1.png"));
        this.enemy.getShurikan().paintIcon(this,g,this.enemy.getX()-400,this.enemy.getY()-100);
        if (this.finishImage!=null) {
            this.finishImage.paintIcon(this, g, 20, 20);
        }

    }

    private void mainGameLoop() { //תזוזת שחקן
        Thread t1 = new Thread(() -> {
            int index = 0;
            this.player.setInMotion(false);
            while (true) {
                boolean gameOver = false;
                repaint();
                int x = this.player.getX() / 10;
                int y = this.player.getY() / 10;
                if (this.bord[x][y].getKind() == Brick.EMPTY_BRICK) {
                    while (this.bord[x][y].getKind() != Brick.FULL_BRICK && index < 1) {
                        x = this.player.getX() / 10;
                        y = this.player.getY() / 10;
                        Brick newBrick = new Brick(Brick.TEMP_BRICK, x, y);
                        this.bord[x][y] = newBrick;
                        System.out.println(x + "," + y);
                        index++;
                        this.player.setInMotion(true);
                        this.player.getTail().add(newBrick);
                        if (this.player.checkTail()){
                           gameOver = true;
                            break;
                        }

                    }
                    index = 0;
                }
                if (this.bord[x][y].getKind() == Brick.FULL_BRICK) {
                    if (this.player.isInMotion()) {
                        updateBord();
                        this.victory();
                        this.player.setInMotion(false);
                        this.player.getTail().clear();
                    }

                }
                try {
                    Thread.sleep(GAME_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (gameOver){
                    gameOver();
                    break;
                }

            }
        });
        t1.start();
    }

    public void moveEnemy(){
        Thread t2 = new Thread(() -> {
            while (true) {
                boolean gameOver = false;
                int enemyX = this.enemy.getX();
                int enemyY = this.enemy.getY();
                this.enemy.moveEnemyCross(this.bord);
                if (this.bord[enemyX / 10 + 1][enemyY / 10].getKind() == Brick.TEMP_BRICK) {
                    gameOver = true;

                }
                if (this.bord[enemyX / 10][enemyY / 10 - 1].getKind() == Brick.TEMP_BRICK) {
                    gameOver = true;

                }
                if (this.bord[enemyX / 10][enemyY / 10 + 1].getKind() == Brick.TEMP_BRICK) {
                    gameOver = true;

                }
                    if (this.bord[enemyX / 10 - 1][enemyY / 10].getKind() == Brick.TEMP_BRICK) {
                        gameOver = true;

                    }
                    try {
                        Thread.sleep(ENEMY_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (gameOver){
                        gameOver();
                        break;
                    }
                }
        });
        t2.start();
    }

    public void createMatrix() {
        bord = new Brick[ROW][COL];
        for (int i = 0; i < this.bord.length; i++) {
            for (int j = 0; j < this.bord[i].length; j++) {
                if (i == 0 || i == bord.length - 1 || j == 0 || j == bord.length - 1) {
                    this.bord[i][j] = new Brick(Brick.FULL_BRICK, i, j);
                } else {
                    this.bord[i][j] = new Brick(Brick.EMPTY_BRICK, i, j);
                }
            }
        }
    }

    public void paintBord(Graphics g) {
        for (int i = 0; i < this.bord.length; i++) {
            for (int j = 0; j < this.bord[i].length; j++) {
                this.bord[i][j].paint(g, i * 10, j * 10);
                if (this.bord[i][j].getKind()==Brick.EMPTY_BRICK){
                    this.bord[i][j].setImageBrick( new ImageIcon("emptybrick.jpg"));
                    this.bord[i][j].getImageBrick().paintIcon(this.bord[i][j],g,i*10,j*10);
                }
                if (this.bord[i][j].getKind()==Brick.FULL_BRICK){
                    this.bord[i][j].setImageBrick( new ImageIcon("fullbrick.jpg"));
                    this.bord[i][j].getImageBrick().paintIcon(this.bord[i][j],g,i*10,j*10);
                }
            }
        }


    }

    public void updateBordTail(){
        int index = 0;
        int tailSize = this.player.getTail().size();
        while (index<tailSize){
            int x= this.player.getTail().get(index).getX();
            int y= this.player.getTail().get(index).getY();
            this.bord[x][y].setKind(Brick.FULL_BRICK);
            index++;
        }

    }

    public void updateBord() {
        updateBordTail();
        int startX = this.player.getTail().get(0).getX();
        int startY = this.player.getTail().get(0).getY();
        int endX = this.player.getTail().get(this.player.getTail().size() - 1).getX();
        int endY = this.player.getTail().get(this.player.getTail().size() - 1).getY();
        int direction = getDirection();
        System.out.println(direction);
        if (direction == RIGHT_DOWN) {
            for (int i = 0; i < this.player.getTail().size() - 1; i++) {
                if (this.player.getTail().get(i).getX() - this.player.getTail().get(i + 1).getX() == -1) {
                    while (this.bord[startX][startY + 1].getKind() == Brick.EMPTY_BRICK) {
                        this.bord[startX][startY + 1].setKind(Brick.FULL_BRICK);
                        startY++;
                    }
                    startX++;
                    startY = this.player.getTail().get(0).getY();
                    ;
                }
                if (this.player.getTail().get(i).getY() != this.player.getTail().get(i + 1).getY()) {
                    break;
                }

            }
        }
        if (direction == RIGHT_UP) {
            for (int i = 0; i < this.player.getTail().size() - 1; i++) {
                if (this.player.getTail().get(i).getX() - this.player.getTail().get(i + 1).getX() == -1) {
                    while (this.bord[startX][startY - 1].getKind() == Brick.EMPTY_BRICK) {
                        this.bord[startX][startY - 1].setKind(Brick.FULL_BRICK);
                        startY--;
                    }
                    startX++;
                    startY = this.player.getTail().get(0).getY();

                }
                if (this.player.getTail().get(i).getY() != this.player.getTail().get(i + 1).getY()) {
                    break;
                }

            }
        }
        if (direction == LEFT_DOWN) {
            for (int i = 0; i < this.player.getTail().size() - 1; i++) {
                if (this.player.getTail().get(i).getX() - this.player.getTail().get(i + 1).getX() == 1) {
                    while (this.bord[startX][startY + 1].getKind() == Brick.EMPTY_BRICK) {
                        this.bord[startX][startY + 1].setKind(Brick.FULL_BRICK);
                        startY++;
                    }
                    startX--;
                    startY = this.player.getTail().get(0).getY();

                }
                if (this.player.getTail().get(i).getY() != this.player.getTail().get(i + 1).getY()) {
                    break;
                }

            }
        }
        if (direction == LEFT_UP) {
            for (int i = 0; i < this.player.getTail().size() - 1; i++) {
                if (this.player.getTail().get(i).getX() - this.player.getTail().get(i + 1).getX() == 1) {
                    while (this.bord[startX][startY - 1].getKind() == Brick.EMPTY_BRICK) {
                        this.bord[startX][startY - 1].setKind(Brick.FULL_BRICK);
                        startY--;
                    }
                    startX--;
                    startY = this.player.getTail().get(0).getY();

                }
                if (this.player.getTail().get(i).getY() != this.player.getTail().get(i + 1).getY()) {
                    break;
                }

            }
        }
        if (direction == DOWN_RIGHT) {
            for (int i = 0; i < this.player.getTail().size() - 1; i++) {
                if (this.player.getTail().get(i).getY() - this.player.getTail().get(i + 1).getY() == -1) {
                    while (this.bord[startX+1][startY].getKind() == Brick.EMPTY_BRICK) {
                        this.bord[startX+1][startY].setKind(Brick.FULL_BRICK);
                        startX++;
                    }
                    startX=this.player.getTail().get(0).getX();
                    startY++;

                }
                if (this.player.getTail().get(i).getX() != this.player.getTail().get(i + 1).getX()) {
                    break;
                }

            }
        }
        if (direction == DOWN_LEFT) {
            for (int i = 0; i < this.player.getTail().size() - 1; i++) {
                if (this.player.getTail().get(i).getY() - this.player.getTail().get(i + 1).getY() == -1) {
                    while (this.bord[startX-1][startY].getKind() == Brick.EMPTY_BRICK) {
                        this.bord[startX-1][startY].setKind(Brick.FULL_BRICK);
                        startX--;
                    }
                    startX=this.player.getTail().get(0).getX();
                    startY++;

                }
                if (this.player.getTail().get(i).getX() != this.player.getTail().get(i + 1).getX()) {
                    break;
                }

            }
        }
        if (direction == UP_LEFT) {
            for (int i = 0; i < this.player.getTail().size() - 1; i++) {
                if (this.player.getTail().get(i).getY() - this.player.getTail().get(i + 1).getY() == 1) {
                    while (this.bord[startX-1][startY].getKind() == Brick.EMPTY_BRICK) {
                        this.bord[startX-1][startY].setKind(Brick.FULL_BRICK);
                        startX--;
                    }
                    startX=this.player.getTail().get(0).getX();
                    startY--;

                }
                if (this.player.getTail().get(i).getX() != this.player.getTail().get(i + 1).getX()) {
                    break;
                }

            }
        }
        if (direction == UP_RIGHT) {
            for (int i = 0; i < this.player.getTail().size() - 1; i++) {
                if (this.player.getTail().get(i).getY() - this.player.getTail().get(i + 1).getY() == 1) {
                    while (this.bord[startX+1][startY].getKind() == Brick.EMPTY_BRICK) {
                        this.bord[startX+1][startY].setKind(Brick.FULL_BRICK);
                        startX++;
                    }
                    startX=this.player.getTail().get(0).getX();
                    startY--;

                }
                if (this.player.getTail().get(i).getX() != this.player.getTail().get(i + 1).getX()) {
                    break;
                }

            }
        }
        completionMissingPoints(direction);
    }

    public void completionMissingPoints (int direction){
        int startX = this.player.getTail().get(0).getX();
        int startY = this.player.getTail().get(0).getY();
        int endX = this.player.getTail().get(this.player.getTail().size() - 1).getX();
        int endY = this.player.getTail().get(this.player.getTail().size() - 1).getY();
//         if (direction == RIGHT_UP || direction == RIGHT_DOWN )
//         {
//             startX=0;
//         }
//        if (direction == LEFT_DOWN || direction == LEFT_UP )
//        {
//            startX=ROW-1;
//        }

        for (int i=0;i<this.bord.length;i++){
            for (int j=0;j<this.bord.length;j++){
                if (this.bord[i][j].getKind()==Brick.EMPTY_BRICK){
                    if ((this.bord[i][j].getX()>=startX && this.bord[i][j].getX()<=endX) || (this.bord[i][j].getX()<=startX && this.bord[i][j].getX()>=endX)){
                        if ((this.bord[i][j].getY()>=startY && this.bord[i][j].getY()<=endY) || (this.bord[i][j].getY()<=startY && this.bord[i][j].getY()>=endY)){
                            this.bord[i][j].setKind(Brick.FULL_BRICK);
                        }

                        }
                }

            }
        }
    }

    public int getDirection(){
        int direction = 0;
        try {
            int directionUp = 0, directionDown = 0, directionRight = 0, directionLeft = 0;
            int directionUp1 = 0, directionDown1 = 0, directionRight1 = 0, directionLeft1 = 0;
            int index = 0;
            if (this.player.getTail().size() > 3) {
                if (this.player.getTail().get(0).getX() == this.player.getTail().get(1).getX()) {
                    if (this.player.getTail().get(0).getY() > this.player.getTail().get(1).getY()) {
                        directionUp = 1;
                    }
                    if (this.player.getTail().get(0).getY() < this.player.getTail().get(1).getY()) {
                        directionDown = 1;
                    }
                    while (index < this.player.getTail().size() - 2) {
                        if (this.player.getTail().get(index).getX() != this.player.getTail().get(index + 1).getX()) {
                            break;
                        }
                        index++;
                    }
                    if (this.player.getTail().get(index).getX() > this.player.getTail().get(index + 1).getX()) {
                        directionLeft1 = 1;
                    }
                    if (this.player.getTail().get(index).getX() < this.player.getTail().get(index + 1).getX()) {
                        directionRight1 = 1;
                    }

                }
                if (this.player.getTail().get(0).getY() == this.player.getTail().get(1).getY()) {
                    if (this.player.getTail().get(0).getX() > this.player.getTail().get(1).getX()) {
                        directionLeft = 1;
                    }
                    if (this.player.getTail().get(0).getX() < this.player.getTail().get(1).getX()) {
                        directionRight = 1;
                    }
                    while (index < this.player.getTail().size() - 2) {
                        if (this.player.getTail().get(index).getY() != this.player.getTail().get(index + 1).getY()) {
                            break;
                        }
                        index++;
                    }
                    if (this.player.getTail().get(index).getY() > this.player.getTail().get(index + 1).getY()) {
                        directionUp1 = 1;
                    }
                    if (this.player.getTail().get(index).getY() < this.player.getTail().get(index + 1).getY()) {
                        directionDown1 = 1;
                    }
                }
                if (directionDown == 1 && directionLeft1 == 1) {
                    direction = DOWN_LEFT;
                }
                if (directionDown == 1 && directionRight1 == 1) {
                    direction = DOWN_RIGHT;
                    return direction;
                }
                if (directionUp == 1 && directionRight1 == 1) {
                    direction = UP_RIGHT;
                    return direction;
                }
                if (directionUp == 1 && directionLeft1 == 1) {
                    direction = UP_LEFT;
                    return direction;
                }
                if (directionLeft == 1 && directionUp1 == 1) {
                    direction = LEFT_UP;
                    return direction;
                }
                if (directionLeft == 1 && directionDown1 == 1) {
                    direction = LEFT_DOWN;
                    return direction;
                }
                if (directionRight == 1 && directionDown1 == 1) {
                    direction = RIGHT_DOWN;
                    return direction;
                }
                if (directionRight == 1 && directionUp1 == 1) {
                    direction = RIGHT_UP;
                    return direction;
                }
                if (directionRight1 == 0 && directionLeft1 == 0) {
                    direction = UP_RIGHT;
                    return direction;
                }
                if (directionUp1 == 0 && directionDown1 == 0) {
                    direction = RIGHT_UP;
                    return direction;
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
                System.out.println(e);
            }
        System.out.println(direction);
        return direction;

        }

    public void gameOver(){
       this.finishImage = new ImageIcon("gameover_image.jpg");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("gameover_effect.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

    }

    public double calculateArea (){
        double countFull =0;
        double areaPercent = 0;
        for (int i=1;i<this.bord.length-1;i++) {
            for (int j = 1; j < this.bord.length-1; j++) {
                if (this.bord[i][j].getKind() == Brick.FULL_BRICK) {
                    countFull++;
                }
            }
        }
        areaPercent = countFull/(ROW*COL)*100;
        System.out.println(areaPercent);
        return areaPercent;
    }

    public void victory (){
        double areaPercent = calculateArea();
        if (areaPercent>=LEVEL_ONE){
            this.finishImage = new ImageIcon("youwin_image.jpg");
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("clapp_effect.wav").getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch(Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }

    }


    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("AirXonix_Music.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }


}
