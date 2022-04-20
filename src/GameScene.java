import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Random;

public class GameScene extends JPanel {
    private Player player;
    private Enemy enemy;
    private Brick[][] bord;
    Polygon p = new Polygon();


    public static final int GAME_SCENE_WIDTH = 600;
    public static final int GAME_SCENE_HEIGHT = 600;
    public static final int GAME_SPEED = 10;
    public static final int ENEMY_SPEED = 100;
    public static final int ROW = 60;
    public static final int COL = 60;
    public static final int FILL_FROM_OUT = 1;
    public static final int FILL_FROM_INSIDE = 2;
    public static final int UP_LEFT = 1;
    public static final int UP_RIGHT = 2;
    public static final int DOWN_LEFT = 3;
    public static final int DOWN_RIGHT = 4;
    public static final int LEFT_UP = 5;
    public static final int RIGHT_UP = 6;
    public static final int LEFT_DOWN = 7;
    public static final int RIGHT_DOWN = 8;
    public static final double LEVEL_ONE = 0.1;



    public GameScene() {
        this.setBounds(0, 41, GAME_SCENE_WIDTH, GAME_SCENE_HEIGHT);
        this.setBackground(Color.RED);
        Random rnd = new Random();
        int randomX = rnd.nextInt(11, 590);
        int randomY = rnd.nextInt(11, 590);
        this.player = new Player(Brick.PLAYER_BRICK, 0, 40);
        this.createMatrix();
        this.enemy = new Enemy(randomX, randomY);
        this.setBackground(Color.RED);
        this.movePlayer();
        this.moveEnemy();
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this.player);
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
    }

    private void movePlayer() { //תזוזת שחקן
        Thread t1 = new Thread(() -> {
            int index = 0;
            int index2=0;
            this.player.setInMotion(false);
            while (true) {
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
                            gameOver();
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
            }
        });
        t1.start();
    }

    public void moveEnemy(){
        Thread t2 = new Thread(() -> {
            while (true){
                int enemyX=this.enemy.getX();
                int enemyY = this.enemy.getY();
                boolean injury = false;
                    while (this.bord[enemyX / 10+1][enemyY / 10+1].getKind() == Brick.EMPTY_BRICK) {
                        this.enemy.setX(enemyX += 10);
                        this.enemy.setY(enemyY += 10);
                        if (this.bord[enemyX / 10+1][enemyY / 10].getKind()==Brick.TEMP_BRICK){
                            gameOver();
                        }
                        if (this.bord[enemyX / 10+1][enemyY / 10].getKind()==Brick.FULL_BRICK)
                            break;
                        try {
                            Thread.sleep(ENEMY_SPEED);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (this.bord[enemyX / 10-1][enemyY / 10-1].getKind() == Brick.EMPTY_BRICK) {
                        this.enemy.setX(enemyX += 10);
                        this.enemy.setY(enemyY -= 10);
                        if (this.bord[enemyX / 10][enemyY / 10-1].getKind()==Brick.TEMP_BRICK) {
                            gameOver();
                        }
                        if (this.bord[enemyX / 10][enemyY / 10-1].getKind()==Brick.FULL_BRICK)
                            break;
                        try {
                            Thread.sleep(ENEMY_SPEED);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (this.bord[enemyX / 10-1][enemyY / 10+1].getKind() == Brick.EMPTY_BRICK) {
                        this.enemy.setX(enemyX -= 10);
                        this.enemy.setY(enemyY += 10);
                        if (this.bord[enemyX / 10][enemyY / 10+1].getKind()==Brick.TEMP_BRICK) {
                            gameOver();
                        }
                        if (this.bord[enemyX / 10][enemyY / 10+1].getKind()==Brick.FULL_BRICK)
                            break;
                        try {
                            Thread.sleep(ENEMY_SPEED);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (this.bord[enemyX / 10+1][enemyY / 10-1].getKind() == Brick.EMPTY_BRICK) {
                        this.enemy.setX(enemyX -= 10);
                        this.enemy.setY(enemyY -= 10);
                        if (this.bord[enemyX / 10-1][enemyY / 10].getKind()==Brick.TEMP_BRICK) {
                            gameOver();
                        }
                        if (this.bord[enemyX / 10-1][enemyY / 10].getKind()==Brick.FULL_BRICK) {
                            break;
                        }
                        try {
                            Thread.sleep(ENEMY_SPEED);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                try {
                    Thread.sleep(ENEMY_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        int enemyX = this.enemy.getX();
        int enemyY = this.enemy.getY();
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
    }


//        public void completionTail () {
//            int startX = this.player.getTail().get(0).getX();
//            int startY = this.player.getTail().get(0).getY();
//            int endX = this.player.getTail().get(this.player.getTail().size() - 1).getX();
//            int endY = this.player.getTail().get(this.player.getTail().size() - 1).getY();
//            if (startX > endX) {
//                while (startX > endX) {
//                    endY++;
//                    Brick newBrick = new Brick(Brick.TEMP_BRICK, startX, endY);
//                    this.player.getTail().add(newBrick);
//                    this.bord[endX][endY]=newBrick;
//                }
//            }
//            if (startX < endX) {
//                while (startX < endX) {
//                    endX--;
//                    Brick newBrick = new Brick(Brick.TEMP_BRICK, endX, endY);
//                    this.player.getTail().add(newBrick);
//                }
//            }
//            if (startY > endY) {
//                while (startY > endY) {
//                    endY++;
//                    Brick newBrick = new Brick(Brick.TEMP_BRICK, startX, endY);
//                    this.player.getTail().add(newBrick);
//                    this.bord[endX][endY] = newBrick;
//                }
//            }
//            if (startY < endY) {
//                while (startY < endY) {
//                    endX++;
//                    Brick newBrick = new Brick(Brick.TEMP_BRICK, endX, endY);
//                    this.player.getTail().add(newBrick);
//                    this.bord[endX][endY] = newBrick;
//                }
//            }
//        }



    public int getDirection(){
        int directionUp=0,directionDown=0,directionRight=0,directionLeft=0;
        int directionUp1=0,directionDown1=0,directionRight1=0,directionLeft1=0;
        int direction=0;
        int index=2;
        if (this.player.getTail().get(0).getX()==this.player.getTail().get(1).getX()){
            if (this.player.getTail().get(0).getY()>this.player.getTail().get(1).getY()){
                directionUp=1;
            }
            if (this.player.getTail().get(0).getY()<this.player.getTail().get(1).getY()){
                directionDown=1;
            }
            while (index<this.player.getTail().size()-1){
                if (this.player.getTail().get(index).getX()!=this.player.getTail().get(index+1).getX()){
                    break;
                }
                index++;
            }
            if (this.player.getTail().get(index).getX()>this.player.getTail().get(index+1).getX()){
                directionLeft1=1;
            }
            if (this.player.getTail().get(index).getX()<this.player.getTail().get(index+1).getX()){
                directionRight1=1;
            }

        }
        if (this.player.getTail().get(0).getY()==this.player.getTail().get(1).getY()){
            if (this.player.getTail().get(0).getX()>this.player.getTail().get(1).getX()){
                directionLeft=1;
            }
            if (this.player.getTail().get(0).getX()<this.player.getTail().get(1).getX()){
                directionRight=1;
        }
            while (index<this.player.getTail().size()-1){
                if (this.player.getTail().get(index).getY()!=this.player.getTail().get(index+1).getY()){
                    break;
                }
                index++;
            }
            if (this.player.getTail().get(index).getY()>this.player.getTail().get(index+1).getY()){
                directionUp1=1;
            }
            if (this.player.getTail().get(index).getY()<this.player.getTail().get(index+1).getY()){
                directionDown1=1;
            }
            }
        if (directionDown==1 && directionLeft1==1){
            direction = DOWN_LEFT;
        }
        if (directionDown==1 && directionRight1==1){
            direction = DOWN_RIGHT;
        }
        if (directionUp==1 && directionRight1==1){
            direction = UP_RIGHT;
        }
        if (directionUp==1 && directionLeft1==1){
            direction = UP_LEFT;
        }
        if (directionLeft==1 && directionUp1==1){
            direction = LEFT_UP;
        }
        if (directionLeft==1 && directionDown1==1){
            direction = LEFT_DOWN;
        }
        if (directionRight==1 && directionDown1==1){
            direction = RIGHT_DOWN;
        }
        if (directionRight==1 && directionUp1==1){
            direction = RIGHT_UP;
        }
        return direction;
    }

    public int fillMethod() {
        int type = 0;
        int enemyX = this.enemy.getX()/10;
        int enemyY = this.enemy.getY()/10;
        boolean enemyPlaceX = false, enemyPlaceY = false;
        for (int i=0;i<this.player.getTail().size();i++){
            if (this.player.getTail().get(i).getX()==enemyX){
                enemyPlaceX=true;
            }
            if (this.player.getTail().get(i).getY()==enemyY){
                enemyPlaceY=true;
            }
        }
        if (enemyPlaceX&&enemyPlaceY){
            type = FILL_FROM_OUT;
        }
        else {
            type = FILL_FROM_INSIDE;
        }
        return type;

    }

    public void gameOver(){
        JLabel title = new JLabel("Game Over");
        title.setBounds(50, 50, 50, 50);
        Font font = new Font("Arial", Font.BOLD, 20);
        title.setForeground(Color.BLACK);
        title.setFont(font);
        this.add(title);
    }

    public double calculateArea (){
        double countFull =0;
        double areaPercent = 0;
        for (int i=0;i<this.bord.length;i++) {
            for (int j = 0; j < this.bord.length; j++) {
                if (this.bord[i][j].getKind() == Brick.FULL_BRICK) {
                    countFull++;
                }
            }
        }
        areaPercent = countFull/(ROW*COL);
        System.out.println(areaPercent);
        return areaPercent;
    }

    public void victory (){
        double areaPercent = calculateArea();
        if (areaPercent>=LEVEL_ONE){
            JLabel title = new JLabel("VICTORY");
            title.setBounds(50, 50, 50, 50);
            Font font = new Font("Arial", Font.BOLD, 20);
            title.setForeground(Color.RED);
            title.setFont(font);
            this.add(title);
        }
    }



}
