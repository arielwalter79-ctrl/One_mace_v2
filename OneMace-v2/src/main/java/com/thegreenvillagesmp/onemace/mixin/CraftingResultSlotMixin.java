package com.thegreenvillagesmp.onemace.mixin;

import com.thegreenvillagesmp.onemace.OneMace;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultSlot.class)
public abstract class CraftingResultSlotMixin {
    @Inject(method = "onTakeItem", at = @At("HEAD"), cancellable = true)
    private void onTakeMace(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!OneMace.isMace(stack)) return;
        if (OneMace.maceExists()) {
            player.sendMessage(Text.literal("A Mace already exists on this server."), false);
            ci.cancel();
            return;
        }
        OneMace.markMaceExists();
    }
}
