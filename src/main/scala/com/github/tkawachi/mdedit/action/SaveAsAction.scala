package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.MainWindow
import grizzled.slf4j.Logging
import java.awt.Toolkit
import java.awt.event.InputEvent._
import java.awt.event.{KeyEvent, ActionEvent}
import javax.swing.Action._
import javax.swing.{KeyStroke, AbstractAction}

class SaveAsAction(mainWindow: MainWindow) extends AbstractAction("別名で保存") with Logging {
  val defaultAccelKey =
    KeyStroke.getKeyStroke(KeyEvent.VK_S, SHIFT_DOWN_MASK | Toolkit.getDefaultToolkit.getMenuShortcutKeyMask)

  putValue(ACCELERATOR_KEY, defaultAccelKey)


  def actionPerformed(e: ActionEvent) {
    mainWindow.saveAsFile()
  }
}
