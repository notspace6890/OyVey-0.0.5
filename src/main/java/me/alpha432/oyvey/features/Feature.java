// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features;

import java.util.Iterator;
import java.util.Collection;
import me.alpha432.oyvey.features.gui.OyVeyGui;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.OyVey;
import java.util.ArrayList;
import me.alpha432.oyvey.manager.TextManager;
import me.alpha432.oyvey.features.setting.Setting;
import java.util.List;
import me.alpha432.oyvey.util.Util;

public class Feature implements Util
{
    public List<Setting> settings;
    public TextManager renderer;
    private String name;
    
    public Feature() {
        this.settings = new ArrayList<Setting>();
        this.renderer = OyVey.textManager;
    }
    
    public Feature(final String name) {
        this.settings = new ArrayList<Setting>();
        this.renderer = OyVey.textManager;
        this.name = name;
    }
    
    public static boolean nullCheck() {
        return Feature.mc.field_71439_g == null;
    }
    
    public static boolean fullNullCheck() {
        return Feature.mc.field_71439_g == null || Feature.mc.field_71441_e == null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<Setting> getSettings() {
        return this.settings;
    }
    
    public boolean hasSettings() {
        return !this.settings.isEmpty();
    }
    
    public boolean isEnabled() {
        return this instanceof Module && ((Module)this).isOn();
    }
    
    public boolean isDisabled() {
        return !this.isEnabled();
    }
    
    public Setting register(final Setting setting) {
        setting.setFeature(this);
        this.settings.add(setting);
        if (this instanceof Module && Feature.mc.field_71462_r instanceof OyVeyGui) {
            OyVeyGui.getInstance().updateModule((Module)this);
        }
        return setting;
    }
    
    public void unregister(final Setting settingIn) {
        final List<Setting> removeList = new ArrayList<Setting>();
        for (final Setting setting : this.settings) {
            if (setting.equals(settingIn)) {
                removeList.add(setting);
            }
        }
        if (!removeList.isEmpty()) {
            this.settings.removeAll(removeList);
        }
        if (this instanceof Module && Feature.mc.field_71462_r instanceof OyVeyGui) {
            OyVeyGui.getInstance().updateModule((Module)this);
        }
    }
    
    public Setting getSettingByName(final String name) {
        for (final Setting setting : this.settings) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }
    
    public void reset() {
        for (final Setting setting : this.settings) {
            setting.setValue(setting.getDefaultValue());
        }
    }
    
    public void clearSettings() {
        this.settings = new ArrayList<Setting>();
    }
}
