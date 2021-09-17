// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.mixin.mixins;

import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.entity.RenderDragon;
import me.alpha432.oyvey.util.ColorUtil;
import org.lwjgl.opengl.GL11;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.features.modules.render.Wireframe;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.model.ModelBase;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.client.renderer.entity.Render;

@Mixin({ RenderEnderCrystal.class })
public class MixinRenderEnderCrystal extends Render<EntityEnderCrystal>
{
    @Shadow
    private static final ResourceLocation field_110787_a;
    @Shadow
    private final ModelBase field_76995_b;
    @Shadow
    private final ModelBase field_188316_g;
    
    protected MixinRenderEnderCrystal(final RenderManager renderManager) {
        super(renderManager);
        this.field_76995_b = (ModelBase)new ModelEnderCrystal(0.0f, true);
        this.field_188316_g = (ModelBase)new ModelEnderCrystal(0.0f, false);
    }
    
    @Overwrite
    public void func_76986_a(final EntityEnderCrystal entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final float f = entity.field_70261_a + partialTicks;
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)x, (float)y, (float)z);
        this.func_110776_a(MixinRenderEnderCrystal.field_110787_a);
        float f2 = MathHelper.func_76126_a(f * 0.2f) / 2.0f + 0.5f;
        f2 += f2 * f2;
        if (this.field_188301_f) {
            GlStateManager.func_179142_g();
            GlStateManager.func_187431_e(this.func_188298_c((Entity)entity));
        }
        if (Wireframe.getINSTANCE().isOn() && Wireframe.getINSTANCE().crystals.getValue()) {
            final float red = ClickGui.getInstance().red.getValue() / 255.0f;
            final float green = ClickGui.getInstance().green.getValue() / 255.0f;
            final float blue = ClickGui.getInstance().blue.getValue() / 255.0f;
            if (Wireframe.getINSTANCE().cMode.getValue().equals(Wireframe.RenderMode.WIREFRAME) && Wireframe.getINSTANCE().crystalModel.getValue()) {
                this.field_188316_g.func_78088_a((Entity)entity, 0.0f, f * 3.0f, f2 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            GlStateManager.func_179094_E();
            GL11.glPushAttrib(1048575);
            if (Wireframe.getINSTANCE().cMode.getValue().equals(Wireframe.RenderMode.WIREFRAME)) {
                GL11.glPolygonMode(1032, 6913);
            }
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            if (Wireframe.getINSTANCE().cMode.getValue().equals(Wireframe.RenderMode.WIREFRAME)) {
                GL11.glEnable(2848);
            }
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glColor4f(((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() / 255.0f) : red, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() / 255.0f) : green, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() / 255.0f) : blue, Wireframe.getINSTANCE().cAlpha.getValue() / 255.0f);
            if (Wireframe.getINSTANCE().cMode.getValue().equals(Wireframe.RenderMode.WIREFRAME)) {
                GL11.glLineWidth((float)Wireframe.getINSTANCE().crystalLineWidth.getValue());
            }
            this.field_188316_g.func_78088_a((Entity)entity, 0.0f, f * 3.0f, f2 * 0.2f, 0.0f, 0.0f, 0.0625f);
            GL11.glDisable(2896);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glColor4f(((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() / 255.0f) : red, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() / 255.0f) : green, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() / 255.0f) : blue, Wireframe.getINSTANCE().cAlpha.getValue() / 255.0f);
            if (Wireframe.getINSTANCE().cMode.getValue().equals(Wireframe.RenderMode.WIREFRAME)) {
                GL11.glLineWidth((float)Wireframe.getINSTANCE().crystalLineWidth.getValue());
            }
            this.field_188316_g.func_78088_a((Entity)entity, 0.0f, f * 3.0f, f2 * 0.2f, 0.0f, 0.0f, 0.0625f);
            GlStateManager.func_179126_j();
            GlStateManager.func_179099_b();
            GlStateManager.func_179121_F();
        }
        else {
            this.field_188316_g.func_78088_a((Entity)entity, 0.0f, f * 3.0f, f2 * 0.2f, 0.0f, 0.0f, 0.0625f);
        }
        if (this.field_188301_f) {
            GlStateManager.func_187417_n();
            GlStateManager.func_179119_h();
        }
        GlStateManager.func_179121_F();
        final BlockPos blockpos = entity.func_184518_j();
        if (blockpos != null) {
            this.func_110776_a(RenderDragon.field_110843_g);
            final float f3 = blockpos.func_177958_n() + 0.5f;
            final float f4 = blockpos.func_177956_o() + 0.5f;
            final float f5 = blockpos.func_177952_p() + 0.5f;
            final double d0 = f3 - entity.field_70165_t;
            final double d2 = f4 - entity.field_70163_u;
            final double d3 = f5 - entity.field_70161_v;
            RenderDragon.func_188325_a(x + d0, y - 0.3 + f2 * 0.4f + d2, z + d3, partialTicks, (double)f3, (double)f4, (double)f5, entity.field_70261_a, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
        }
        super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
    }
    
    @Nullable
    protected ResourceLocation getEntityTexture(final EntityEnderCrystal entityEnderCrystal) {
        return null;
    }
    
    static {
        field_110787_a = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    }
}
