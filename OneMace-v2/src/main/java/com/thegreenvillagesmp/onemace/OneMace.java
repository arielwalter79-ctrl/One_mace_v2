package com.thegreenvillagesmp.onemace;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public final class OneMace implements ModInitializer {
    public static final String MOD_ID = "onemace";
    private static boolean maceExists = false;
    private static int ticks = 0;

    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(OneMace::tick);

        // Block putting the Mace into a Decorated Pot.
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (isMace(player.getStackInHand(hand))
                    && world.getBlockState(hit.getBlockPos()).isOf(Blocks.DECORATED_POT)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        // Block putting the Mace into normal and Glow Item Frames.
        UseEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (entity instanceof ItemFrameEntity && isMace(player.getStackInHand(hand))) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        System.out.println("[OneMace] Loaded v2.0.0");
    }

    private static void tick(MinecraftServer server) {
        if (++ticks < 20) return;
        ticks = 0;

        boolean found = false;
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (isMace(stack)) {
                    if (!found) {
                        found = true;
                    } else {
                        stack.setCount(0);
                        player.sendMessage(Text.literal("Only one Mace may exist on this server."), false);
                    }
                }
            }
        }
        maceExists = found;
    }

    public static boolean maceExists() { return maceExists; }
    public static void markMaceExists() { maceExists = true; }
    public static boolean isMace(ItemStack stack) {
        return stack != null && stack.isOf(Items.MACE);
    }
}
