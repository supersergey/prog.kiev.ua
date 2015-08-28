package mygroup;

import java.util.List;

/**
 * Created by user on 28.08.2015.
 */
public interface PizzaDAO {
    public List<Pizza> list();
    public void addPizza(Pizza pizza);
    public void addPizzas(List<Pizza> list);
}
