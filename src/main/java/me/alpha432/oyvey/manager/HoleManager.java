// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.manager;

import net.minecraft.block.Block;
import java.util.Iterator;
import net.minecraft.util.math.Vec3i;
import net.minecraft.init.Blocks;
import me.alpha432.oyvey.util.BlockUtil;
import net.minecraft.entity.player.EntityPlayer;
import me.alpha432.oyvey.util.EntityUtil;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.BlockPos;
import me.alpha432.oyvey.features.Feature;

public class HoleManager extends Feature
{
    private static final BlockPos[] surroundOffset;
    private List<BlockPos> holes;
    private List<BlockPos> midSafety;
    
    public HoleManager() {
        this.holes = new ArrayList<BlockPos>();
        this.midSafety = new ArrayList<BlockPos>();
    }
    
    public void update() {
        if (!Feature.fullNullCheck()) {
            this.holes = this.calcHoles();
        }
    }
    
    public List<BlockPos> getHoles() {
        return this.holes;
    }
    
    public List<BlockPos> getMidSafety() {
        return this.midSafety;
    }
    
    public List<BlockPos> getSortedHoles() {
        this.holes.sort(Comparator.comparingDouble(hole -> HoleManager.mc.field_71439_g.func_174818_b(hole)));
        return this.getHoles();
    }
    
    public List<BlockPos> calcHoles() {
        final List<BlockPos> safeSpots = new ArrayList<BlockPos>();
        this.midSafety.clear();
        final List<BlockPos> positions = BlockUtil.getSphere(EntityUtil.getPlayerPos((EntityPlayer)HoleManager.mc.field_71439_g), 6.0f, 6, false, true, 0);
        for (final BlockPos pos : positions) {
            if (!HoleManager.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!HoleManager.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!HoleManager.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            boolean isSafe = true;
            boolean midSafe = true;
            for (final BlockPos offset : HoleManager.surroundOffset) {
                final Block block = HoleManager.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)offset)).func_177230_c();
                if (BlockUtil.isBlockUnSolid(block)) {
                    midSafe = false;
                }
                if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                    isSafe = false;
                }
            }
            if (isSafe) {
                safeSpots.add(pos);
            }
            if (!midSafe) {
                continue;
            }
            this.midSafety.add(pos);
        }
        return safeSpots;
    }
    
    public boolean isSafe(final BlockPos pos) {
        boolean isSafe = true;
        for (final BlockPos offset : HoleManager.surroundOffset) {
            final Block block = HoleManager.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)offset)).func_177230_c();
            if (block != Blocks.field_150357_h) {
                isSafe = false;
                break;
            }
        }
        return isSafe;
    }
    
    static {
        surroundOffset = BlockUtil.toBlockPos(EntityUtil.getOffsets(0, true));
    }
}
