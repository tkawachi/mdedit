package com.github.tkawachi.mdedit

import java.awt.event.{KeyEvent, KeyListener}
import javax.swing.JEditorPane
import javax.swing.undo.UndoManager

/**
 * Markdown のソースコードを書くペイン。
 */
class SourcePane(preview: HtmlPreview) extends JEditorPane {

  /**
   * undo 管理オブジェクト。
   */
  private[this] val undoManager = new UndoManager
  getDocument.addUndoableEditListener(undoManager)

  addKeyListener(new KeyListener {

    import KeyEvent._

    def keyTyped(e: KeyEvent) {}

    def keyPressed(e: KeyEvent) {
      // Ctrl-z or Cmd-z で undo
      // Shirt-Ctrl-z or Shift-Cmd-z で redo
      (e.getKeyCode, e.isShiftDown, e.isControlDown || e.isMetaDown) match {
        case (VK_Z, false, true) => if (undoManager.canUndo) undoManager.undo()
        case (VK_Z, true, true) => if (undoManager.canRedo) undoManager.redo()
        case _ =>
      }
    }

    def keyReleased(e: KeyEvent) {
      preview.setMarkdownSource(getText)
    }
  })

}
