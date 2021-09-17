// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.combat;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import me.alpha432.oyvey.util.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class SelfFill extends Module
{
    private final Setting<Boolean> packet;
    
    public SelfFill() {
        super("SelfFill", "SelfFills yourself in a hole.", Category.COMBAT, true, false, true);
        this.packet = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)Boolean.FALSE));
    }
    
    @Override
    public void onEnable() {
        SelfFill.mc.field_71439_g.func_70664_aZ();
        SelfFill.mc.field_71439_g.func_70664_aZ();
    }
    
    @Override
    public void onUpdate() {
        final BlockPos pos = new BlockPos(SelfFill.mc.field_71439_g.field_70165_t, SelfFill.mc.field_71439_g.field_70163_u, SelfFill.mc.field_71439_g.field_70161_v);
        if (SelfFill.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150350_a && BlockUtil.isPositionPlaceable(pos.func_177977_b(), false) == 3) {
            BlockUtil.placeBlock(pos.func_177977_b(), EnumHand.MAIN_HAND, false, this.packet.getValue(), false);
        }
        if (SelfFill.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150343_Z) {
            SelfFill.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(SelfFill.mc.field_71439_g.field_70165_t, SelfFill.mc.field_71439_g.field_70163_u - 1.3, SelfFill.mc.field_71439_g.field_70161_v, false));
            SelfFill.mc.field_71439_g.func_70107_b(SelfFill.mc.field_71439_g.field_70165_t, SelfFill.mc.field_71439_g.field_70163_u - 1.3, SelfFill.mc.field_71439_g.field_70161_v);
            this.toggle();
        }
    }
}
