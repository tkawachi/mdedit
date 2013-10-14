package com.github.tkawachi.mdedit.action

import java.awt.Toolkit
import java.awt.event.InputEvent._
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import javax.swing.undo.UndoManager
import scala.swing.Action


class RedoAction(undoManager: UndoManager) extends Action("やり直す") {
  accelerator = Option(
    KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit.getMenuShortcutKeyMask | SHIFT_DOWN_MASK)
  )

  def apply() = if (undoManager.canRedo) undoManager.redo()
}
