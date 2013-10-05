package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.MainWindow
import grizzled.slf4j.Logging
import java.awt.Toolkit
import java.awt.event.{KeyEvent, ActionEvent}
import javax.swing.Action._
import javax.swing.{KeyStroke, AbstractAction}

class SaveAction(mainWindow: MainWindow) extends AbstractAction("保存") with Logging {
  val defaultAccelKey =
    KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit.getMenuShortcutKeyMask)

  putValue(MNEMONIC_KEY, 'S'.toInt)
  putValue(ACCELERATOR_KEY, defaultAccelKey)


  def actionPerformed(e: ActionEvent) {
    mainWindow.saveFile()
  }
}
