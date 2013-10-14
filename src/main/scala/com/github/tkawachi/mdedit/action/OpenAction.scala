package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.{Platform, MainWindow}
import grizzled.slf4j.Logging
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import scala.swing.Action

class OpenAction(mainWindow: MainWindow) extends Action("開く") with Logging {
  accelerator = Option(
    KeyStroke.getKeyStroke(KeyEvent.VK_O, Platform.shortcutKeyMask)
  )

  def apply() = mainWindow.openFile()
}
