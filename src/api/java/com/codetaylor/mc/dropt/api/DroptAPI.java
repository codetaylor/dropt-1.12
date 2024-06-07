package com.codetaylor.mc.dropt.api;

import com.codetaylor.mc.dropt.api.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "unused"})
public final class DroptAPI {

  /**
   * The following fields are injected during Dropt's FMLConstructionEvent.
   */

  private static final Supplier<String> SUPPLIER_MOD_ID = null;
  private static final Supplier<IDroptRuleBuilder> SUPPLIER_RULE_BUILDER = null;
  private static final Supplier<IDroptHarvesterRuleBuilder> SUPPLIER_HARVESTER_RULE_BUILDER = null;
  private static final Supplier<IDroptDropBuilder> SUPPLIER_DROP_BUILDER = null;
  private static final IRuleRegistrationHandler CONSUMER_RULE = null;

  /**
   * @return the mod id of Dropt
   */
  public static String modId() {

    return SUPPLIER_MOD_ID.get();
  }

  /**
   * Call this during {@link com.codetaylor.mc.dropt.api.event.DroptLoadRulesEvent}
   * to register rule lists.
   *
   * @param id       the list id
   * @param priority the list priority
   * @param builders the list of rule builders to register
   */
  public static void registerRuleList(ResourceLocation id, int priority, List<IDroptRuleBuilder> builders) {

    CONSUMER_RULE.register(id, priority, builders);
  }

  /**
   * @return a new rule builder
   */
  public static IDroptRuleBuilder rule() {

    return SUPPLIER_RULE_BUILDER.get();
  }

  /**
   * @return a new harvester builder
   */
  public static IDroptHarvesterRuleBuilder harvester() {

    return SUPPLIER_HARVESTER_RULE_BUILDER.get();
  }

  /**
   * @return a new drop builder
   */
  public static IDroptDropBuilder drop() {

    return SUPPLIER_DROP_BUILDER.get();
  }

  /**
   * @param fixed the fixed range
   * @return a new fixed range
   */
  public static RandomFortuneInt range(int fixed) {

    RandomFortuneInt result = new RandomFortuneInt();
    result.fixed = fixed;
    return result;
  }

  /**
   * @param min the range min
   * @param max the range max
   * @return a new range
   */
  public static RandomFortuneInt range(int min, int max) {

    RandomFortuneInt result = new RandomFortuneInt();
    result.min = min;
    result.max = max;
    return result;
  }

  /**
   * @param min             the range min
   * @param max             the range max
   * @param fortuneModifier the fortune modifier
   * @return a new fortune modified range
   */
  public static RandomFortuneInt range(int min, int max, int fortuneModifier) {

    RandomFortuneInt result = new RandomFortuneInt();
    result.min = min;
    result.max = max;
    result.fortuneModifier = fortuneModifier;
    return result;
  }

  /**
   * @param weight the weight
   * @return a new weight
   */
  public static RuleDropSelectorWeight weight(int weight) {

    RuleDropSelectorWeight result = new RuleDropSelectorWeight();
    result.value = weight;
    return result;
  }

  /**
   * @param weight          the weight
   * @param fortuneModifier the fortune modifier
   * @return a new fortune modified weight
   */
  public static RuleDropSelectorWeight weight(int weight, int fortuneModifier) {

    RuleDropSelectorWeight result = new RuleDropSelectorWeight();
    result.value = weight;
    result.fortuneModifier = fortuneModifier;
    return result;
  }

  /**
   * @param domain the resource location domain
   * @param path   the resource location path
   * @return a ready-to-parse string
   * @see #itemString(String, String, int, int, NBTTagCompound)
   */
  public static String itemString(String domain, String path) {

    return DroptAPI.itemString(domain, path, 0, 1,null);
  }

  /**
   * @param domain the resource location domain
   * @param path   the resource location path
   * @param meta   the item's meta
   * @return a ready-to-parse string
   * @see #itemString(String, String, int, int, NBTTagCompound)
   */
  public static String itemString(String domain, String path, int meta) {

    return DroptAPI.itemString(domain, path, meta, 1,null);
  }

  /**
   * @param domain   the resource location domain
   * @param path     the resource location path
   * @param meta     the item's meta
   * @param quantity the item stack quantity
   * @return a ready-to-parse string
   * @see #itemString(String, String, int, int, NBTTagCompound)
   */
  public static String itemString(String domain, String path, int meta, int quantity) {

    return DroptAPI.itemString(domain, path, meta, quantity, null);
  }

  /**
   * Returns a ready-to-parse string in the format:
   * <p>
   * <code>domain:path:(*|meta)#nbt</code>
   *
   * @param domain   the resource location domain
   * @param path     the resource location path
   * @param meta     the item's meta
   * @param quantity the item stack quantity
   * @param tag      the item's tag
   * @return a ready-to-parse string
   */
  public static String itemString(String domain, String path, int meta, int quantity, @Nullable NBTTagCompound tag) {

    return domain + ":" + path + ":" + ((meta == OreDictionary.WILDCARD_VALUE) ? "*" : meta) + ((tag != null) ? "#" + tag : "") + " * " + quantity;
  }

  /**
   * Returns a ready-to-parse string from the given {@link ItemStack}.
   *
   * @param itemStack the item stack
   * @return a ready-to-parse string
   * @see #itemString(String, String, int, int, NBTTagCompound)
   */
  public static String itemString(ItemStack itemStack) {

    ResourceLocation registryName = itemStack.getItem().getRegistryName();
    return DroptAPI.itemString(registryName.getResourceDomain(), registryName.getResourcePath(), itemStack.getMetadata(), itemStack.getCount(), itemStack.getTagCompound());
  }

}
