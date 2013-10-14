package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.{Platform, MainWindow}
import grizzled.slf4j.Logging
import java.awt.event.InputEvent._
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import scala.swing.Action


class SaveAsAction(mainWindow: MainWindow) extends Action("別名で保存") with Logging {
  accelerator = Option(
    KeyStroke.getKeyStroke(KeyEvent.VK_S, SHIFT_DOWN_MASK | Platform.shortcutKeyMask)
  )

  def apply() = mainWindow.saveAsFile()
}
