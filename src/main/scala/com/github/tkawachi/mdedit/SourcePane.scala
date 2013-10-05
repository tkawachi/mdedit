package com.github.tkawachi.mdedit

import java.awt.event.{ActionEvent, KeyEvent, KeyListener}
import javax.swing.undo.UndoManager
import javax.swing.{KeyStroke, AbstractAction, JEditorPane}

/**
 * Markdown のソースコードを書くペイン。
 */
class SourcePane(preview: HtmlPreview) extends JEditorPane {

  import java.awt.event.InputEvent._
  import javax.swing.Action._

  /**
   * undo 管理オブジェクト。
   */
  private[this] val undoManager = new UndoManager
  getDocument.addUndoableEditListener(undoManager)

  private[this] val undoAction = new AbstractAction("元に戻す(U)") {
    val defaultAccelKey =
      if (Platform.isMac) KeyStroke.getKeyStroke(KeyEvent.VK_Z, META_DOWN_MASK)
      else KeyStroke.getKeyStroke(KeyEvent.VK_Z, CTRL_DOWN_MASK)

    putValue(MNEMONIC_KEY, 'U')
    putValue(ACCELERATOR_KEY, defaultAccelKey)

    override def isEnabled = undoManager.canUndo

    def actionPerformed(e: ActionEvent) {
      if (isEnabled) undoManager.undo()
    }
  }

  private[this] val redoAction = new AbstractAction("やり直す(R)") {
    val defaultAccelKey =
      if (Platform.isMac) KeyStroke.getKeyStroke(KeyEvent.VK_Z, META_DOWN_MASK | SHIFT_DOWN_MASK)
      else KeyStroke.getKeyStroke(KeyEvent.VK_Y, CTRL_DOWN_MASK)

    putValue(MNEMONIC_KEY, 'R')
    putValue(ACCELERATOR_KEY, defaultAccelKey)

    override def isEnabled = undoManager.canRedo

    def actionPerformed(e: ActionEvent) {
      if (isEnabled) undoManager.redo()
    }
  }

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
