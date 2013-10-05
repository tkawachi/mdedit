package com.github.tkawachi.mdedit.action

import java.awt.Toolkit
import java.awt.event.InputEvent._
import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.Action._
import javax.swing.undo.UndoManager
import javax.swing.{AbstractAction, KeyStroke}

class RedoAction(undoManager: UndoManager) extends AbstractAction("やり直す") {
  val defaultAccelKey =
    KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit.getMenuShortcutKeyMask | SHIFT_DOWN_MASK)

  putValue(ACCELERATOR_KEY, defaultAccelKey)

  def actionPerformed(e: ActionEvent) {
    if (undoManager.canRedo) undoManager.redo()
  }

}
