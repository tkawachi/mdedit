package com.github.tkawachi.mdedit

import com.github.tkawachi.mdedit.action.{RedoAction, UndoAction}
import grizzled.slf4j.Logging
import javax.swing.event.{DocumentEvent, DocumentListener}
import javax.swing.undo.UndoManager
import javax.swing.{KeyStroke, JEditorPane}

/**
 * Markdown のソースコードを書くペイン。
 */
class SourcePane(preview: HtmlPreview) extends JEditorPane with Logging {

  import javax.swing.Action._

  /**
   * undo 管理オブジェクト。
   */
  private[this] val undoManager = new UndoManager
  getDocument.addUndoableEditListener(undoManager)

  val undoAction = new UndoAction(undoManager)

  val redoAction = new RedoAction(undoManager)

  private var _isDirty = false

  Seq(undoAction, redoAction).foreach { (action) =>
    getInputMap.put(action.getValue(ACCELERATOR_KEY).asInstanceOf[KeyStroke], action.getValue(NAME))
    getActionMap.put(action.getValue(NAME), action)
  }

  getDocument.addDocumentListener(new DocumentListener {
    def insertUpdate(e: DocumentEvent) {
      _isDirty = true
      updatePreview()
    }

    def changedUpdate(e: DocumentEvent) {
      _isDirty = true
    }

    def removeUpdate(e: DocumentEvent) {
      _isDirty = true
      updatePreview()
    }
  })

  def isDirty: Boolean = _isDirty

  /**
   * ファイルから読み込んだテキストを設定する。
   * @param txt テキスト
   */
  def setTextFromFile(txt: String) {
    setText(txt)
    _isDirty = false
  }

  def updatePreview() {
    preview.setMarkdownSource(getText)
  }

}
