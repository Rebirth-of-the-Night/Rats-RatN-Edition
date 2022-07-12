package com.github.alexthe666.rats.server.items;

import com.github.alexthe666.rats.RatsMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class ItemPlagueHealer extends ItemGenericFood {

    private final float healChance;

    public ItemPlagueHealer(int amount, float saturation, String name, float healChance) {
        super(amount, saturation, false, name);
        this.healChance = healChance;
    }

    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (player.isPotionActive(RatsMod.PLAGUE_POTION)) {
            if (itemRand.nextDouble() <= healChance) {
                player.removePotionEffect(RatsMod.PLAGUE_POTION);
            }
        }
    }

    @Nonnull
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        if(stack.getItem() == RatsItemRegistry.PLAGUE_STEW) {
            super.onItemUseFinish(stack, worldIn, entityLiving);
            return new ItemStack(Items.BOWL);
        }else {
            return super.onItemUseFinish(stack, worldIn, entityLiving);
        }
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.rats.plague_heal_chance.desc", (int) (healChance * 100F)));

    }
}
