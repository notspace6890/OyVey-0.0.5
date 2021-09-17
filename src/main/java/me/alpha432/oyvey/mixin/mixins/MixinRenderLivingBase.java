// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.mixin.mixins;

import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.client.renderer.OpenGlHelper;
import me.alpha432.oyvey.util.ColorUtil;
import net.minecraft.client.Minecraft;
import me.alpha432.oyvey.OyVey;
import org.lwjgl.opengl.GL11;
import me.alpha432.oyvey.features.modules.client.ClickGui;
import me.alpha432.oyvey.features.modules.render.Wireframe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.renderer.entity.RenderManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLivingBase;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    protected ModelBase field_77045_g;
    @Shadow
    private static final Logger field_147923_a;
    @Shadow
    protected boolean field_188323_j;
    float red;
    float green;
    float blue;
    
    @Shadow
    protected abstract boolean func_193115_c(final EntityLivingBase p0);
    
    @Shadow
    protected abstract float func_77040_d(final T p0, final float p1);
    
    @Shadow
    protected abstract float func_77034_a(final float p0, final float p1, final float p2);
    
    @Shadow
    protected abstract float func_77044_a(final T p0, final float p1);
    
    @Shadow
    protected abstract void func_77043_a(final T p0, final float p1, final float p2, final float p3);
    
    @Shadow
    public abstract float func_188322_c(final T p0, final float p1);
    
    @Shadow
    protected abstract void func_180565_e();
    
    @Shadow
    protected abstract boolean func_177088_c(final T p0);
    
    @Shadow
    protected abstract void func_77039_a(final T p0, final double p1, final double p2, final double p3);
    
    @Shadow
    protected abstract void func_177091_f();
    
    @Shadow
    protected abstract void func_77036_a(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6);
    
    @Shadow
    protected abstract void func_177093_a(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    @Shadow
    protected abstract boolean func_177090_c(final T p0, final float p1);
    
    protected MixinRenderLivingBase(final RenderManager renderManager) {
        super(renderManager);
        this.red = 0.0f;
        this.green = 0.0f;
        this.blue = 0.0f;
    }
    
    @Overwrite
    public void func_76986_a(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        if (!MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Pre((EntityLivingBase)entity, (RenderLivingBase)RenderLivingBase.class.cast(this), partialTicks, x, y, z))) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179129_p();
            this.field_77045_g.field_78095_p = this.func_77040_d(entity, partialTicks);
            final boolean shouldSit = entity.func_184218_aH() && entity.func_184187_bx() != null && entity.func_184187_bx().shouldRiderSit();
            this.field_77045_g.field_78093_q = shouldSit;
            this.field_77045_g.field_78091_s = entity.func_70631_g_();
            try {
                float f = this.func_77034_a(entity.field_70760_ar, entity.field_70761_aq, partialTicks);
                final float f2 = this.func_77034_a(entity.field_70758_at, entity.field_70759_as, partialTicks);
                float f3 = f2 - f;
                if (shouldSit && entity.func_184187_bx() instanceof EntityLivingBase) {
                    final EntityLivingBase entitylivingbase = (EntityLivingBase)entity.func_184187_bx();
                    f = this.func_77034_a(entitylivingbase.field_70760_ar, entitylivingbase.field_70761_aq, partialTicks);
                    f3 = f2 - f;
                    float f4 = MathHelper.func_76142_g(f3);
                    if (f4 < -85.0f) {
                        f4 = -85.0f;
                    }
                    if (f4 >= 85.0f) {
                        f4 = 85.0f;
                    }
                    f = f2 - f4;
                    if (f4 * f4 > 2500.0f) {
                        f += f4 * 0.2f;
                    }
                    f3 = f2 - f;
                }
                final float f5 = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
                this.func_77039_a(entity, x, y, z);
                float f4 = this.func_77044_a(entity, partialTicks);
                this.func_77043_a(entity, f4, f, partialTicks);
                final float f6 = this.func_188322_c(entity, partialTicks);
                float f7 = 0.0f;
                float f8 = 0.0f;
                if (!entity.func_184218_aH()) {
                    f7 = entity.field_184618_aE + (entity.field_70721_aZ - entity.field_184618_aE) * partialTicks;
                    f8 = entity.field_184619_aG - entity.field_70721_aZ * (1.0f - partialTicks);
                    if (entity.func_70631_g_()) {
                        f8 *= 3.0f;
                    }
                    if (f7 > 1.0f) {
                        f7 = 1.0f;
                    }
                    f3 = f2 - f;
                }
                GlStateManager.func_179141_d();
                this.field_77045_g.func_78086_a((EntityLivingBase)entity, f8, f7, partialTicks);
                this.field_77045_g.func_78087_a(f8, f7, f4, f3, f5, f6, (Entity)entity);
                if (this.field_188301_f) {
                    final boolean flag1 = this.func_177088_c(entity);
                    GlStateManager.func_179142_g();
                    GlStateManager.func_187431_e(this.func_188298_c((Entity)entity));
                    if (!this.field_188323_j) {
                        this.func_77036_a(entity, f8, f7, f4, f3, f5, f6);
                    }
                    if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v()) {
                        this.func_177093_a(entity, f8, f7, partialTicks, f4, f3, f5, f6);
                    }
                    GlStateManager.func_187417_n();
                    GlStateManager.func_179119_h();
                    if (flag1) {
                        this.func_180565_e();
                    }
                }
                else {
                    if (Wireframe.getINSTANCE().isOn() && Wireframe.getINSTANCE().players.getValue() && entity instanceof EntityPlayer && Wireframe.getINSTANCE().mode.getValue().equals(Wireframe.RenderMode.SOLID)) {
                        this.red = ClickGui.getInstance().red.getValue() / 255.0f;
                        this.green = ClickGui.getInstance().green.getValue() / 255.0f;
                        this.blue = ClickGui.getInstance().blue.getValue() / 255.0f;
                        GlStateManager.func_179094_E();
                        GL11.glPushAttrib(1048575);
                        GL11.glDisable(3553);
                        GL11.glDisable(2896);
                        GL11.glEnable(2848);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                        if (OyVey.friendManager.isFriend(entity.func_70005_c_()) || entity == Minecraft.func_71410_x().field_71439_g) {
                            GL11.glColor4f(0.0f, 191.0f, 255.0f, Wireframe.getINSTANCE().alpha.getValue() / 255.0f);
                        }
                        else {
                            GL11.glColor4f(((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() / 255.0f) : this.red, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() / 255.0f) : this.green, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() / 255.0f) : this.blue, Wireframe.getINSTANCE().alpha.getValue() / 255.0f);
                        }
                        this.func_77036_a(entity, f8, f7, f4, f3, f5, f6);
                        GL11.glDisable(2896);
                        GL11.glEnable(2929);
                        GL11.glDepthMask(true);
                        if (OyVey.friendManager.isFriend(entity.func_70005_c_()) || entity == Minecraft.func_71410_x().field_71439_g) {
                            GL11.glColor4f(0.0f, 191.0f, 255.0f, Wireframe.getINSTANCE().alpha.getValue() / 255.0f);
                        }
                        else {
                            GL11.glColor4f(((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() / 255.0f) : this.red, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() / 255.0f) : this.green, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() / 255.0f) : this.blue, Wireframe.getINSTANCE().alpha.getValue() / 255.0f);
                        }
                        this.func_77036_a(entity, f8, f7, f4, f3, f5, f6);
                        GL11.glEnable(2896);
                        GlStateManager.func_179099_b();
                        GlStateManager.func_179121_F();
                    }
                    final boolean flag1 = this.func_177090_c(entity, partialTicks);
                    if (!(entity instanceof EntityPlayer) || (Wireframe.getINSTANCE().isOn() && Wireframe.getINSTANCE().mode.getValue().equals(Wireframe.RenderMode.WIREFRAME) && Wireframe.getINSTANCE().playerModel.getValue()) || Wireframe.getINSTANCE().isOff()) {
                        this.func_77036_a(entity, f8, f7, f4, f3, f5, f6);
                    }
                    if (flag1) {
                        this.func_177091_f();
                    }
                    GlStateManager.func_179132_a(true);
                    if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v()) {
                        this.func_177093_a(entity, f8, f7, partialTicks, f4, f3, f5, f6);
                    }
                    if (Wireframe.getINSTANCE().isOn() && Wireframe.getINSTANCE().players.getValue() && entity instanceof EntityPlayer && Wireframe.getINSTANCE().mode.getValue().equals(Wireframe.RenderMode.WIREFRAME)) {
                        this.red = ClickGui.getInstance().red.getValue() / 255.0f;
                        this.green = ClickGui.getInstance().green.getValue() / 255.0f;
                        this.blue = ClickGui.getInstance().blue.getValue() / 255.0f;
                        GlStateManager.func_179094_E();
                        GL11.glPushAttrib(1048575);
                        GL11.glPolygonMode(1032, 6913);
                        GL11.glDisable(3553);
                        GL11.glDisable(2896);
                        GL11.glDisable(2929);
                        GL11.glEnable(2848);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        if (OyVey.friendManager.isFriend(entity.func_70005_c_()) || entity == Minecraft.func_71410_x().field_71439_g) {
                            GL11.glColor4f(0.0f, 191.0f, 255.0f, Wireframe.getINSTANCE().alpha.getValue() / 255.0f);
                        }
                        else {
                            GL11.glColor4f(((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getRed() / 255.0f) : this.red, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getGreen() / 255.0f) : this.green, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? (ColorUtil.rainbow(ClickGui.getInstance().rainbowHue.getValue()).getBlue() / 255.0f) : this.blue, Wireframe.getINSTANCE().alpha.getValue() / 255.0f);
                        }
                        GL11.glLineWidth((float)Wireframe.getINSTANCE().lineWidth.getValue());
                        this.func_77036_a(entity, f8, f7, f4, f3, f5, f6);
                        GL11.glEnable(2896);
                        GlStateManager.func_179099_b();
                        GlStateManager.func_179121_F();
                    }
                }
                GlStateManager.func_179101_C();
            }
            catch (Exception var20) {
                MixinRenderLivingBase.field_147923_a.error("Couldn't render entity", (Throwable)var20);
            }
            GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
            GlStateManager.func_179098_w();
            GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
            GlStateManager.func_179089_o();
            GlStateManager.func_179121_F();
            super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
            MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Post((EntityLivingBase)entity, (RenderLivingBase)RenderLivingBase.class.cast(this), partialTicks, x, y, z));
        }
    }
    
    static {
        field_147923_a = LogManager.getLogger();
    }
}
