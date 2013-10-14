package com.github.tkawachi.mdedit.action

import java.awt.Toolkit
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import javax.swing.undo.UndoManager
import scala.swing.Action

class UndoAction(undoManager: UndoManager) extends Action("元に戻す") {
  accelerator = Option(
    KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit.getMenuShortcutKeyMask)
  )

  def apply() = if (undoManager.canUndo) undoManager.undo()
}
