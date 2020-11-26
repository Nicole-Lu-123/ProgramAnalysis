package com.mycompany;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;

public class BasicSimpleUniverse extends Applet{
    public SimpleUniverse universe;
    public BranchGroup rootBranchGroup;

    public static void main(String[] args){
        new MainFrame(new BasicSimpleUniverse(), 750, 750);
    }

    public BasicSimpleUniverse(){
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        rootBranchGroup = new BranchGroup();
        add("Center", canvas);
        BranchGroup scene = createSceneGraph();

        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);

    }

    public BranchGroup createSceneGraph() {
        TransformGroup tg = new TransformGroup();
        Transform3D trans3d = new Transform3D();
        trans3d.setScale(10); // set size
        tg.setTransform(trans3d);
        rootBranchGroup.addChild(tg);

        // set background
        TransformGroup moveGroup = new TransformGroup();
        Transform3D move = new Transform3D();
        move.setTranslation(new Vector3f(0.15f, 0.0f, -100.0f));

        BoundingSphere bound = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0);
        //Color3f bgColor = /new Color3f(0.0f, 0.0f, 0.0f); // create background color
        TextureLoader backgroundTexture = new TextureLoader("./Project2/my-app/universe.jpg", this);
        Background bg = new Background(backgroundTexture.getImage());
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);
        bg.setApplicationBounds(bound);
//        bg.setBounds(bound);
//        rootBranchGroup.addChild(bg);
        tg.setTransform(move);
        tg.addChild(bg);
        //moveGroup.setTransform(move);
        //moveGroup.addChild(bg);
        rootBranchGroup.addChild(moveGroup);
//        Vector3f vector = new Vector3f(0.7f, 0.0f, -10.0f);
//        trans3d.setTranslation(vector);
//        tg.setTransform(trans3d);

        // set light source
        Color3f lightColor = new Color3f(1.0f, 1.0f, 0.9f);
        Vector3f lightDirection = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bound);
        light.setDirection(new Vector3f(0.15f, 0.0f, -100.0f));
        rootBranchGroup.addChild(light);

        // load object
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Sphere sph = new Sphere(0.5f);  // create planet(class)
        objTrans.addChild(sph);
        tg.addChild(objTrans);
        Vector3f vector = new Vector3f(2.0f, 0.0f, -20.0f); // set the location of object
        trans3d.setTranslation(vector);
        tg.setTransform(trans3d);

        // obj self-rotate
//        Transform3D yAxis = new Transform3D();
//        Alpha rotationAlpha = new Alpha(-1,Alpha.INCREASING_ENABLE,
//                0,0,
//                4000,0,0,
//                0,0,0);
//        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objTrans,yAxis,0.0f,(float)Math.PI*2.0f);
//        rotator.setSchedulingBounds(bound);
//        objTrans.addChild(rotator);

        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(tg);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        rootBranchGroup.addChild(myMouseRotate);

        MouseTranslate myMouseTranslate = new MouseTranslate();
        myMouseTranslate.setTransformGroup(tg);
        myMouseTranslate.setSchedulingBounds(new BoundingSphere());
        rootBranchGroup.addChild(myMouseTranslate);


        rootBranchGroup.compile();

        return rootBranchGroup;
    }
}
