package com.github.tkawachi.mdedit.action

import com.github.tkawachi.mdedit.Platform
import java.awt.event.KeyEvent
import javax.swing.KeyStroke
import javax.swing.undo.UndoManager
import scala.swing.Action

class UndoAction(undoManager: UndoManager) extends Action("元に戻す") {
  accelerator = Option(
    KeyStroke.getKeyStroke(KeyEvent.VK_Z, Platform.shortcutKeyMask)
  )

  def apply() = if (undoManager.canUndo) undoManager.undo()
}
