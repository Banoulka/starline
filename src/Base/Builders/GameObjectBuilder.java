package Base.Builders;

import Abstracts.GameObject;
import Base.Coord;

public abstract class GameObjectBuilder
        <O extends GameObject, SELF extends GameObjectBuilder<O, SELF>> {

    protected abstract O getObj();
    protected abstract SELF self();

    public abstract void clear();
    public abstract O build();

    public SELF image(String imageName) {
        getObj().setImg(imageName);
        return self();
    }

    public SELF sizeSquare(double size) {
        getObj().setGoWidth(size);
        getObj().setGoHeight(size);
        return self();
    }

    public SELF sizeRect(double goWidth, double goHeight) {
        getObj().setGoWidth(goWidth);
        getObj().setGoHeight(goHeight);
        return self();
    }

    public SELF position(Coord coord) {
        getObj().setPosition(coord);
        return self();
    }
}
