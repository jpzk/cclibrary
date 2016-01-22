package scenes.glitch;

import processing.core.PApplet;
import processing.core.PGraphics;

public class GlitchVectorFieldSketch extends PApplet {

    public PGraphics pg;
    public GlitchVectorField field;
    public float time;

    public void settings() {
        size(800, 600, P3D);
        //fullScreen(P3D);
    }

    public void setup() {
        time = 0.0f;
        pg = createGraphics(800, 600);
        background(20);
        frameRate(25);
        fill(200);
        field = new GlitchVectorField();
    }

    public void mouseMoved() {

    }

    public void draw() {
        time += 0.1f;
        pg.beginDraw();
        pg.background(0, 0, 0, 255);
        field.mouseMoved(mouseX, mouseY);
        field.draw(pg, time);
        pg.endDraw();
        image(pg, 0, 0);
    }
}