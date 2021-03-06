package com.cmakeplugin;

import com.cmakeplugin.agent.CMakeInstrumentationUtils;
import com.cmakeplugin.utils.CMakeProxyToJB;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.extensions.PluginId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CMakePlusComponent implements ApplicationComponent {
  private static final Logger LOGGER = LoggerFactory.getLogger("CMakePlus");

  @Override
  public void initComponent() {
    CMakeComponent.isCMakePlusActive =
        ApplicationManager.getApplication().isEAP() || CheckLicense.isLicensed();
    if (!CMakeComponent.isCMakePlusActive) {
      final String message =
          "CMake Plus plugin License not found. Plugin functionality will be disabled.";
      LOGGER.warn(message);
      new Notification("CMake Plus", "CMake Plus", message, NotificationType.ERROR).notify(null);
      return;
    }
    CMakeComponent.isCMakePlusActive = checkVersionOfCmakeSimpleHighlighter();
    if (!CMakeComponent.isCMakePlusActive) {
      final String message =
          "Update CMake Simple Highlighter plugin to "
              + FROM_BRANCH
              + "."
              + FROM_BUILD
              + " (or above) please, to enable CMake Plus functionality.";
      LOGGER.warn(message);
      new Notification("CMake Plus", "CMake Plus", message, NotificationType.ERROR).notify(null);
      return;
    }
    if (CMakeProxyToJB.isCLION) {
      CMakeInstrumentationUtils.patchJBclasses();
    }
  }

  private static final int FROM_BRANCH = 201;
  private static final int FROM_BUILD = 1;

  private boolean checkVersionOfCmakeSimpleHighlighter() {
    final IdeaPluginDescriptor pluginDescriptor =
        PluginManager.getPlugin(PluginId.getId("artsiomch.cmake"));
    String versionStr = (pluginDescriptor != null) ? pluginDescriptor.getVersion() : "";
    String[] versionArr = versionStr.split("\\.");
    int branch = 0;
    int build = 0;
    if (versionArr.length > 1) {
        branch = Integer.parseInt(versionArr[0]);
        build =  Integer.parseInt(versionArr[1]);
    }
    return branch > FROM_BRANCH || (branch == FROM_BRANCH && build >= FROM_BUILD);
  }
}
