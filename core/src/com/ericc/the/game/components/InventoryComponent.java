package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.List;

public class InventoryComponent implements Component {
    public List<Item> items = new ArrayList<>();
    public int chosen = 0;
}
