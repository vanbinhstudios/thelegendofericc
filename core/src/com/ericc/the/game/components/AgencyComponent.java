package com.ericc.the.game.components;

import com.badlogic.ashley.core.Component;
import com.ericc.the.game.agencies.Agency;

public class AgencyComponent implements Component {
    public Agency agency;
    public int initiative = 0;

    public AgencyComponent(Agency agency) {
        this.agency = agency;
    }
}
