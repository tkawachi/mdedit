package com.github.tkawachi.mdedit.action

import java.awt.Toolkit
import java.awt.event.{ActionEvent, KeyEvent}
import javax.swing.Action._
import javax.swing.undo.UndoManager
import javax.swing.{KeyStroke, AbstractAction}

class UndoAction(undoManager: UndoManager) extends AbstractAction("元に戻す") {
  val defaultAccelKey =
    KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit.getMenuShortcutKeyMask)

  putValue(ACCELERATOR_KEY, defaultAccelKey)

  def actionPerformed(e: ActionEvent) {
    if (undoManager.canUndo) undoManager.undo()
  }
}
