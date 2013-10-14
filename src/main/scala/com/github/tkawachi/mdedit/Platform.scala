package com.github.tkawachi.mdedit

import java.awt.Toolkit

object Platform {
  val isMac: Boolean = System.getProperty("os.name").startsWith("Mac")
  val shortcutKeyMask: Int = Toolkit.getDefaultToolkit.getMenuShortcutKeyMask
}
