package scenes.particles;

import processing.core.PApplet;
import processing.core.PGraphics;

public class ParticleVectorFieldSketch extends PApplet {

    public PGraphics pg;
    public PGraphics lineGraphics;
    public ParticleVectorField field;
    public float time;

    public void settings() {
        size(800, 600, P3D);
        //fullScreen(P3D);
    }

    public void setup() {
        time = 0.0f;
        pg = createGraphics(800, 600);
        lineGraphics = createGraphics(800, 600);
        background(20);
        frameRate(35);
        fill(200);
        field = new ParticleVectorField();
    }

    public void draw() {
        time += 0.1f;
        field.draw(pg, lineGraphics, time);
        image(lineGraphics, 0, 0);
    }
}