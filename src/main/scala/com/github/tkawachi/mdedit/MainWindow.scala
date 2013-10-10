package com.github.tkawachi.mdedit

import com.github.tkawachi.mdedit.action.{SaveAsAction, OpenAction, SaveAction}
import grizzled.slf4j.Logging
import java.awt.FileDialog
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
    Seq(openAction, saveAction, saveAsAction).foreach { (action) =>
      fileMenu.add(new JMenuItem(action))
    }
    val editMenu = menuBar.add(new JMenu("Edit"))
    editMenu.add(new JMenuItem(sourcePane.undoAction))
    editMenu.add(new JMenuItem(sourcePane.redoAction))
    menuBar
  }

  /**
   * ファイルを開く
   */
  def openFile() {
    chooseFile(false).foreach { (file) =>
      optFile = Option(file)
      sourcePane.setMarkdownSource(Source.fromFile(file)("utf-8").mkString)
    }
  }

  /**
   * 保存
   */
  def saveFile() {
    optFile.orElse {
      optFile = chooseFile(true)
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
    for (file <- chooseFile(true)) {
      optFile = Option(file)
      writeToFile(file)
    }
  }

  /**
   * ファイルを選択する。
   * @return
   */
  def chooseFile(forSave: Boolean): Option[File] = {
    val dialog =
      if (forSave) new FileDialog(this, "保存するファイルを選択", FileDialog.SAVE)
      else new FileDialog(this, "開くファイルを選択", FileDialog.LOAD)
    dialog.setVisible(true)
    val files = dialog.getFiles
    if (files.size > 0) Option(files(0))
    else None
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
