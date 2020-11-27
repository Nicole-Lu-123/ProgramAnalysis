//// Reference: http://www.java2s.com/Code/Java/3D/BasicConstruct.htm
//
//package com.mycompany;
//
//import javax.media.j3d.*;
//import javax.swing.JFrame;
//import java.awt.*;
//import java.util.TimerTask;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import javax.swing.*;
//import com.sun.j3d.utils.geometry.Sphere;
//import com.sun.j3d.utils.universe.SimpleUniverse;
//import com.sun.j3d.utils.geometry.Box;
//import javax.vecmath.*;
//import com.sun.j3d.utils.behaviors.mouse.*;
//
//public class BasicConstruct extends JFrame{
//    public static final Color3f RED= new Color3f(1, 0, 0);
//    public static final Color3f BLUE = new Color3f(0,0,1);
//
//    protected SimpleUniverse simpleU;
//
//    protected BranchGroup rootBranchGroup;
//
//    // Constructor that consturcts the window with the given name.
//    public BasicConstruct(String name) {
//        // The next line will construct the window and name it with the given name
//        super(name);
//        // Perform the initial setup, just once
//        initial_setup();
//    }
//
//    protected void initial_setup() {
//        // A JFrame is a Container -- something that can hold
//        // other things, e.g a button, a textfield, etc..
//        // however, for a container to hold something, you need
//        // to specify the layout of the storage. For our
//        // example, we would like to use a BorderLayout.
//        // The next line does just this:
//        getContentPane().setLayout(new BorderLayout());
//
//        // The next step is to setup graphics configuration
//        // for Java3D. Since different machines/OS have differnt
//        // configuration for displaying stuff, therefore, for
//        // java3D to work, it is important to obtain the correct
//        // graphics configuration first.
//        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//
//        // Since we are doing stuff via java3D -- meaning we
//        // cannot write pixels directly to the screen, we need
//        // to construct a "canvas" for java3D to "paint". And
//        // this "canvas" will be constructed with the graphics
//        // information we just obtained.
//        Canvas3D canvas3D = new Canvas3D(config);
//
//        // And we need to add the "canvas to the centre of our window..
//        getContentPane().add("Center", canvas3D);
//
//        // Creates the universe
//        simpleU = new SimpleUniverse(canvas3D);
//
//        // First create the BranchGroup object
//        rootBranchGroup = new BranchGroup();
//    }
//
//    /**
//     * Adds a light source to the universe
//     *
//     * @param direction
//     *            The inverse direction of the light
//     * @param color
//     *            The color of the light
//     */
//    public void addDirectionalLight(Vector3f direction, Color3f color) {
//        // Creates a bounding sphere for the lights
//        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
//
//        // Then create a directional light with the given direction and color
//        DirectionalLight lightD = new DirectionalLight(color, direction);
//        lightD.setInfluencingBounds(bounds);
//
//        // Then add it to the root BranchGroup
//        rootBranchGroup.addChild(lightD);
//    }
//
//    // add a box to universe
//    public void addBox(float x, float y, float z, Color3f diffuse, Color3f spec) {
//        // Add a box with the given dimension
//
//        // First setup an appearance for the box
//        Appearance app = new Appearance();
//        Material mat = new Material();
//        mat.setDiffuseColor(diffuse);
//        mat.setSpecularColor(spec);
//        mat.setShininess(5.0f);
//        Transform3D transform = new Transform3D();
//
//        app.setMaterial(mat);
//        Box box = new Box(x, y, z, app);
//
//
//        // Create a TransformGroup and make it the parent of the box
//        TransformGroup tg = new TransformGroup();
//        Vector3f vector = new Vector3f(0.7f, 0.0f, .0f);
//        transform.setTranslation(vector);
//        tg.setTransform(transform);
//        tg.addChild(box);
//
//        // Then add it to the rootBranchGroup
//        rootBranchGroup.addChild(tg);
//
//        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
//
//        MouseRotate myMouseRotate = new MouseRotate();
//        myMouseRotate.setTransformGroup(tg);
//        myMouseRotate.setSchedulingBounds(new BoundingSphere());
//        rootBranchGroup.addChild(myMouseRotate);
//
//        MouseTranslate myMouseTranslate = new MouseTranslate();
//        myMouseTranslate.setTransformGroup(tg);
//        myMouseTranslate.setSchedulingBounds(new BoundingSphere());
//        rootBranchGroup.addChild(myMouseTranslate);
//
//        MouseZoom myMouseZoom = new MouseZoom();
//        myMouseZoom.setTransformGroup(tg);
//        myMouseZoom.setSchedulingBounds(new BoundingSphere());
//        rootBranchGroup.addChild(myMouseZoom);
//    }
//
//    //
//    public void addSphere(){
//        Sphere sphere = new Sphere(0.05f);
//        TransformGroup tg = new TransformGroup();
//        Transform3D transform = new Transform3D();
//
//        // set the transform to move (translate) the object to the location
//        Vector3f vector = new Vector3f(0.7f, 0.0f, .0f);
//        transform.setTranslation(vector);
//        tg.setTransform(transform);
//
//        tg.addChild(sphere);
//        rootBranchGroup.addChild(tg);
//
//        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
//
//        MouseRotate myMouseRotate = new MouseRotate();
//        myMouseRotate.setTransformGroup(tg);
//        myMouseRotate.setSchedulingBounds(new BoundingSphere());
//        rootBranchGroup.addChild(myMouseRotate);
//
//        MouseTranslate myMouseTranslate = new MouseTranslate();
//        myMouseTranslate.setTransformGroup(tg);
//        myMouseTranslate.setSchedulingBounds(new BoundingSphere());
//        rootBranchGroup.addChild(myMouseTranslate);
//
//        MouseZoom myMouseZoom = new MouseZoom();
//        myMouseZoom.setTransformGroup(tg);
//        myMouseZoom.setSchedulingBounds(new BoundingSphere());
//        rootBranchGroup.addChild(myMouseZoom);
//    }
//
//    // Finalise everything to get ready
//    public void finalise() {
//        // Then add the branch group into the Universe
//        simpleU.addBranchGraph(rootBranchGroup);
//        // And set up the camera position
//        simpleU.getViewingPlatform().setNominalViewingTransform();
//    }
//
//    public static void main(String[] argv) {
//        BasicConstruct bc = new BasicConstruct("SimpleUniverse");
//
//        bc.setSize(1000, 1000);
//        bc.addBox(0.6f, 0.2f, 0.2f, RED, RED);
//        //bc.addSphere();
//        bc.addDirectionalLight(new Vector3f(0f, 0f, -1), new Color3f(1f, 1f, 0f));
//        bc.finalise();
//
//        bc.setVisible(true);
//
//        return;
//    }
//}
