package concepts

import processing.core.{PGraphics, PImage, PVector}

case class ImageSample(position: PVector, color: Int)

case class ImageFieldOptions(width: Int, height: Int, distance: Float)

class ImageField(options: VectorFieldOptions, initialize: PVector => PVector,
                 image: PImage) extends VectorField(options, initialize) {

  /**
    * Method for drawing the function values in color blocks
    *
    * @param graphics
    */
  override def drawColors(graphics: PGraphics): Unit = {
    samples.foreach { sample =>
      val pos = sample.position
      graphics.noStroke()
      val x = (sample.position.x + sample.force.x * 10.0f).toInt
      val y = (sample.position.y + sample.force.y * 10.0f).toInt
      val color = image.get(x, y)
      graphics.fill(color)
      graphics.rect(pos.x, pos.y, 20, 20)
    }
  }

  override def drawBoth(graphics: PGraphics): Unit = {
    def mapc(x: Float) = (x * 255.00).toInt
    samples.foreach { sample =>
      val pos = sample.position
      val force = sample.force
      val line = pos.copy().add(force.copy().mult(15.0f));
      graphics.noStroke()
      val x = (((1.0f + sample.force.x) * 500.0f)).toInt
      val y = (((1.0f + sample.force.y) * 500.0f)).toInt
      val color = image.get(x, y)
      graphics.stroke(color)
      graphics.strokeWeight(2.0f)
      graphics.line(pos.x, pos.y, line.x, line.y)
    }
  }


}
