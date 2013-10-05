package com.github.tkawachi.mdedit

import com.github.tkawachi.mdedit.action.{RedoAction, UndoAction}
import java.awt.event.{KeyEvent, KeyListener}
import javax.swing.undo.UndoManager
import javax.swing.{KeyStroke, JEditorPane}

/**
 * Markdown のソースコードを書くペイン。
 */
class SourcePane(preview: HtmlPreview) extends JEditorPane {

  import javax.swing.Action._

  /**
   * undo 管理オブジェクト。
   */
  private[this] val undoManager = new UndoManager
  getDocument.addUndoableEditListener(undoManager)

  val undoAction = new UndoAction(undoManager)

  val redoAction = new RedoAction(undoManager)

  Seq(undoAction, redoAction).foreach { (action) =>
    getInputMap.put(action.getValue(ACCELERATOR_KEY).asInstanceOf[KeyStroke], action.getValue(NAME))
    getActionMap.put(action.getValue(NAME), action)
  }

  addKeyListener(new KeyListener {


    def keyTyped(e: KeyEvent) {}

    def keyPressed(e: KeyEvent) {}

    def keyReleased(e: KeyEvent) {
      updatePreview()
    }
  })

  def setMarkdownSource(txt: String) {
    setText(txt)
    updatePreview()
  }

  def updatePreview() {
    preview.setMarkdownSource(getText)
  }

}
