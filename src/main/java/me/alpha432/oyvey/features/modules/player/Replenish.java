// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import java.util.ArrayList;
import me.alpha432.oyvey.util.Timer;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class Replenish extends Module
{
    private final Setting<Integer> delay;
    private final Setting<Integer> gapStack;
    private final Setting<Integer> xpStackAt;
    private final Timer timer;
    private ArrayList<Item> Hotbar;
    
    public Replenish() {
        super("Replenish", "Replenishes your hotbar", Category.PLAYER, false, false, false);
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)0, (T)0, (T)10));
        this.gapStack = (Setting<Integer>)this.register(new Setting("GapStack", (T)1, (T)50, (T)64));
        this.xpStackAt = (Setting<Integer>)this.register(new Setting("XPStack", (T)1, (T)50, (T)64));
        this.timer = new Timer();
        this.Hotbar = new ArrayList<Item>();
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            return;
        }
        this.Hotbar.clear();
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = Replenish.mc.field_71439_g.field_71071_by.func_70301_a(l_I);
            if (!l_Stack.func_190926_b() && !this.Hotbar.contains(l_Stack.func_77973_b())) {
                this.Hotbar.add(l_Stack.func_77973_b());
            }
            else {
                this.Hotbar.add(Items.field_190931_a);
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (Replenish.mc.field_71462_r != null) {
            return;
        }
        if (!this.timer.passedMs(this.delay.getValue() * 1000)) {
            return;
        }
        for (int l_I = 0; l_I < 9; ++l_I) {
            if (this.RefillSlotIfNeed(l_I)) {
                this.timer.reset();
                return;
            }
        }
    }
    
    private boolean RefillSlotIfNeed(final int p_Slot) {
        final ItemStack l_Stack = Replenish.mc.field_71439_g.field_71071_by.func_70301_a(p_Slot);
        if (l_Stack.func_190926_b() || l_Stack.func_77973_b() == Items.field_190931_a) {
            return false;
        }
        if (!l_Stack.func_77985_e()) {
            return false;
        }
        if (l_Stack.func_190916_E() >= l_Stack.func_77976_d()) {
            return false;
        }
        if (l_Stack.func_77973_b().equals(Items.field_151153_ao) && l_Stack.func_190916_E() >= this.gapStack.getValue()) {
            return false;
        }
        if (l_Stack.func_77973_b().equals(Items.field_151062_by) && l_Stack.func_190916_E() > this.xpStackAt.getValue()) {
            return false;
        }
        for (int l_I = 9; l_I < 36; ++l_I) {
            final ItemStack l_Item = Replenish.mc.field_71439_g.field_71071_by.func_70301_a(l_I);
            if (!l_Item.func_190926_b()) {
                if (this.CanItemBeMergedWith(l_Stack, l_Item)) {
                    Replenish.mc.field_71442_b.func_187098_a(Replenish.mc.field_71439_g.field_71069_bz.field_75152_c, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)Replenish.mc.field_71439_g);
                    Replenish.mc.field_71442_b.func_78765_e();
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean CanItemBeMergedWith(final ItemStack p_Source, final ItemStack p_Target) {
        return p_Source.func_77973_b() == p_Target.func_77973_b() && p_Source.func_82833_r().equals(p_Target.func_82833_r());
    }
}
