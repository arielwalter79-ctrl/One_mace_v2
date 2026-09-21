package com.thegreenvillagesmp.onemace.mixin;

import com.thegreenvillagesmp.onemace.OneMace;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleItem.class)
public abstract class BundleItemMixin {
    @Inject(method = "canBeBundled", at = @At("HEAD"), cancellable = true, require = 0)
    private static void blockMaceInBundle(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (OneMace.isMace(stack)) cir.setReturnValue(false);
    }
}
