package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.{MainWindow, Platform}
import grizzled.slf4j.Logging
import java.awt.event.InputEvent._
import java.awt.event.{KeyEvent, ActionEvent}
import javax.swing.Action._
import javax.swing.{KeyStroke, AbstractAction}

class SaveAction(mainWindow: MainWindow) extends AbstractAction("保存") with Logging {
  val defaultAccelKey =
    if (Platform.isMac) KeyStroke.getKeyStroke(KeyEvent.VK_S, META_DOWN_MASK)
    else KeyStroke.getKeyStroke(KeyEvent.VK_S, CTRL_DOWN_MASK)

  putValue(MNEMONIC_KEY, 'S'.toInt)
  putValue(ACCELERATOR_KEY, defaultAccelKey)


  def actionPerformed(e: ActionEvent) {
    mainWindow.saveFile()
  }
}
