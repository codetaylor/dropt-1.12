package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.athenaeum.tools.ZenDocExporter;

import java.nio.file.Paths;

public class ExportDocumentation {

  public static void main(String[] args) {

    ZenDocExporter export = new ZenDocExporter();
    export.export(Paths.get("docs/zs/"), new Class[]{
        ZenDropt.class,
        ZenRuleList.class,
        ZenRule.class,
        ZenHarvester.class,
        ZenDrop.class
    });
  }
}
