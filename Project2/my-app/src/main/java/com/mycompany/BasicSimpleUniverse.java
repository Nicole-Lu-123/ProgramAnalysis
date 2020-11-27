//package com.mycompany;
//import java.applet.Applet;
//import java.awt.*;
//import java.util.*;
//import java.util.List;
//
//import javax.media.j3d.*;
//import javax.vecmath.Color3f;
//import javax.vecmath.Point3d;
//import javax.vecmath.Point3f;
//import javax.vecmath.Vector3f;
//
//import com.mycompany.app.CombineInfo;
//import com.sun.j3d.utils.applet.MainFrame;
//import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
//import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
//import com.sun.j3d.utils.geometry.Box;
//import com.sun.j3d.utils.geometry.Sphere;
//import com.sun.j3d.utils.image.TextureLoader;
//import com.sun.j3d.utils.universe.SimpleUniverse;
//
//public class BasicSimpleUniverse extends Applet{
//    public SimpleUniverse universe;
//    public BranchGroup rootBranchGroup;
//    public BoundingSphere bound;
//
//    public Map<String, java.util.List<String>> classmethodinfo;
//    public  Map<String, java.util.List<String>> classextendinfo;
//    public  Map<String, java.util.List<String>> classimpleinfo;
//    public  Map<String, List<String>> classdependinfo;
//    public int classNum;
//    public List<String> classNameList;
//
//    public HashMap<String,Point3f> classLocMap = new HashMap();
//
//    public static final Color3f BLUE = new Color3f(Color.blue);
//    public static final Color3f BLACK = new Color3f(0.0f, 0.0f, 0.0f);
//    public static final Color3f WHITE = new Color3f(1.0f, 1.0f, 1.0f);
//
////    public static void main(String[] args){
////        new MainFrame(new BasicSimpleUniverse(cbi1), 700, 700);
////    }
//
//    public BasicSimpleUniverse(CombineInfo cbi1){
//        set_up(cbi1);
//
//        setLayout(new BorderLayout());
//        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
//        Canvas3D canvas = new Canvas3D(config);
//        canvas.setSize(700,700);
//        rootBranchGroup = new BranchGroup();
//        add("Center", canvas);
//        BranchGroup scene = createSceneGraph();
//
//        universe = new SimpleUniverse(canvas);
//        universe.getViewingPlatform().setNominalViewingTransform();
//        universe.addBranchGraph(scene);
//
//    }
//
//    private void set_up(CombineInfo cbi1) {
//        classmethodinfo = cbi1.getmethodmap();
//        classextendinfo = cbi1.getextendmap();
//        classimpleinfo = cbi1.getintermap();
//        classdependinfo = cbi1.getdependmap();
//        classNum = classmethodinfo.size();
//        System.out.println("Number of class: " + classNum);
//    }
//
//    public BranchGroup createSceneGraph() {
//        TransformGroup tg = new TransformGroup();
//        Transform3D trans3d = new Transform3D();
//        trans3d.setScale(0.8);
//        tg.setTransform(trans3d);
//        rootBranchGroup.addChild(tg);
//
//        // set background
//        TransformGroup moveGroup = new TransformGroup();
//        Transform3D move = new Transform3D();
//        move.setTranslation(new Vector3f(0.15f, 0.0f, -100.0f));
//        bound = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0);
//        TextureLoader backgroundTexture = new TextureLoader("./Project2/my-app/universe2.jpg", this);
//        Background bg = new Background(backgroundTexture.getImage());
//        bg.setImageScaleMode(Background.SCALE_FIT_ALL);
//        bg.setApplicationBounds(bound);
//       // rootBranchGroup.addChild(bg);
////        moveGroup.setTransform(move);
////        moveGroup.addChild(bg);
//        tg.setTransform(move);
//        tg.addChild(bg);
//        rootBranchGroup.addChild(moveGroup);
//
//        // set light source
//        Color3f lightColor = new Color3f(1.0f, 1.0f, 0.9f);
//        Vector3f lightDirection = new Vector3f(4.0f, -7.0f, -12.0f);
//        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
//        light.setInfluencingBounds(bound);
//        light.setDirection(new Vector3f(0.15f,0.0f,-100.0f));
//        rootBranchGroup.addChild(light);
//
//        // create a sphere for each class
//        classmethodinfo.forEach((className, methodList)->{
//            float xrandom = randomFloatOne();
//            float yrandom = randomFloatOne();
//            float zrandom = randomFloatOne();
//            addSphere(className, 0.05f,xrandom,yrandom,zrandom);
//        });
//
//        // draw line of class extension
//        classextendinfo.forEach((className,classList)->{
//            for(String extendClass:classList){
//                drawLine(className, extendClass,new Color3f(Color.green));
//            }
//        });
//        classimpleinfo.forEach((className,classList)->{
//            for(String impleClass:classList){
//                drawLine(className, impleClass,new Color3f(Color.white));
//            }
//        });
//        classdependinfo.forEach((className,classList)->{
//            for(String depenClass:classList){
//                drawLine(className, depenClass,new Color3f(Color.blue));
//            }
//        });
//
//        rootBranchGroup.compile();
//
//        return rootBranchGroup;
//    }
//
//    // draw a line between two planets(classes)
//    private void drawLine(String className, String extendClass, Color3f color) {
//        LineArray lineX = new LineArray(2, LineArray.COORDINATES);
//        lineX.setCoordinate(0, classLocMap.get(className));
//        lineX.setCoordinate(1, classLocMap.get(extendClass));
//
//        Appearance app = new Appearance();
//        ColoringAttributes ca = new ColoringAttributes();
//        ca.setColor(color);
//        app.setColoringAttributes(ca);
//        Shape3D shapeLine = new Shape3D(lineX, app);
//        rootBranchGroup.addChild(shapeLine);
//    }
//
//    private float randomFloatOne() {
//        Random r = new Random();
//        float random = -1.0f + r.nextFloat() * (1.0f-(-1.0f));
//        return random;
//    }
//
//    public void addSphere(String className, float radius, float x, float y, float z) {
//        Sphere sphere = new Sphere(radius);
//        TransformGroup tg = new TransformGroup();
//        Transform3D transform = new Transform3D();
//        // set location of sphere at (x,y,z) in the scene
//        transform.setTranslation(new Vector3f(x, y, z));
//        tg.setTransform(transform);
//        tg.addChild(sphere);
//        rootBranchGroup.addChild(tg);
//        allowMouseRotateTranslate(tg);
//        // add class name under the planet
//        addText(className,x,y,z);
//        // map to track the location of the sphere(class)
//        classLocMap.put(className, new Point3f(x,y,z));
//
//        // for each method in this class, create a cube
//        Color3f randomcolor = randomColor3f();
//        int methodNum = classmethodinfo.get(className).size();
//        System.out.println("Method Number of "+className+": "+methodNum);
//        for (int k = 0; k<methodNum;k++){
//            float xrandom = randomFloat(3*radius,-3*radius,radius);
//            float yrandom = randomFloat(3*radius,-3*radius,radius);
//            float zrandom = randomFloat(3*radius,-3*radius,radius);
//            addCube(x+xrandom,y+yrandom,z+zrandom,randomcolor);
//        }
//
//
//    }
//
//    private void addText(String className, float x,float y, float z) {
//        Transform3D t3D = new Transform3D();
//        t3D.setTranslation(new Vector3f(x,y+0.05f,z));
//        TransformGroup objMove = new TransformGroup(t3D);
//        rootBranchGroup.addChild(objMove);
//
//        TransformGroup objSpin = new TransformGroup();
//        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        objMove.addChild(objSpin);
//
//        Appearance textAppear = new Appearance();
//        ColoringAttributes textColor = new ColoringAttributes();
//        textColor.setColor(1.0f, 0.0f, 0.0f);
//        textAppear.setColoringAttributes(textColor);
//        textAppear.setMaterial(new Material());
//
//        // Create a simple shape leaf node, add it to the scene graph.
//        Font3D font3D = new Font3D(new Font("Helvetica", Font.PLAIN, 1),
//                new FontExtrusion());
//        Text3D textGeom = new Text3D(font3D, new String(className));
//        textGeom.setAlignment(Text3D.ALIGN_CENTER);
//        Shape3D textShape = new Shape3D();
//        textShape.setGeometry(textGeom);
//        textShape.setAppearance(textAppear);
//        objSpin.addChild(textShape);
//
//
//        Transform3D trans3d = new Transform3D();
//        trans3d.setScale(0.08);
//        objSpin.setTransform(trans3d);
//
//    }
//
//    // randomly pick up a float in the range (min, -radius) ,(radius, max)
//    public float randomFloat(float max, float min, float radius){
//        Random r = new Random();
//        float random = min + r.nextFloat() * (max-min);
//        while (random>-radius && random< radius){
//            random = min + r.nextFloat() * (max-min);
//        }
//        return random;
//    }
//
//    public void addCube(float xpos, float ypos, float zpos, Color3f color) {
//        Appearance app = new Appearance();
//        //app.setMaterial(new Material(color, black, color, white, 70f));
//        app.setMaterial(new Material(color, BLACK, color, WHITE, 70f));
//        float l = 0.015f;
//        Box b = new Box(l,l,l,app);
//
//        TransformGroup tg = new TransformGroup();
//        Transform3D transform = new Transform3D();
//        // set location of sphere at (x,y,z) in the scene
//        transform.setTranslation(new Vector3f(xpos, ypos, zpos));
//        tg.setTransform(transform);
//        tg.addChild(b);
//        rootBranchGroup.addChild(tg);
//
//        allowMouseRotateTranslate(tg);
//
///        // TODO:if there's for loop in this method, then make the cube self-rotating
//        //objRotate(tg);
//    }
//
//    // randomly pick a brighter color
//    public Color3f randomColor3f() {
//        Random rand = new Random();
//        float r = rand.nextFloat() / 2f + 0.5f;
//        float g = rand.nextFloat() / 2f + 0.5f;
//        float b = rand.nextFloat() / 2f + 0.5f;
//        return new Color3f(r,g,b);
//    }
//
//    // not working here
//    public void objRotate(TransformGroup objTrans) {
//        Transform3D yAxis = new Transform3D();
//        Alpha rotationAlpha = new Alpha(-1,Alpha.INCREASING_ENABLE,
//                0,0,
//                4000,0,0,
//                0,0,0);
//        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objTrans,yAxis,0.0f,(float)Math.PI*2.0f);
//        rotator.setSchedulingBounds(bound);
//        objTrans.addChild(rotator);
//    }
//
//    public void allowMouseRotateTranslate(TransformGroup tg){
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
//    }
//
//}
