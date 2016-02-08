package scenes.particles

import concepts._
import processing.core.{PGraphics, PImage}

class ParticleVectorField {

  val options = VectorFieldOptions(50, 50, 15.0f)
  val field = new VectorField(options, FieldInitializers.random)
  val pOptions = ParticleOptions(500, 800.0f, 600.0f)
  val particles = new ParticleSystem(pOptions)

  def mouseClicked() = {
    field(Modifiers.randomModifier(1.5f))
  }

  def draw(graphics: PGraphics, lineGraphics: PGraphics, time: Float): Unit = {
    field(TimeModifiers.waveModifier(time * 0.1f, 0.1f, 0.01f))

    /*
    graphics.beginDraw()
    graphics.background(0,0,0,100)
    field.drawBoth(graphics)
    graphics.endDraw()
    */

    lineGraphics.beginDraw()
    lineGraphics.background(0,0,0,2)
    particles.draw(lineGraphics, time)
    lineGraphics.endDraw()

    particles.particles.foreach { particle =>
      val force = field.getAverageForce(particle.position, 100.0f)
      particle.velocity.add(force)
      particle.velocity.mult(0.5f)
      particle.position.add(particle.velocity)
      if(particle.position.x < 0)
        particle.position.x = 800
      if(particle.position.y < 0)
        particle.position.y = 600
    }
  }
}
