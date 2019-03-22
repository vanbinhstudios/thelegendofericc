package com.ericc.the.game.map;

import com.ericc.the.game.Engines;

public class CurrentMap {
    public static Map map;

    public static void setMap(Map anotherMap, Engines engines) {
        CurrentMap.map = anotherMap;
        engines.updateLogicEngine();
    }

    public static void setMap(Map anotherMap) {
        CurrentMap.map = anotherMap;
    }
}
