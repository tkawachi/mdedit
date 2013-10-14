package com.github.tkawachi.mdedit

import com.apple.eawt.AppEvent.QuitEvent
import com.apple.eawt.{QuitResponse, QuitHandler, Application}
import com.github.tkawachi.mdedit.action.{SaveAsAction, OpenAction, SaveAction}
import grizzled.slf4j.Logging
import java.io.{PrintWriter, File}
import javax.swing._
import scala.io.Source
import scala.swing.Dialog.{Result, Options}
import scala.swing._

/**
 * メインウィンドウ。
 */
class MainWindow extends Frame with Logging {
  title = "mdedit"
  System.setProperty("com.apple.mrj.application.apple.menu.about.name", "mdedit")

  peer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  var optFile: Option[File] = None

  val previewPane = new HtmlPreview
  val sourcePane = new SourcePane(previewPane)

  val splitPane = new SplitPane(Orientation.Vertical)
  splitPane.leftComponent = new ScrollPane(sourcePane)
  splitPane.rightComponent = new ScrollPane(previewPane)
  splitPane.resizeWeight = 0.5

  contents = splitPane

  val openAction = new OpenAction(this)
  val saveAction = new SaveAction(this)
  val saveAsAction = new SaveAsAction(this)

  menuBar =
    new MenuBar {
      contents += new Menu("File") {
        for (action <- Seq(openAction, saveAction, saveAsAction)) {
          contents += new MenuItem(action)
        }
      }

      contents += new Menu("Edit") {
        for (action <- Seq(sourcePane.undoAction, sourcePane.redoAction)) {
          contents += new MenuItem(action)
        }
      }
    }

  if (Platform.isMac) {
    Application.getApplication.setQuitHandler(new QuitHandler {
      def handleQuitRequestWith(ev: QuitEvent, resp: QuitResponse) {
        if (sourcePane.isDirty) {
          if (saveDirtyBuffer()) resp.cancelQuit()
          else resp.performQuit()
        } else resp.performQuit()
      }
    })
  }

  /**
   * ファイルを開く
   */
  def openFile() {
    if (sourcePane.isDirty) {
      if (saveDirtyBuffer()) return
    }

    for (file <- FileChooser.chooseOpen(this.peer)) {
      optFile = Option(file)
      sourcePane.setTextFromFile(Source.fromFile(file)("utf-8").mkString)
    }
  }

  /**
   * 未保存のファイルを保存する。
   * @return cancel したか？
   */
  def saveDirtyBuffer(): Boolean = {
    Dialog.showConfirmation(message = "ファイルが変更されています。保存しますか？", optionType = Options.YesNoCancel) match {
      case Result.Yes => saveFile().isEmpty
      case Result.No => false
      case _ => true
    }
  }

  /**
   * 保存
   */
  def saveFile(): Option[File] = {
    optFile match {
      case Some(file) => writeToFile(file)
      case None => saveAsFile()
    }
    optFile
  }

  /**
   * ファイルに書き込む。
   * @param file 書き込み先。
   */
  private[this] def writeToFile(file: File) {
    val writer = new PrintWriter(file, "utf-8")
    try writer.write(sourcePane.peer.getText) finally writer.close()
  }

  /**
   * 別名で保存
   */
  def saveAsFile(): Option[File] = {
    info("saveAsFile()")
    for (file <- FileChooser.chooseSave(this.peer)) yield {
      optFile = Option(file)
      writeToFile(file)
      file
    }
  }
}

object MainWindow {
  def main(args: Array[String]) {
    // メニューの一番左を設定できるはずだが、効かないようだ。
    // sys.props("com.apple.mrj.application.apple.menu.about.name") = "mdedit"
    sys.props("apple.laf.useScreenMenuBar") = "true"
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

    val win = new MainWindow()
    win.size = new Dimension(500, 400)
    win.visible = true
  }
}
