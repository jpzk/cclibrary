package scenes.glitch

import concepts._
import processing.core.{PGraphics, PVector}

class GlitchVectorField {
  val options = VectorFieldOptions(50, 50, 20.0f)

 val field = new VectorField(options, FieldInitializers.random)

  def mouseMoved(mouseX: Int, mouseY: Int) = {
    def mousePos = new PVector(mouseX, mouseY)
    def attract(sample: VectorSample) = {
      val rv = new PVector(sample.position.x - mouseX, sample.position.y - mouseY)
      rv.normalize().mult(0.1f)
      sample.force.add(rv).normalize()
      sample
    }
    field.applyNeighbors(attract, mousePos, 100.0f)
  }

  def draw(graphics: PGraphics, time: Float): Unit = {
    field(TimeModifiers.waveModifier(time * 0.5f, 1.0f))
    field(TimeModifiers.waveModifier(time * 0.2f, 1.0f))
    field(TimeModifiers.waveModifier(time * 0.1f, 1.0f))
    field(TimeModifiers.xModifier(time * 0.8f, 1.0f))
    //field.drawBoth(graphics)
    def mapc(x: Float) = (x * 255.00).toInt

    field.samples.foreach {
     sample =>
      val pos = sample.position
      val force = sample.force
      val line = pos.copy().add(force.copy().mult(20.0f));
      graphics.noStroke()
      graphics.fill(mapc(force.x), mapc(force.y), 100, 200)
      graphics.rect(pos.x + force.x * 20.0f, pos.y, 10, 20)
      //graphics.stroke(255)
      //graphics.line(pos.x, pos.y, line.x, line.y)

    }
  }
}
