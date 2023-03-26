package com.mygame;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.material.MatParamOverride;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.shader.VarType;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ColorSpace;

public class ScreenControl extends AbstractControl {
    private ByteBuffer buffer;
    private Image image;
    private Texture texture;

    public ScreenControl() {
        // create the buffer to draw on
        buffer = ByteBuffer.allocateDirect(128 * 128 * 4);
        for (int i = 0; i < 128 * 128; i++) {
            buffer.putInt(-6724045);    // brownish in ARGB8
        }

        // create an image based on the buffer
        image = new Image(Image.Format.ARGB8, 128, 128, buffer, ColorSpace.sRGB);

        // create the texture based on the buffer
        texture = new Texture2D(image);
    }

    public void update() {
        // put the same brownish color on the cube
        for (int i = 0; i < 128 * 128; i++) {
            buffer.putInt(-6724045);    // brownish in ARGB8
        }
        image.setUpdateNeeded();
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);

        this.spatial.addMatParamOverride(new MatParamOverride(VarType.Texture2D, "DiffuseMap", texture));
    }

    @Override()
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);

        ex.getCapsule(this).write(buffer, "buffer", null);
    }

    @Override()
    public void read(JmeImporter im) throws IOException {
        super.read(im);

        buffer = im.getCapsule(this).readByteBuffer("buffer", null);

        // recreate an image based on the buffer
        image = new Image(Image.Format.ARGB8, 128, 128, buffer, ColorSpace.sRGB);

        // recreate the texture based on the buffer
        texture = new Texture2D(image);

        // recreate the material override
        this.spatial.clearMatParamOverrides();
        this.spatial.addMatParamOverride(new MatParamOverride(VarType.Texture2D, "DiffuseMap", texture));
    }

    @Override()
    protected void controlUpdate(float f) {
    }

    @Override()
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}
