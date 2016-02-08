package concepts

import processing.core.{PGraphics, PVector}

case class VectorSample(position: PVector, force: PVector)

case class VectorFieldOptions(width: Int, height: Int, distance: Float)

object Modifiers {
  def randomModifier(strength: Float) = {
    new Function[VectorSample, VectorSample] {
      def apply(s: VectorSample): VectorSample = {
        s.force.add(PVector.random3D().mult(strength))
        s
      }
    }
  }
}

object TimeModifiers {
  def waveModifier(offset: Float, strength: Float, frequence: Float) = {
    new Function[VectorSample, VectorSample] {
      def apply(s: VectorSample): VectorSample = {
        val x = Math.sin(frequence * s.position.x + offset).toFloat
        val y = Math.cos(frequence * s.position.y + offset).toFloat
        val addthis = (new PVector(x, y)).mult(strength)
        s.force.add(addthis).normalize()
        s
      }
    }
  }

  def xModifier(offset: Float, strength: Float, frequence: Float) = {
    new Function[VectorSample, VectorSample] {
      def apply(s: VectorSample): VectorSample = {
        val x = Math.sin(frequence * s.position.x + offset).toFloat
        val y = Math.sin(frequence * s.position.x * s.position.y + offset).toFloat
        val addthis = (new PVector(x, y)).mult(strength)
        s.force.add(addthis).normalize()
        s
      }
    }
  }
}

/**
  * Object holding a couple of field initializers
  */
object FieldInitializers {
  val random = new Function[PVector, PVector] {
    def apply(x: PVector): PVector = PVector.random3D()
  }
}

/**
  * Class representing a vector field
  * @param options
  * @param initialize
  */
class VectorField(options: VectorFieldOptions, initialize: (PVector => PVector)) {
  val distance = options.distance

  val samples = Range(0, options.width).flatMap { x =>
    Range(0, options.height).map { y =>
      val vec = new PVector(x * distance, y * distance)
      val fval = initialize(vec)
      VectorSample(new PVector(x * distance, y * distance), fval)
    }
  }

  /**
    * Apply a modifier function
    *
    * @param modifier
    */
  def apply(modifier: (VectorSample => VectorSample)) = {
    samples.foreach { sample => modifier(sample) }
  }

  /**
    * Apply a modifier function in the neighborhood of a vector
    *
    * @param modifier
    * @param pVector
    * @param distance
    */
  def applyNeighbors(modifier: (VectorSample => VectorSample),
                     pVector: PVector, distance: Float) = {

    val neighbors = samples.filter {
      sample => pVector.dist(sample.position) < distance
    }
    neighbors.foreach { sample => modifier(sample) }
  }

  /**
    * Getting the average vector field
    *
    * @param pVector
    * @param distance
    */
  def getAverageForce(pVector: PVector, distance: Float) = {
    val neighbors = samples.filter {
      sample => pVector.dist(sample.position) < distance
    }
    val forces = neighbors
      .map { sample => sample.force }

    forces match {
      case forces if forces.length < 1 => PVector.random2D()
      case _ => forces
        .reduce { (f1, f2) => f1.copy().add(f2) }
        .normalize()
    }
  }


  /**
    * Method for drawing the vector function values debugging
    * @param graphics
    */
  def drawVectors(graphics: PGraphics): Unit = {
    samples.foreach { sample =>
      val pos = sample.position
      val force = sample.force
      graphics.stroke(255)
      val line = pos.copy().add(force.copy().mult(20.0f));
      graphics.line(pos.x, pos.y, line.x, line.y)
    }
  }

  /**
    * Method for drawing the function values in color blocks
    *
    * @param graphics
    */
  def drawColors(graphics: PGraphics): Unit = {
    def mapc(x: Float) = (x * 255.00).toInt
    samples.foreach { sample =>
      val pos = sample.position
      val force = sample.force
      graphics.noStroke()
      graphics.fill(mapc(force.x), mapc(force.y), mapc(force.z))
      graphics.rect(pos.x, pos.y, 10, 10)
    }
  }

  def drawBoth(graphics: PGraphics): Unit = {
    def mapc(x: Float) = (x * 255.00).toInt
    samples.foreach { sample =>
      val pos = sample.position
      val force = sample.force
      val line = pos.copy().add(force.copy().mult(20.0f));
      graphics.noStroke()
      graphics.fill(mapc(force.x), mapc(force.y), 255, 100)
      graphics.rect(pos.x, pos.y, 20, 20)
      graphics.stroke(255)
      graphics.line(pos.x, pos.y, line.x, line.y)
    }
  }

}
