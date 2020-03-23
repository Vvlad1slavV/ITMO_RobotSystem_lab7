package com.itmo.r3135.Commands;

import com.itmo.r3135.Control;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

/**
 * Класс обработки комадны show
 */

public class ShowCommand extends AbstractCommand {

    public ShowCommand(Control control) {
        super(control);
    }

    /**
     * Функция выводит на экран все элементы коллекции.
     */
    @Override
    public void activate(String s) {
        HashSet<Product> products = control.getProducts();
        if (products.size() != 0)
            for (Product product : products) System.out.println(product);
        else System.out.println("Коллекция пуста.");
    }
}
