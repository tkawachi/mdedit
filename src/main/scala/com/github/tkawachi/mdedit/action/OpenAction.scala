package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.{MainWindow, Platform}
import grizzled.slf4j.Logging
import java.awt.event.InputEvent._
import java.awt.event.{KeyEvent, ActionEvent}
import javax.swing.Action._
import javax.swing.{KeyStroke, AbstractAction}

class OpenAction(mainWindow: MainWindow) extends AbstractAction("開く") with Logging {
  val defaultAccelKey =
    if (Platform.isMac) KeyStroke.getKeyStroke(KeyEvent.VK_O, META_DOWN_MASK)
    else KeyStroke.getKeyStroke(KeyEvent.VK_O, CTRL_DOWN_MASK)

  putValue(MNEMONIC_KEY, 'O'.toInt)
  putValue(ACCELERATOR_KEY, defaultAccelKey)

  def actionPerformed(e: ActionEvent) {
    mainWindow.openFile()
  }
}
