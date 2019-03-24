package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.map.StaircaseDestination;

public class StaircaseDestinationComponent implements Component {
    public StaircaseDestination destination;

    public StaircaseDestinationComponent(StaircaseDestination destination) {
        this.destination = destination;
    }
}
