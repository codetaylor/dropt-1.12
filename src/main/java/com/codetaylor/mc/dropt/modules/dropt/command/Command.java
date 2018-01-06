package com.codetaylor.mc.dropt.modules.dropt.command;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class Command
    extends CommandBase {

  @Override
  public String getName() {

    return "dropt";
  }

  @Override
  public String getUsage(ICommandSender sender) {

    return "dropt <reload>";
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

    if (args.length > 0 && "reload".equals(args[0])) {
      RuleLoader.loadRuleLists(ModuleDropt.MOD_ID, ModuleDropt.RULE_PATH, ModuleDropt.RULE_LISTS, ModuleDropt.LOGGER);
      RuleLoader.parseRuleLists(ModuleDropt.RULE_LISTS, ModuleDropt.LOGGER);
    }
  }

  @Override
  public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

    if (sender instanceof EntityPlayer) {
      return server.getPlayerList().canSendCommands(((EntityPlayer) sender).getGameProfile());
    }

    return false;
  }
}
