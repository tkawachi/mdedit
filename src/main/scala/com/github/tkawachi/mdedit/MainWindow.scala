package com.github.tkawachi.mdedit

import com.github.tkawachi.mdedit.action.{SaveAsAction, OpenAction, SaveAction}
import grizzled.slf4j.Logging
import java.io.{PrintWriter, File}
import javax.swing._
import scala.io.Source

/**
 * メインウィンドウ。
 */
class MainWindow extends JFrame("mdedit") with Logging {
  System.setProperty("com.apple.mrj.application.apple.menu.about.name", "mdedit")
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  var optFile: Option[File] = None

  val previewPane = new HtmlPreview
  val sourcePane = new SourcePane(previewPane)

  val splitPane = new JSplitPane()
  splitPane.add(new JScrollPane(sourcePane), JSplitPane.LEFT)
  splitPane.add(new JScrollPane(previewPane), JSplitPane.RIGHT)
  splitPane.setResizeWeight(0.5)

  getContentPane.add(splitPane)

  val openAction = new OpenAction(this)
  val saveAction = new SaveAction(this)
  val saveAsAction = new SaveAsAction(this)

  setJMenuBar(createMenuBar)

  def createMenuBar: JMenuBar = {
    val menuBar = new JMenuBar

    val fileMenu = menuBar.add(new JMenu("File"))
    for (action <- Seq(openAction, saveAction, saveAsAction)) {
      fileMenu.add(new JMenuItem(action))
    }

    val editMenu = menuBar.add(new JMenu("Edit"))
    for (action <- Seq(sourcePane.undoAction, sourcePane.redoAction)) {
      editMenu.add(new JMenuItem(action))
    }

    menuBar
  }

  /**
   * ファイルを開く
   */
  def openFile() {
    for (file <- FileChooser.chooseOpen(this)) {
      optFile = Option(file)
      sourcePane.setMarkdownSource(Source.fromFile(file)("utf-8").mkString)
    }
  }

  /**
   * 保存
   */
  def saveFile() {
    optFile.orElse {
      optFile = FileChooser.chooseSave(this)
      optFile
    }.foreach(writeToFile)
  }

  /**
   * ファイルに書き込む。
   * @param file 書き込み先。
   */
  private[this] def writeToFile(file: File) {
    val writer = new PrintWriter(file, "utf-8")
    try writer.write(sourcePane.getText) finally writer.close()
  }

  /**
   * 別名で保存
   */
  def saveAsFile() {
    info("saveAsFile()")
    for (file <- FileChooser.chooseSave(this)) {
      optFile = Option(file)
      writeToFile(file)
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
    win.setSize(500, 400)
    win.setVisible(true)
  }
}
