package com.mycompany;
import java.applet.Applet;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class BasicSimpleUniverse extends Applet{
    public SimpleUniverse universe;
    public BranchGroup rootBranchGroup;
    public BoundingSphere bound;

    public HashMap<String,Point3f> classLocMap = new HashMap();
    public int numClass;

    public static void main(String[] args){
        new MainFrame(new BasicSimpleUniverse(), 700, 700);
    }

    public BasicSimpleUniverse(){
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        canvas.setSize(700,700);
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
        trans3d.setScale(10);
        tg.setTransform(trans3d);
        rootBranchGroup.addChild(tg);

        // set background
        TransformGroup moveGroup = new TransformGroup();
        Transform3D move = new Transform3D();
        move.setTranslation(new Vector3f(0.15f, 0.0f, -100.0f));

        bound = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0);
        TextureLoader backgroundTexture = new TextureLoader("./Project2/my-app/universe2.jpg", this);
        Background bg = new Background(backgroundTexture.getImage());
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);
        bg.setApplicationBounds(bound);
       // rootBranchGroup.addChild(bg);
//        moveGroup.setTransform(move);
//        moveGroup.addChild(bg);
        tg.setTransform(move);
        tg.addChild(bg);
        rootBranchGroup.addChild(moveGroup);

        // set light source
        Color3f lightColor = new Color3f(1.0f, 1.0f, 0.9f);
        Vector3f lightDirection = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bound);
        light.setDirection(new Vector3f(0.15f,0.0f,-100.0f));
        rootBranchGroup.addChild(light);

        // create a sphere for each class
        addSphere(0.15f,.02f,0.0f,-5.0f);
        addSphere(0.15f, 0.7f, -0.8f, -3.0f);

        // draw line to represent class
        LineArray lineX = new LineArray(2, LineArray.COORDINATES);
        lineX.setCoordinate(0, new Point3f(0.02f, 0.0f, -5.0f));
        lineX.setCoordinate(1, new Point3f(0.7f, -0.8f, -3.0f));
        rootBranchGroup.addChild(new Shape3D(lineX));

        rootBranchGroup.compile();

        return rootBranchGroup;
    }

    public void addSphere(float radius, float x, float y, float z) {
        Sphere sphere = new Sphere(radius);
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        // set location of sphere at (x,y,z) in the scene
        transform.setTranslation(new Vector3f(x, y, z));
        tg.setTransform(transform);
        tg.addChild(sphere);
        rootBranchGroup.addChild(tg);
        allowMouseRotateTranslate(tg);
        // map to track the location of the sphere(class)
        //classLocMap.put(classname, new Point3f(x,y,z));

        // for each method in this class, create a cube
        //int k = getMethodNum(className);
        for (int k = 0; k<5;k++){
            float xrandom = randomFloat(3*radius,-3*radius,radius);
            float yrandom = randomFloat(3*radius,-3*radius,radius);
            float zrandom = randomFloat(3*radius,-3*radius,radius);
            addCube(x+xrandom,y+yrandom,z+zrandom);
        }


    }
    // randomly pick up a float in the range (min, -radius) ,(radius, max)
    public float randomFloat(float max, float min, float radius){
        Random r = new Random();
        float random = min + r.nextFloat() * (max-min);
        System.out.println(random);
        while (random>-radius && random< radius){
            random = min + r.nextFloat() * (max-min);
            System.out.println(random);
        }

        return random;
    }

    public void addCube(float xpos, float ypos, float zpos) {
        Appearance app = new Appearance();
        Color3f color = new Color3f(Color.blue);
        Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

        //app.setMaterial(new Material(color, black, color, white, 70f));
        app.setMaterial(new Material(color, black, color, white, 70f));
        float l = 0.05f;
        Box b = new Box(l,l,l,app);

        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        // set location of sphere at (x,y,z) in the scene
        transform.setTranslation(new Vector3f(xpos, ypos, zpos));
        tg.setTransform(transform);
        tg.addChild(b);
        rootBranchGroup.addChild(tg);

        allowMouseRotateTranslate(tg);

        // TODO:if there's for loop in this method, then make the cube self-rotating
        //objRotate(tg);
    }

    // not working here
    public void objRotate(TransformGroup objTrans) {
        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1,Alpha.INCREASING_ENABLE,
                0,0,
                4000,0,0,
                0,0,0);
        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objTrans,yAxis,0.0f,(float)Math.PI*2.0f);
        rotator.setSchedulingBounds(bound);
        objTrans.addChild(rotator);
    }

    public void allowMouseRotateTranslate(TransformGroup tg){
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
    }

}
