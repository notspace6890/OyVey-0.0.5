// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;

public class Speed extends Module
{
    public Speed() {
        super("Speed", "Speed.", Category.MOVEMENT, true, false, false);
    }
    
    @Override
    public String getDisplayInfo() {
        return "Strafe";
    }
}
