// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemPickaxe;
import me.alpha432.oyvey.features.modules.misc.NoHitBox;
import java.util.List;
import com.google.common.base.Predicate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer
{
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
        if (NoHitBox.getINSTANCE().isOn() && ((Minecraft.func_71410_x().field_71439_g.func_184614_ca().func_77973_b() instanceof ItemPickaxe && NoHitBox.getINSTANCE().pickaxe.getValue()) || (Minecraft.func_71410_x().field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP && NoHitBox.getINSTANCE().crystal.getValue()) || (Minecraft.func_71410_x().field_71439_g.func_184614_ca().func_77973_b() == Items.field_151153_ao && NoHitBox.getINSTANCE().gapple.getValue()) || Minecraft.func_71410_x().field_71439_g.func_184614_ca().func_77973_b() == Items.field_151033_d || Minecraft.func_71410_x().field_71439_g.func_184614_ca().func_77973_b() == Items.field_151142_bV)) {
            return new ArrayList<Entity>();
        }
        return (List<Entity>)worldClient.func_175674_a(entityIn, boundingBox, predicate);
    }
}
