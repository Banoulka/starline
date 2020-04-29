package Abstracts;

import Base.Utility.Utils;

public abstract class PlanetData {

    protected String age;
    protected String gravity;
    protected String size;

    public String getAge() {
        return age;
    }

    public String getGravity() {
        return gravity;
    }

    public String getSize() {
        return size;
    }

    public double getAgeNumber() {
        return Utils.strToNum(this.age);
    }

    public double getSizeNumber() {
        return Utils.strToNum(this.size);
    }

    public double getGravityNumber() {
        return Utils.strToNum(this.gravity);
    }
}
