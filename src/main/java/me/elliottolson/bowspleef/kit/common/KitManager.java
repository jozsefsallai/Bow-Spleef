package me.elliottolson.bowspleef.kit.common;

import me.elliottolson.bowspleef.kit.common.Kit;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Elliott Olson (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class KitManager {

    public static List<Kit> getKits = new ArrayList<Kit>();

    public static Kit getKit(String name){
        for (Kit kit : getKits){
            if (kit.getName().equalsIgnoreCase(name)){
                return kit;
            }
        }
        return null;
    }

}
