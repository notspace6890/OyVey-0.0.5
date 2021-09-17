// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.misc;

import net.minecraft.entity.Entity;
import me.alpha432.oyvey.features.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.alpha432.oyvey.OyVey;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import me.alpha432.oyvey.features.modules.Module;

public class MCF extends Module
{
    private boolean clicked;
    
    public MCF() {
        super("MCF", "Middleclick Friends.", Category.MISC, true, false, false);
        this.clicked = false;
    }
    
    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown(2)) {
            if (!this.clicked && MCF.mc.field_71462_r == null) {
                this.onClick();
            }
            this.clicked = true;
        }
        else {
            this.clicked = false;
        }
    }
    
    private void onClick() {
        final RayTraceResult result = MCF.mc.field_71476_x;
        if (result != null && result.field_72313_a == RayTraceResult.Type.ENTITY) {
            final Entity entity = result.field_72308_g;
            if (entity instanceof EntityPlayer) {
                if (OyVey.friendManager.isFriend(entity.func_70005_c_())) {
                    OyVey.friendManager.removeFriend(entity.func_70005_c_());
                    Command.sendMessage(ChatFormatting.RED + entity.func_70005_c_() + ChatFormatting.RED + " has been unfriended.");
                }
                else {
                    OyVey.friendManager.addFriend(entity.func_70005_c_());
                    Command.sendMessage(ChatFormatting.AQUA + entity.func_70005_c_() + ChatFormatting.AQUA + " has been friended.");
                }
            }
        }
        this.clicked = true;
    }
}
