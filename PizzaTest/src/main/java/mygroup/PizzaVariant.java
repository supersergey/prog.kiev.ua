package mygroup;

/**
 * Created by user on 28.08.2015.
 */
class PizzaVariant {

    long id;
    private float price;
    private int weight;
    private int diameter;

    public PizzaVariant() {
    }

    public PizzaVariant(float price, int weight, int diameter) {
        this.price = price;
        this.weight = weight;
        this.diameter = diameter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    @Override
    public String toString() {
        return "\r\nPrice: " + price +
                "\r\nWeight: " + weight +
                "\r\nDiameter: " + diameter;
    }
}