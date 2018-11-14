package com.codetaylor.mc.dropt.api.api;

import net.minecraft.util.ResourceLocation;

import java.util.List;

public interface IRuleRegistrationHandler {

  void register(ResourceLocation id, int priority, List<IDroptRuleBuilder> builders);

}
