package com.ericc.the.game.actions;

import com.ericc.the.game.Direction;
import com.ericc.the.game.components.Model;
import com.ericc.the.game.utils.GridPoint;

public class AreaOfEffectAttackAction extends Action {
    // bottom left corner of the area we want to attack (relative to the position of the entity who casts the effect)
    public GridPoint relativeStart;
    public Model model;
    public Direction direction;
    public int width;
    public int height;
    public int delay;
    public int power;
    //< indicates whether attack action is directed, if not the position of attack is determined by the relativeStart
    public boolean isDirected;

    public AreaOfEffectAttackAction(GridPoint relativeStart, Model model, Direction direction,
                                    int width, int height, int delay, int power) {
        this(model, direction, width, height, delay, power);
        this.relativeStart = relativeStart;
        this.isDirected = false;
    }

    public AreaOfEffectAttackAction(Model model, Direction direction,
                                    int width, int height, int delay, int power) {
        this.model = model;
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.delay = delay;
        this.power = power;
        this.isDirected = true;
    }

    @Override
    public int getDelay() {
        return delay;
    }
}
