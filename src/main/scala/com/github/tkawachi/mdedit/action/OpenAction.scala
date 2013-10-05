package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.MainWindow
import grizzled.slf4j.Logging
import java.awt.Toolkit
import java.awt.event.{KeyEvent, ActionEvent}
import javax.swing.Action._
import javax.swing.{KeyStroke, AbstractAction}

class OpenAction(mainWindow: MainWindow) extends AbstractAction("開く") with Logging {
  val defaultAccelKey =
    KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit.getMenuShortcutKeyMask)

  putValue(MNEMONIC_KEY, 'O'.toInt)
  putValue(ACCELERATOR_KEY, defaultAccelKey)

  def actionPerformed(e: ActionEvent) {
    mainWindow.openFile()
  }
}
