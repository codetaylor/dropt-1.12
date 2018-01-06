package com.codetaylor.mc.dropt.modules.dropt.command;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLoader;
import com.codetaylor.mc.dropt.modules.dropt.rule.parser.LoggerWrapper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.Logger;

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
      sender.sendMessage(new TextComponentString("Starting rule list reload..."));
      CommandLoggerWrapper wrapper = new CommandLoggerWrapper(ModuleDropt.LOGGER, sender);
      ModuleDropt.RULE_LISTS.clear();
      RuleLoader.loadRuleLists(ModuleDropt.MOD_ID, ModuleDropt.RULE_PATH, ModuleDropt.RULE_LISTS, wrapper);
      RuleLoader.parseRuleLists(ModuleDropt.RULE_LISTS, wrapper);
      sender.sendMessage(new TextComponentString("[" + ModuleDropt.RULE_LISTS.size() + "] files processed"));
    }
  }

  @Override
  public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

    if (sender instanceof EntityPlayer) {
      return server.getPlayerList().canSendCommands(((EntityPlayer) sender).getGameProfile());
    }

    return false;
  }

  private static class CommandLoggerWrapper
      extends LoggerWrapper {

    private final ICommandSender sender;

    public CommandLoggerWrapper(Logger logger, ICommandSender sender) {

      super(logger);
      this.sender = sender;
    }

    @Override
    public void warn(String message) {

      super.warn(message);
      sender.sendMessage(new TextComponentString(message));
    }

    @Override
    public void error(String message) {

      super.error(message);
      sender.sendMessage(new TextComponentString(message));
    }

    @Override
    public void error(String message, Throwable error) {

      super.error(message, error);
      sender.sendMessage(new TextComponentString(message));
      sender.sendMessage(new TextComponentString(error.getMessage()));
    }
  }
}
