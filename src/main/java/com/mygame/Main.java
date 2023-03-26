package com.mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import jme3tools.savegame.SaveGame;

public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        // FIRST: run this piece of code to create the savegame.
        // press T a couple of times and notice how the cube does not change colors
        
        // THEN quit, and comment everything up to the SaveGame call and uncomment
        // the two lines below to load the generated savegame. Now press T and
        // notice the color of the cube change. ARGB changed to RGBA?
        
        Material mat = new Material(assetManager, Materials.LIGHTING);
        mat.setBoolean("UseMaterialColors", false);

        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        geom.setMaterial(mat);
        geom.addControl(new ScreenControl());

        rootNode.attachChild(geom);
        
        SaveGame.saveGame("imagetest", "test", geom);

//        Spatial geom = (Spatial) SaveGame.loadGame("imagetest", "test", assetManager);
//        rootNode.attachChild(geom);

        rootNode.addLight(new PointLight(new Vector3f(-2, 2, 2), 25f));
        rootNode.addLight(new AmbientLight(ColorRGBA.DarkGray));

        inputManager.addMapping("test", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener((ActionListener) (action, isPressed, tpf) -> {
            if (!isPressed) {
                geom.getControl(ScreenControl.class).update();
                System.out.println("updated");
            }
        }, "test");
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
