// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.player;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import me.alpha432.oyvey.util.InventoryUtil;
import net.minecraft.item.ItemEnderPearl;
import org.lwjgl.input.Mouse;
import me.alpha432.oyvey.features.modules.Module;

public class MCP extends Module
{
    private boolean clicked;
    
    public MCP() {
        super("MCP", "Throws a pearl", Category.PLAYER, false, false, false);
        this.clicked = false;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.disable();
        }
    }
    
    @Override
    public void onTick() {
        if (Mouse.isButtonDown(2)) {
            if (!this.clicked) {
                this.throwPearl();
            }
            this.clicked = true;
        }
        else {
            this.clicked = false;
        }
    }
    
    private void throwPearl() {
        final int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
        final boolean offhand = MCP.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151079_bi;
        if (pearlSlot != -1 || offhand) {
            final int oldslot = MCP.mc.field_71439_g.field_71071_by.field_70461_c;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            MCP.mc.field_71442_b.func_187101_a((EntityPlayer)MCP.mc.field_71439_g, (World)MCP.mc.field_71441_e, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }
}
