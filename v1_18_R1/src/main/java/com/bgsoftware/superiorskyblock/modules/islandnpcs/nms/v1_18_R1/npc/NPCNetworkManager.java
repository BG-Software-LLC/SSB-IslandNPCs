package com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.v1_18_R1.npc;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;

import java.net.SocketAddress;

public final class NPCNetworkManager extends NetworkManager {

    public NPCNetworkManager(EnumProtocolDirection side) {
        super(side);
        this.k = new EmptyChannel(null);
        this.l = new SocketAddress() {
        };
    }

    @Override
    public void a(Packet<?> packet) {
        // Do nothing.
    }

}
