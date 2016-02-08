package scenes.vectorfield

import concepts._
import processing.core.{PGraphics, PVector}

class GlitchVectorField {
  val options = VectorFieldOptions(100, 100, 20.0f)

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
    field(TimeModifiers.waveModifier(time * 0.5f, 1.0f, 0.1f))
    field(TimeModifiers.waveModifier(time * 0.2f, 1.0f, 0.1f))
    field(TimeModifiers.waveModifier(time * 0.1f, 1.0f, 0.1f))
    field.drawBoth(graphics)
  }
}
