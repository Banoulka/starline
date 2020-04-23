package Abstracts;


import Base.Coord;

public abstract class Projectile extends GameObject {

    protected double direction;
    protected float speed;
    protected double moveX, moveY;

    public void setOrigin(Coord origin) {
        this.position = origin;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;

        // Set the moveX and moveY based on where the bullet is going
        double radAngle = Math.toRadians(this.direction);

        this.setMoveX(Math.cos(radAngle) * 2);
        this.setMoveY(Math.sin(radAngle) * 2);

        this.setRotate(this.direction - 90);
    }

    public void setMoveX(double moveX) {
        this.moveX = moveX;
    }

    public void setMoveY(double moveY) {
        this.moveY = moveY;
    }

    @Override
    public void update() {
        this.position.x += moveX * speed;
        this.position.y += moveY * speed;
        super.update();
    }
}
