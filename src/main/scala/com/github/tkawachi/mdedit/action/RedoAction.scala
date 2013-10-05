package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.Platform
import java.awt.event.InputEvent._
import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.Action._
import javax.swing.undo.UndoManager
import javax.swing.{AbstractAction, KeyStroke}

class RedoAction(undoManager: UndoManager) extends AbstractAction("やり直す") {
  val defaultAccelKey =
    if (Platform.isMac) KeyStroke.getKeyStroke(KeyEvent.VK_Z, META_DOWN_MASK | SHIFT_DOWN_MASK)
    else KeyStroke.getKeyStroke(KeyEvent.VK_Y, CTRL_DOWN_MASK)

  putValue(MNEMONIC_KEY, 'R'.toInt)
  putValue(ACCELERATOR_KEY, defaultAccelKey)

  def actionPerformed(e: ActionEvent) {
    if (undoManager.canRedo) undoManager.redo()
  }

}
