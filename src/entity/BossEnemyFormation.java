package entity;

import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import screen.Screen;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager;
import engine.DrawManager.SpriteType;


public class BossEnemyFormation extends Entity {
    private int speed;
    private int level;
    private EnemyShip BossShip;
    private DrawManager drawManager;
    private int movecooldown = 60;
    private int randomX;
    private int randomY;
    private Cooldown shootingCooldown;
    private int  width;
    private int height;


    public BossEnemyFormation(final int positionX, final int positionY, final int width, final int height, final int speed,EnemyShip E) {
        super(positionX, positionY, width, height, Color.green);
        this.speed = speed;
        this.BossShip = E;
        this.shootingCooldown = Core.getCooldown(200);
        this.width = width;
        this.height = height;

        setSpecialSprite();


    }

    public final void draw() {
                this.drawManager = Core.getDrawManager();
                drawManager.drawEntity(BossShip, BossShip.getPositionX(),
                        BossShip.getPositionY());
    }

    public final void setSpecialSprite() {
        this.spriteType = SpriteType.Ship;
    }


    //boss formation
    public final void update() {

        int[] nums = { 1 ,-1 };

        if(movecooldown == 60){
            movecooldown = 0;
            randomX =nums[ (int) (Math.random() * nums.length)];
            randomY =nums[ (int) (Math.random() * nums.length)];
            if(this.BossShip.getPositionY() > Core.getHight() / 2){
                randomY = -1;
            }else if(this.BossShip.getPositionY() < Core.getHight() / 10){
                randomY =1;
            }

            if(this.BossShip.getPositionX() < Core.getWidth() / 10){
                randomX = 1;
            }else if(this.BossShip.getPositionX() > Core.getWidth()* 9 / 10 ){
                randomX = -1;
            }
            System.out.print(this.BossShip.getPositionX());
            System.out.println(this.BossShip.getPositionY());

        }else{
            this.BossShip.move( randomX,randomY);
            movecooldown++;

        }




    }

    public final boolean shoot(final Set<BossBullet> bullets,int BossPositionX,int BoosPositionY) {
        if (this.shootingCooldown.checkFinished()) {
            this.shootingCooldown.reset();
            bullets.add(BossBulletPool.getBullet(BossPositionX , BoosPositionY + this.height, 4,"left"));
            bullets.add(BossBulletPool.getBullet(BossPositionX + this.width / 2, BoosPositionY + this.height, 4,"mid"));
            bullets.add(BossBulletPool.getBullet(BossPositionX + this.width , BoosPositionY + this.height, 4,"right"));

            bullets.add(BossBulletPool.getBullet(BossPositionX , BoosPositionY + this.height, -4,"left"));
            bullets.add(BossBulletPool.getBullet(BossPositionX + this.width / 2, BoosPositionY + this.height, -4,"mid"));
            bullets.add(BossBulletPool.getBullet(BossPositionX + this.width , BoosPositionY + this.height, -4,"right"));
            return true;
        }
        return false;
    }
}
