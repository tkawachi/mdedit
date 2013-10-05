package com.github.tkawachi.mdedit

import com.github.tkawachi.mdedit.action.{OpenAction, SaveAction}
import grizzled.slf4j.Logging
import java.io.{PrintWriter, File}
import javax.swing._
import scala.io.Source

/**
 * メインウィンドウ。
 */
class MainWindow extends JFrame("mdedit") with Logging {
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  var optFile: Option[File] = None

  val previewPane = new HtmlPreview
  val sourcePane = new SourcePane(previewPane)

  val splitPane = new JSplitPane()
  splitPane.add(new JScrollPane(sourcePane), JSplitPane.LEFT)
  splitPane.add(new JScrollPane(previewPane), JSplitPane.RIGHT)
  splitPane.setResizeWeight(0.5)

  getContentPane.add(splitPane)

  val saveAction = new SaveAction(this)
  val openAction = new OpenAction(this)

  setJMenuBar(createMenuBar)

  def createMenuBar: JMenuBar = {
    val menuBar = new JMenuBar
    val fileMenu = menuBar.add(new JMenu("File"))
    fileMenu.add(new JMenuItem(saveAction))
    fileMenu.add(new JMenuItem(openAction))
    val editMenu = menuBar.add(new JMenu("Edit"))
    editMenu.add(new JMenuItem(sourcePane.undoAction))
    editMenu.add(new JMenuItem(sourcePane.redoAction))
    menuBar
  }

  def openFile() {
    import JFileChooser._

    val chooser = new JFileChooser()
    chooser.showOpenDialog(this) match {
      case APPROVE_OPTION =>
        optFile = Option(chooser.getSelectedFile)
        optFile.foreach { (file) =>
          sourcePane.setMarkdownSource(Source.fromFile(file)("utf-8").mkString)
        }
      case _ =>
    }
  }

  def saveFile() {
    optFile.orElse {
      optFile = chooseSaveFile()
      optFile
    }.foreach { (file) =>
      val writer = new PrintWriter(file, "utf-8")
      try writer.write(sourcePane.getText) finally writer.close()
    }
  }

  def chooseSaveFile(): Option[File] = {
    import JFileChooser._

    val fileChooser = new JFileChooser()
    fileChooser.showSaveDialog(this) match {
      case APPROVE_OPTION => Option(fileChooser.getSelectedFile)
      case ERROR_OPTION =>
        error("An error occurred during save")
        None
      case _ => None
    }
  }
}

object MainWindow {
  def main(args: Array[String]) {
    val win = new MainWindow()
    win.setSize(500, 400)
    win.setVisible(true)
  }
}
