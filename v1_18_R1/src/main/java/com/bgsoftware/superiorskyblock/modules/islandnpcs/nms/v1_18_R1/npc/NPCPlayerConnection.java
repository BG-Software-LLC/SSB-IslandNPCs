package com.bgsoftware.superiorskyblock.modules.islandnpcs.nms.v1_18_R1.npc;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;

public final class NPCPlayerConnection extends PlayerConnection {

    public NPCPlayerConnection(MinecraftServer server, NetworkManager connection, EntityPlayer player) {
        super(server, connection, player);
    }

    @Override
    public void a(Packet<?> packet) {
        // Do nothing.
    }

    @Override
    public void a(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> listener) {
        // Do nothing.
    }
}
