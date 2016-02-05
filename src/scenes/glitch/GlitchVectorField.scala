package scenes.glitch

import concepts._
import processing.core.{PGraphics, PVector}

class GlitchVectorField {
  val options = VectorFieldOptions(100, 60, 10.0f)

 val field = new VectorField(options, FieldInitializers.random)
  var modifier = 1.0f;
  var bmodifier = 1.0f;
  var hsize = 20
  var wsize = 20

  def mouseMoved(mouseX: Int, mouseY: Int) = {
    hsize = ((mouseX.toFloat / 800.0f) * 10.0f).toInt
    wsize = ((mouseY.toFloat / 600.0f) * 10.0f).toInt
  }

  def draw(graphics: PGraphics, time: Float): Unit = {
    modifier = Math.sin(time / 1000).toFloat
    bmodifier = Math.sin(time / 1000).toFloat

    field(TimeModifiers.waveModifier(time * 0.5f, 1.0f))
    field(TimeModifiers.waveModifier(time * 0.2f, 1.0f))
    field(TimeModifiers.waveModifier(time * 0.1f, 1.0f))
    field(TimeModifiers.xModifier(time / 10.0f, 0.5f))

    //field.drawBoth(graphics)
    def mapc(x: Float) = (x * 100.00).toInt + 155

    field.samples.foreach {
     sample =>
      val pos = sample.position
      val force = sample.force
      graphics.noStroke()
      graphics.fill(mapc(force.x), mapc(force.y), 25*5, mapc(force.x))
       // without force for normal particle effect, check out different parameter settings,
       // this one is awesome, make a gif and a video.
      graphics.rect(pos.x - force.x * 10.0f * hsize, pos.y - force.y * 10 * wsize, (10*force.x).toInt,3)
    }
  }
}
