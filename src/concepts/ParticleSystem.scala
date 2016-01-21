package concepts

import processing.core.{PGraphics, PVector}

case class Particle(position: PVector, velocity: PVector)

case class ParticleOptions(amount: Int, width: Float, height: Float)

class ParticleSystem(options: ParticleOptions) {
  val particles = Range(0, options.amount).map { x =>
    val x = (Math.random() * options.width).toInt
    val y = (Math.random() * options.height).toInt
    val vec = new PVector(x, y)
    val velocity = PVector.random2D
    Particle(vec, velocity)
  }

  def draw(graphics: PGraphics, time: Float) = {
    def mapc(x: Float) = (x * 255.00).toInt
    particles.foreach { particle =>
      val pos = particle.position
      val vel = particle.velocity
      graphics.noStroke()
      graphics.fill(mapc(vel.x), mapc(vel.y),255)
      graphics.rect(pos.x, pos.y, 2, 2)
    }
  }
}
