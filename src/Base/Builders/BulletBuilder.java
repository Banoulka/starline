package Base.Builders;

import Base.Utility.Coord;
import Base.GameObjects.Bullet;
import Base.Interfaces.IGameObjectBuilderEmpty;


public class BulletBuilder extends GameObjectBuilder<Bullet, BulletBuilder>
        implements IGameObjectBuilderEmpty<Bullet> {

    private Bullet bullet = new Bullet();

    public BulletBuilder setOrigin(Coord origin) {
        bullet.setOrigin(origin);
        return this;
    }

    public BulletBuilder setDirection(double direction) {
        bullet.setDirection(direction);
        return this;
    }

    @Override
    protected Bullet getObj() {
        return bullet;
    }

    @Override
    protected BulletBuilder self() {
        return this;
    }

    @Override
    public void clear() {
        bullet = new Bullet();
    }

    @Override
    public Bullet build() {

        // Set the image to the bullet image
        bullet.setImg("bullet");

        // Set the correct size
        bullet.setGoHeight(0.4);
        bullet.setGoWidth(0.1);

        return bullet;
    }

    // UNSUPPORTED METHODS FOR BULLET ==============
    // DO NOT USE

    @Override
    public BulletBuilder image(String imageName) {
        throw new UnsupportedOperationException("Cannot change image of bullet");
    }

    @Override
    public BulletBuilder sizeSquare(double size) {
        throw new UnsupportedOperationException("Cannot change size of Bullet");
    }

    @Override
    public BulletBuilder sizeRect(double goWidth, double goHeight) {
        throw new UnsupportedOperationException("Cannot change size of Bullet");
    }

    @Override
    public BulletBuilder position(Coord coord) {
        throw new UnsupportedOperationException("Cannot change position of Bullet");
    }

    @Override
    public Bullet buildEmpty() {
        bullet.setImg("bullet");

        // Set the correct size
        bullet.setGoHeight(0.4);
        bullet.setGoWidth(0.1);

        Bullet bulletToReturn = bullet;
        clear();
        return bulletToReturn;
    }

    @Override
    public Bullet resetValues(Bullet gameObject) {
        clear();
        return buildEmpty();
    }
}
