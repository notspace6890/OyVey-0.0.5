// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.features.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import me.alpha432.oyvey.features.setting.Setting;
import me.alpha432.oyvey.features.modules.Module;

public class ReverseStep extends Module
{
    private final Setting<Boolean> twoBlocks;
    private static ReverseStep INSTANCE;
    
    public ReverseStep() {
        super("ReverseStep", "ReverseStep.", Category.MOVEMENT, true, false, false);
        this.twoBlocks = (Setting<Boolean>)this.register(new Setting("2Blocks", (T)Boolean.FALSE));
        this.setInstance();
    }
    
    private void setInstance() {
        ReverseStep.INSTANCE = this;
    }
    
    public static ReverseStep getInstance() {
        if (ReverseStep.INSTANCE == null) {
            ReverseStep.INSTANCE = new ReverseStep();
        }
        return ReverseStep.INSTANCE;
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        final IBlockState touchingState = ReverseStep.mc.field_71441_e.func_180495_p(new BlockPos(ReverseStep.mc.field_71439_g.field_70165_t, ReverseStep.mc.field_71439_g.field_70163_u, ReverseStep.mc.field_71439_g.field_70161_v).func_177979_c(2));
        final IBlockState touchingState2 = ReverseStep.mc.field_71441_e.func_180495_p(new BlockPos(ReverseStep.mc.field_71439_g.field_70165_t, ReverseStep.mc.field_71439_g.field_70163_u, ReverseStep.mc.field_71439_g.field_70161_v).func_177979_c(3));
        if (ReverseStep.mc.field_71439_g.func_180799_ab() || ReverseStep.mc.field_71439_g.func_70090_H()) {
            return;
        }
        if (touchingState.func_177230_c() == Blocks.field_150357_h || touchingState.func_177230_c() == Blocks.field_150343_Z) {
            if (ReverseStep.mc.field_71439_g.field_70122_E) {
                final EntityPlayerSP field_71439_g = ReverseStep.mc.field_71439_g;
                --field_71439_g.field_70181_x;
            }
        }
        else if (((this.twoBlocks.getValue() && touchingState2.func_177230_c() == Blocks.field_150357_h) || (this.twoBlocks.getValue() && touchingState2.func_177230_c() == Blocks.field_150343_Z)) && ReverseStep.mc.field_71439_g.field_70122_E) {
            final EntityPlayerSP field_71439_g2 = ReverseStep.mc.field_71439_g;
            --field_71439_g2.field_70181_x;
        }
    }
    
    static {
        ReverseStep.INSTANCE = new ReverseStep();
    }
}
