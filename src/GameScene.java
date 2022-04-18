import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameScene extends JPanel {
    private Player player;
    private Enemy enemy;
    private Brick[][] bord;

    public static final int GAME_SCENE_WIDTH = 600;
    public static final int GAME_SCENE_HEIGHT = 600;
    public static final int GAME_SPEED = 10;
    public static final int ENEMY_SPEED = 100;
    public static final int ROW = 60;
    public static final int COL = 60;
    public static final int FILL_FROM_OUT = 1;
    public static final int FILL_FROM_INSIDE = 2;
    public static final int FILL_FROM_LEFT = 1;
    public static final int FILL_FROM_RIGHT = 2;
    public static final int FILL_FROM_UP = 3;
    public static final int FILL_FROM_DOWN = 4;
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
        this.player.getNinja().paintIcon(this,g,this.player.getX(),this.player.getY());
        this.enemy.setShurikan(new ImageIcon("shuriken.png"));
        this.enemy.getShurikan().paintIcon(this,g,this.enemy.getX(),this.enemy.getY());
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
            }
        }
    }

    public void update(){
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
        update();
        //int type = fillMethod();
        int startX = this.player.getTail().get(0).getX();
        int startY = this.player.getTail().get(0).getY();
        int endX = this.player.getTail().get(this.player.getTail().size() - 1).getX();
        int endY = this.player.getTail().get(this.player.getTail().size() - 1).getY();
        System.out.println(startX+","+startY+","+endX+","+endY);
        int index1=startX, index2=startY;
              while (index1<this.bord.length){
                  while (index2<endY) {
                      this.bord[index1][index2].setKind(Brick.FULL_BRICK);
                      index2++;
                  }
                  index1++;
                  index2 = startY;
                  if  (this.bord[index1][index2+1].getKind()==Brick.FULL_BRICK){
                      this.bord[index1][index2].setKind(Brick.FULL_BRICK);
                      break;
                  }
                  }
    }


    public int fillMethod() {
        int type = 0;
        int startX = this.player.getTail().get(0).getX();
        int startY = this.player.getTail().get(0).getY();
        int endX = this.player.getTail().get(this.player.getTail().size() - 1).getX();
        int endY = this.player.getTail().get(this.player.getTail().size() - 1).getY();
        int enemyX = this.enemy.getX();
        int enemyY = this.enemy.getY();
        if (startX == endX) {
            if ((enemyY > startY && enemyY > endY) || (enemyY < startY && enemyY < endY)) {
                type = FILL_FROM_INSIDE;
            } else {
                type = FILL_FROM_OUT;
            }
        }
        if (startY == endY) {
            if ((enemyX > startX && enemyX > endX) || (enemyX < startX && enemyX < endX)) {
                type = FILL_FROM_INSIDE;
            } else {
                type = FILL_FROM_OUT;
            }
        }
        if (startX != endX && startY != endY) {
            if ((enemyX > startX && enemyX > endX) || (enemyX < startX && enemyX < endX)) {
                type = FILL_FROM_INSIDE;
            } else {
                type = FILL_FROM_OUT;
            }

        }
        return type;

    }

    public int fillDirection() {
        int direction = 0;
        int startX = this.player.getTail().get(0).getX();
        int startY = this.player.getTail().get(0).getY();
        int endX = this.player.getTail().get(this.player.getTail().size() - 1).getX();
        int endY = this.player.getTail().get(this.player.getTail().size() - 1).getY();
            if (startX == endX) {
                if (startY > endY) {
                    direction = FILL_FROM_DOWN;
                }
                if (startY < endY) {
                    direction = FILL_FROM_UP;
                }
            }
            if (startY == endY) {
                if (startX > endY) {
                    direction = FILL_FROM_DOWN;
                }
                if (startY < endY) {
                    direction = FILL_FROM_UP;
                }
            }
return 0;
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
