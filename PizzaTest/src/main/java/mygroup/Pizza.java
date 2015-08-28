package mygroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28.08.2015.
 */
public class Pizza {
    private long id;
    private String name;
    private String description;

    private List<PizzaVariant> pizzaVariants = new ArrayList<PizzaVariant>();

    public Pizza() {
    }

    public Pizza(long id, String name, List<PizzaVariant> pizzaVariants) {
        this.id = id;
        this.name = name;
        this.pizzaVariants = pizzaVariants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PizzaVariant> getPizzaVariants() {
        return pizzaVariants;
    }

    public void setPizzaVariants(List<PizzaVariant> pizzaVariants) {
        this.pizzaVariants = pizzaVariants;
    }

    public void addPizzaVariant(PizzaVariant pizzaVariant) {
        pizzaVariants.add(pizzaVariant);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Pizza: " + this.name +
        "\r\nDescription: " + this.description +
                "\r\nVariants: ");
        for (PizzaVariant pv : pizzaVariants)
        {
            result.append("\r\nVariant: ");
            result.append(pv);
        }
        return result.toString();
    }
}



