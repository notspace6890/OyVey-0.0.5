// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.manager;

import java.util.ArrayList;
import net.minecraft.network.Packet;
import java.util.List;
import me.alpha432.oyvey.features.Feature;

public class PacketManager extends Feature
{
    private final List<Packet<?>> noEventPackets;
    
    public PacketManager() {
        this.noEventPackets = new ArrayList<Packet<?>>();
    }
    
    public void sendPacketNoEvent(final Packet<?> packet) {
        if (packet != null && !Feature.nullCheck()) {
            this.noEventPackets.add(packet);
            PacketManager.mc.field_71439_g.field_71174_a.func_147297_a((Packet)packet);
        }
    }
    
    public boolean shouldSendPacket(final Packet<?> packet) {
        if (this.noEventPackets.contains(packet)) {
            this.noEventPackets.remove(packet);
            return false;
        }
        return true;
    }
}
