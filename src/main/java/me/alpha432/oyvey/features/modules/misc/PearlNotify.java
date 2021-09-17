// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.misc;

import java.util.Iterator;
import me.alpha432.oyvey.features.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.alpha432.oyvey.OyVey;
import net.minecraft.entity.item.EntityEnderPearl;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import net.minecraft.entity.Entity;
import me.alpha432.oyvey.features.modules.Module;

public class PearlNotify extends Module
{
    private Entity enderPearl;
    private boolean flag;
    private final HashMap<EntityPlayer, UUID> list;
    
    public PearlNotify() {
        super("PearlNotify", "Notify pearl throws.", Category.MISC, true, false, false);
        this.list = new HashMap<EntityPlayer, UUID>();
    }
    
    @Override
    public void onEnable() {
        this.flag = true;
    }
    
    @Override
    public void onUpdate() {
        if (PearlNotify.mc.field_71441_e == null || PearlNotify.mc.field_71439_g == null) {
            return;
        }
        this.enderPearl = null;
        for (final Entity e : PearlNotify.mc.field_71441_e.field_72996_f) {
            if (e instanceof EntityEnderPearl) {
                this.enderPearl = e;
                break;
            }
        }
        if (this.enderPearl == null) {
            this.flag = true;
            return;
        }
        EntityPlayer closestPlayer = null;
        for (final EntityPlayer entity : PearlNotify.mc.field_71441_e.field_73010_i) {
            if (closestPlayer == null) {
                closestPlayer = entity;
            }
            else {
                if (closestPlayer.func_70032_d(this.enderPearl) <= entity.func_70032_d(this.enderPearl)) {
                    continue;
                }
                closestPlayer = entity;
            }
        }
        if (closestPlayer == PearlNotify.mc.field_71439_g) {
            this.flag = false;
        }
        if (closestPlayer != null && this.flag) {
            String faceing = this.enderPearl.func_174811_aO().toString();
            if (faceing.equals("west")) {
                faceing = "east";
            }
            else if (faceing.equals("east")) {
                faceing = "west";
            }
            Command.sendMessage(OyVey.friendManager.isFriend(closestPlayer.func_70005_c_()) ? (ChatFormatting.AQUA + closestPlayer.func_70005_c_() + ChatFormatting.DARK_GRAY + " has just thrown a pearl heading " + faceing + "!") : (ChatFormatting.RED + closestPlayer.func_70005_c_() + ChatFormatting.DARK_GRAY + " has just thrown a pearl heading " + faceing + "!"));
            this.flag = false;
        }
    }
}
