package com.codetaylor.mc.dropt.modules.dropt.command;

import com.codetaylor.mc.dropt.modules.dropt.ModuleDropt;
import com.codetaylor.mc.dropt.modules.dropt.Util;
import com.codetaylor.mc.dropt.modules.dropt.rule.RuleLoader;
import com.codetaylor.mc.dropt.modules.dropt.rule.data.RuleList;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.DebugFileWrapper;
import com.codetaylor.mc.dropt.modules.dropt.rule.log.LoggerWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

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

    if (args.length > 0) {

      if ("export".equals(args[0])) {
        long timestamp = System.currentTimeMillis();
        sender.sendMessage(new TextComponentString("Exporting to config/dropt/export/" + timestamp));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path exportPath = ModuleDropt.RULE_PATH.resolve("export/" + timestamp);

        try {
          Files.deleteIfExists(exportPath);
        } catch (IOException e) {
          sender.sendMessage(new TextComponentString("Error exporting, see log for details"));
          ModuleDropt.LOGGER.error("Unable to delete export folder", e);
          return;
        }

        try {
          Files.createDirectories(exportPath);
        } catch (IOException e) {
          sender.sendMessage(new TextComponentString("Error exporting, see log for details"));
          ModuleDropt.LOGGER.error("Unable to create export folder", e);
          return;
        }

        if (!Files.isDirectory(exportPath)) {
          sender.sendMessage(new TextComponentString("Error exporting, see log for details"));
          ModuleDropt.LOGGER.error("Export path not a folder: " + exportPath.toString());
          return;
        }

        int[] illegalChars = {34, 60, 62, 124, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 58, 42, 63, 92, 47};
        Arrays.sort(illegalChars);

        for (RuleList list : ModuleDropt.RULE_LISTS) {

          StringBuilder cleanName = new StringBuilder();
          int len = list._filename.codePointCount(0, list._filename.length());
          for (int i = 0; i < len; i++) {
            int c = list._filename.codePointAt(i);
            if (Arrays.binarySearch(illegalChars, c) < 0) {
              cleanName.appendCodePoint(c);
            } else {
              cleanName.append("_");
            }
          }

          String filename = cleanName.toString().replaceAll(":", "_");
          Path ruleFile = exportPath.resolve(filename + ".json");

          try {
            FileWriter writer = new FileWriter(ruleFile.toFile());
            gson.toJson(list, writer);
            writer.close();
            sender.sendMessage(new TextComponentString("Exported " + filename));
          } catch (IOException e) {
            sender.sendMessage(new TextComponentString("Error exporting, see log for details"));
            ModuleDropt.LOGGER.error("Error exporting rule list: " + filename, e);
          }
        }

      } else if ("reload".equals(args[0])) {
        sender.sendMessage(new TextComponentString("Starting rule list reload..."));

        FileWriter logFileWriter = ModuleDropt.LOG_FILE_WRITER_PROVIDER.createLogFileWriter();
        CommandLoggerWrapper wrapper = new CommandLoggerWrapper(ModuleDropt.LOGGER, sender, logFileWriter);
        ModuleDropt.RULE_LISTS.clear();
        ModuleDropt.RULE_CACHE.clear();
        RuleLoader.loadRuleLists(ModuleDropt.RULE_PATH, ModuleDropt.RULE_LISTS, wrapper,
            new DebugFileWrapper(logFileWriter)
        );
        RuleLoader.parseRuleLists(ModuleDropt.RULE_LISTS, wrapper, new DebugFileWrapper(logFileWriter));
        Util.closeSilently(logFileWriter);
        sender.sendMessage(new TextComponentString("[" + ModuleDropt.RULE_LISTS.size() + "] files processed"));

      } else if ("hand".equals(args[0])) {

        if (sender instanceof EntityPlayer) {
          ItemStack itemStack = ((EntityPlayer) sender).getHeldItemMainhand();
          ResourceLocation registryName = itemStack.getItem().getRegistryName();

          if (registryName != null) {
            String string = registryName.toString() + ":" + itemStack.getMetadata();
            NBTTagCompound tagCompound = itemStack.getTagCompound();

            if (tagCompound != null) {
              string += "#" + tagCompound.toString();
            }

            string = string.replaceAll("\"", "\\\\\"");

            sender.sendMessage(new TextComponentString(string));

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(string), null);
          }
        }

      } else if ("verbose".equals(args[0])) {

        if (sender instanceof EntityPlayer) {
          EntityPlayer player = (EntityPlayer) sender;

          if (ModuleDropt.CONSOLE_LOG.hasListeningPlayer(player)) {
            ModuleDropt.CONSOLE_LOG.removeListeningPlayer(player);
            sender.sendMessage(new TextComponentString("verbose off"));

          } else {
            ModuleDropt.CONSOLE_LOG.addListeningPlayer(player);
            sender.sendMessage(new TextComponentString("verbose on - logging block info to console"));
          }
        }

      }
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

    public CommandLoggerWrapper(Logger logger, ICommandSender sender, FileWriter fileWriter) {

      super(logger, fileWriter);
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
