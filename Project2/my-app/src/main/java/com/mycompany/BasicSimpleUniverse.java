package com.mycompany;

import Compare.Compare;
import com.mycompany.app.CombineInfo;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.*;

public class BasicSimpleUniverse extends Applet implements ActionListener, KeyListener {
    public SimpleUniverse universe;
    public BranchGroup rootBranchGroup;
    public BoundingSphere bound;
    public TransformGroup transformGroup;
    public Transform3D transform3D = new Transform3D();
    public MovingView view;

    public Map<String, List<String>> classmethodinfo;
    public Map<String, List<String>> classextendinfo;
    public Map<String, List<String>> classimpleinfo;
    public Map<String, List<String>> classdependinfo;
    public Map<String, List<String>> classLoopMethodinfo;
    public Map<String, List<String>> classmethodinfo2;
    public Map<String, List<String>> classextendinfo2;
    public Map<String, List<String>> classimpleinfo2;
    public Map<String, List<String>> classdependinfo2;
    public Map<String, List<String>> classLoopMethodinfo2;
    public List<String> addClass;
    public List<String> deleteClass;
    public Map<String, List<String>> addMethod;
    public Map<String, List<String>> deleteMethod;
    public Map<String, List<String>> addExtend;
    public Map<String, List<String>> deleteExtend;
    public Map<String, List<String>> addImple;
    public Map<String, List<String>> deleteImple;
    public Map<String, List<String>> addDep;
    public Map<String, List<String>> deleteDep;
    public int classNum;
    public int classNum2;

    public HashMap<String, Point3f> classLocMap = new HashMap();


    public static final Color3f BLACK = new Color3f(0.0f, 0.0f, 0.0f);
    public static final Color3f WHITE = new Color3f(1.0f, 1.0f, 1.0f);

//    public static void main(String[] args){
//        new MainFrame(new BasicSimpleUniverse(cbi1), 700, 700);
//    }

    public BasicSimpleUniverse(CombineInfo cbi1, CombineInfo cbi2, Compare compare) {
        set_up(cbi1);
        set_up2(cbi2);
        set_up(compare);


        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        canvas.setSize(700, 700);
        rootBranchGroup = new BranchGroup();
        add("Center", canvas);
//        BranchGroup scene = createSceneGraph();

        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();

        universe.getViewer().getView().setBackClipDistance(300.0);

        canvas.addKeyListener(this);

        universe.addBranchGraph(createSceneGraph());

    }

    private void set_up(CombineInfo cbi1) {
        classmethodinfo = cbi1.getmethodmap();
        classextendinfo = cbi1.getextendmap();
        classimpleinfo = cbi1.getintermap();
        classdependinfo = cbi1.getdependmap();
        classLoopMethodinfo = cbi1.getClassLoopinfo();
        classNum = classmethodinfo.size();
        System.out.println("XXXXXXXXXXXXXNumber of class of 1st commit: " + classLoopMethodinfo);
    }

    private void set_up2(CombineInfo cbi2) {
        classmethodinfo2 = cbi2.getmethodmap();
        classextendinfo2 = cbi2.getextendmap();
        classimpleinfo2 = cbi2.getintermap();
        classdependinfo2 = cbi2.getdependmap();
        classLoopMethodinfo2 = cbi2.getClassLoopinfo();
        classNum2 = classmethodinfo2.size();
        System.out.println("YYYYYYYYYYYYNumber of class of 2nd commit: " + classLoopMethodinfo2);
    }


    private void set_up(Compare compare) {
        addClass = compare.compareClass().get(0);
        deleteClass = compare.compareClass().get(1);
        addMethod = compare.methodAdd();
        deleteMethod = compare.methodDelet();
        addExtend = compare.addextend();
        deleteExtend = compare.removeextend();
        addImple = compare.addinterface();
        deleteImple = compare.removeinterface();
        addDep = compare.adddep();
        deleteDep = compare.removedep();
    }


    public BranchGroup createSceneGraph() {
        TransformGroup tg = new TransformGroup();
        Transform3D trans3d = new Transform3D();
        trans3d.setScale(0.8);
        tg.setTransform(trans3d);
        rootBranchGroup.addChild(tg);

        // set background
        TransformGroup moveGroup = new TransformGroup();
        Transform3D move = new Transform3D();
        move.setTranslation(new Vector3f(0.15f, 0.0f, -100.0f));

        bound = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
        TextureLoader backgroundTexture = new TextureLoader("./Project2/my-app/universe2.jpg", this);
        Background bg = new Background(backgroundTexture.getImage());
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);
        bg.setApplicationBounds(bound);
        tg.setTransform(move);
        tg.addChild(bg);
        rootBranchGroup.addChild(moveGroup);

        //view

        transformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.getViewer().getView().setDepthBufferFreezeTransparent(false);

        KeyNavigatorBehavior keyNavigator = new KeyNavigatorBehavior(transformGroup);
        keyNavigator.setSchedulingBounds(bound);
//        PlatformGeometry platformGeometry = new PlatformGeometry();
//        platformGeometry.addChild(keyNavigator);
//        universe.getViewingPlatform().setPlatformGeometry(platformGeometry);
        BranchGroup branchGroup = new BranchGroup();
        branchGroup.addChild(keyNavigator);
        universe.addBranchGraph(branchGroup);

        rootBranchGroup.addChild(createViewGraph());
//        rootBranchGroup.addChild(createSatellite());




        // set light source
        Color3f lightColor = new Color3f(1.0f, 1.0f, 0.9f);
        Vector3f lightDirection = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bound);
        light.setDirection(new Vector3f(0.15f, 0.0f, -100.0f));
        rootBranchGroup.addChild(light);

        // create a sphere for each class
        classmethodinfo.forEach((className, methodList) -> {
            float xrandom = randomFloatOne();
            float yrandom = randomFloatOne();
            float zrandom = randomFloatOne();
            addSphere(className, 0.05f, xrandom, yrandom, zrandom);
        });
        // create a blinking sphere for each newly added class
        for (String className : addClass) {
            float xrandom = randomFloatOne();
            float yrandom = randomFloatOne();
            float zrandom = randomFloatOne();
            addNewSphere(className, 0.05f, xrandom, yrandom, zrandom);
        }


        // draw lines
        if (!classextendinfo.isEmpty()) {
            classextendinfo.forEach((className, classList) -> {
                System.out.println("Class: " + className + "extends " + classList);
                for (String extendClass : classList) {
                    drawLine(className, extendClass, new Color3f(Color.green));
                }
            });
        }
        if (!classimpleinfo.isEmpty()) {
            classimpleinfo.forEach((className, classList) -> {
                System.out.println("Class: " + className + "implements " + classList);
                for (String impleClass : classList) {
                    drawLine(className, impleClass, new Color3f(Color.white));
                }
            });
        }
        if (!classdependinfo.isEmpty()) {
            classdependinfo.forEach((className, classList) -> {
                System.out.println("Class: " + className + "calls functions in " + classList);
                for (String depenClass : classList) {
                    drawLine(className, depenClass, new Color3f(Color.blue));
                }
            });
        }


        // newly added relation of classes
        addExtend.forEach((className, classList) -> {
            for (String s : classList) {
                drawNewLine(className, s, new Color3f(Color.green));
            }
        });
        addImple.forEach((className, classList) -> {
            for (String s : classList) {
                drawNewLine(className, s, new Color3f(Color.white));
            }
        });
        addDep.forEach((className, classList) -> {
            for (String s : classList) {
                drawNewLine(className, s, new Color3f(Color.blue));
            }
        });

        System.out.println("2nd Commit added classes relations: ");
        System.out.println("Class extends:");
        System.out.println(classextendinfo2);
        System.out.println("Class implements:");
        System.out.println(classimpleinfo2);
        System.out.println("Class function calls:");
        System.out.println(classdependinfo2);


        System.out.println();

        rootBranchGroup.compile();
        return rootBranchGroup;
    }

    public BranchGroup createViewGraph() {
        BranchGroup viewgroup = new BranchGroup();
        transformGroup = new TransformGroup();

        view = new MovingView();
        transformGroup.addChild(view);

        viewgroup.addChild(transformGroup);

        DirectionalLight directionalLight = new DirectionalLight(true, new Color3f(-0.5f,0.5f,1.0f), new Vector3f(-0.5f, 0.5f, 0.0f));

        directionalLight.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));

        viewgroup.addChild(directionalLight);

        viewgroup.compile();
        return viewgroup;




//        Integer viewNumber = 4;
//        Canvas3D[] canvas3D = new Canvas3D[viewNumber];
//        String viewOption[] = {"Front View", "Side View", "Plan View", "Zoom Out View"};
//        TransformGroup viewPointPlatform;
////        viewManager = new ViewManager(this, 1, 2);
//        BranchGroup view1 = new BranchGroup();
//        TransformGroup transformGroup = new TransformGroup();
//        view1.addChild(transformGroup);
//        GraphicsConfiguration configuration = SimpleUniverse.getPreferredConfiguration();
//        for(int i = 0; i < viewNumber; i++) {
//            canvas3D[i] = new Canvas3D(configuration);
//            viewPointPlatform = createViewPointPlatform(canvas3D[i], i);
//            transformGroup.addChild(viewPointPlatform);
//        }
//        return view1;

    }


    // a newly added class blinks several times
    private void addNewSphere(String className, float radius, float x, float y, float z) {
        Material mat = new Material();
        Appearance app = new Appearance();
        mat.setDiffuseColor(new Color3f(0.0f, 0.5f, 0.5f)); // color: blue-green
        app.setMaterial(mat);

        Sphere sphere = new Sphere(radius);
        sphere.setAppearance(app);
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        // set location of sphere at (x,y,z) in the scene
        transform.setTranslation(new Vector3f(x, y, z));
        tg.setTransform(transform);
//        tg.addChild(sphere);
        rootBranchGroup.addChild(tg);
        allowMouseRotateTranslate(tg);
        // add class name under the planet
        addText(className, x, y, z);
        // map to track the location of the sphere(class)
        classLocMap.put(className, new Point3f(x, y, z));

        TransparencyAttributes transparency = new TransparencyAttributes(1, 1.0f);
        transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
        transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);

        app.setTransparencyAttributes(transparency);
        app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);

        Alpha alpha1 = new Alpha(5, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE,
                0, 0, 2000, 0, 0, 2000, 0, 0);
        TransparencyInterpolator transparency1 = new TransparencyInterpolator(alpha1, transparency, 0.0f, 1.0f);
        transparency1.setSchedulingBounds(bound);
        tg.addChild(transparency1);

        // add methods of the corresponding class
        Color3f randomcolor = randomColor3f();
        for (String method : classmethodinfo2.get(className)) {
            float xrandom = randomFloat(3 * radius, -3 * radius, radius);
            float yrandom = randomFloat(3 * radius, -3 * radius, radius);
            float zrandom = randomFloat(3 * radius, -3 * radius, radius);
            addNewCube(x + xrandom, y + yrandom, z + zrandom, randomcolor, className, method);
        }

//        for (String loop: classLoopMethodinfo.keySet()) {
//            float xrandom = randomFloat(3 * radius, -3 * radius, radius);
//            float yrandom = randomFloat(3 * radius, -3 * radius, radius);
//            float zrandom = randomFloat(3 * radius, -3 * radius, radius);
//            createSatellite();
//        }

//        for (String loop: classLoopMethodinfo.get(loop)) {
//
//        }
    }

    // draw a line between two planets(classes)
    private void drawLine(String className, String otherClass, Color3f color) {
        // check if the first commit contains the extend class
        if (classLocMap.containsKey(otherClass)) {
            TransformGroup tg = new TransformGroup();
            rootBranchGroup.addChild(tg);

            LineArray lineX = new LineArray(2, LineArray.COORDINATES);
            lineX.setCoordinate(0, classLocMap.get(className));
            lineX.setCoordinate(1, classLocMap.get(otherClass));

            Appearance app = new Appearance();
            ColoringAttributes ca = new ColoringAttributes();
            ca.setColor(color);
            app.setColoringAttributes(ca);
            Shape3D shapeLine = new Shape3D(lineX, app);
            tg.addChild(shapeLine);

            // deleted relations will blink forever
            deleteRelation(className, otherClass, tg, app, deleteExtend);
            deleteRelation(className, otherClass, tg, app, deleteImple);
            deleteRelation(className, otherClass, tg, app, deleteDep);
        }
    }

    private void drawNewLine(String className, String otherClass, Color3f color) {
        if (classLocMap.containsKey(otherClass)) {
            TransformGroup tg = new TransformGroup();
            rootBranchGroup.addChild(tg);

            LineArray lineX = new LineArray(2, LineArray.COORDINATES);
            lineX.setCoordinate(0, classLocMap.get(className));
            lineX.setCoordinate(1, classLocMap.get(otherClass));

            Appearance app = new Appearance();
            ColoringAttributes ca = new ColoringAttributes();
            ca.setColor(color);
            app.setColoringAttributes(ca);
            Shape3D shapeLine = new Shape3D(lineX, app);
            tg.addChild(shapeLine);

            // blinks several times
            TransparencyAttributes transparency = new TransparencyAttributes(1, 1.0f);
            transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
            transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);

            app.setTransparencyAttributes(transparency);
            app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
            app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);

            Alpha alpha1 = new Alpha(6, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE,
                    0, 0, 2000, 0, 0, 2000, 0, 0);
            TransparencyInterpolator transparency1 = new TransparencyInterpolator(alpha1, transparency, 0.0f, 1.0f);
            transparency1.setSchedulingBounds(bound);
            tg.addChild(transparency1);

        }
    }


    private void deleteRelation(String className, String otherClass, TransformGroup tg, Appearance app,
                                Map<String, List<String>> relation) {
        if (relation.containsKey(className) && relation.get(className).contains(otherClass)) {
            TransparencyAttributes transparency = new TransparencyAttributes(1, 1.0f);
            transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
            transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);

            app.setTransparencyAttributes(transparency);
            app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
            app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);

            Alpha alpha1 = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE,
                    0, 0, 2000, 0, 0, 2000, 0, 0);
            TransparencyInterpolator transparency1 = new TransparencyInterpolator(alpha1, transparency, 0.0f, 1.0f);
            transparency1.setSchedulingBounds(bound);
            tg.addChild(transparency1);
        }
    }

    private float randomFloatOne() {
        Random r = new Random();
        float random = -1.0f + r.nextFloat() * (1.0f - (-1.0f));
        return random;
    }

    public void addSphere(String className, float radius, float x, float y, float z) {

//        float xrand = randomFloat( radius, -1 * radius, radius);
//        float yrand = randomFloat(radius, -1 * radius, radius);
//        float zrand = randomFloat(radius, -1 * radius, radius);
//
//
        Material mat = new Material();
        Appearance app = new Appearance();
        mat.setDiffuseColor(new Color3f(0.0f, 0.5f, 1.0f)); // color: baby blue
        app.setMaterial(mat);
//
//        Sphere sphere = new Sphere(radius,app);
////        sphere.setAppearance(app);
//        TransformGroup tg = new TransformGroup();
//        Transform3D transform = new Transform3D();
//        // set location of sphere at (x,y,z) in the scene
//        transform.setTranslation(new Vector3f(x, y, z));
//        tg.setTransform(transform);
//
//        TransformGroup tg_loop = new TransformGroup();
//        tg_loop.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        tg_loop.addChild(sphere);
////        tg.addChild(sphere);
//        //
//        TransformGroup tg2 = new TransformGroup();
//        Transform3D t3d = new Transform3D();
//
//        t3d.setTranslation(new Vector3d(x+ xrand, y+yrand, z+zrand));
//        t3d.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, 3.0f));
//        t3d.setScale(0.1);
//
//        tg2.setTransform(t3d);
//
//        TransformGroup tg2_loop = new TransformGroup();
//
//        Appearance appearance = setupAppearance(new Color3f(0.5f, 0.5f, 0.5f));
//        tg2_loop.addChild(new Sphere(0.3f, appearance));
//        tg2_loop.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        tg2_loop.addChild(rotation(tg2_loop, 300L));
//
//        tg2.addChild(tg2_loop);
//        tg_loop.addChild(tg2);
//        tg.addChild(tg_loop);
//        tg.addChild(tg2);
        //
        TransformGroup tg = new TransformGroup();

        BranchGroup tg1 = createSatellite(radius, x, y, z);
        rootBranchGroup.addChild(tg1);
//        allowMouseRotateTranslate(tg);
        // add class name under the planet
        addText(className, x, y, z);
        // map to track the location of the sphere(class)
        classLocMap.put(className, new Point3f(x, y, z));

        // if the class is deleted at next commit, then sphere will keep blinking
        if (deleteClass.contains(className)) {
            TransparencyAttributes transparency = new TransparencyAttributes(1, 1.0f);
            transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
            transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);

            app.setTransparencyAttributes(transparency);
            app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
            app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);

            Alpha alpha1 = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE,
                    0, 0, 1000, 0, 0, 1000, 0, 0);
            TransparencyInterpolator transparency1 = new TransparencyInterpolator(alpha1, transparency, 0.0f, 1.0f);
            transparency1.setSchedulingBounds(bound);
            tg.addChild(transparency1);

        }

        // for each method in this class, create a cube
        Color3f randomcolor = randomColor3f();
        int methodNum = classmethodinfo.get(className).size();
        System.out.println("Method Number of " + className + ": " + methodNum);
        for (String method : classmethodinfo.get(className)) {
            float xrandom = randomFloat(3 * radius, -3 * radius, radius);
            float yrandom = randomFloat(3 * radius, -3 * radius, radius);
            float zrandom = randomFloat(3 * radius, -3 * radius, radius);
            addCube(x + xrandom, y + yrandom, z + zrandom, randomcolor, className, method);
        }

        System.out.println("Add new methods !!!");
        if (addMethod.containsKey(className) && !addMethod.get(className).isEmpty()) {
            for (String method : addMethod.get(className)) {
                float xrandom = randomFloat(3 * radius, -3 * radius, radius);
                float yrandom = randomFloat(3 * radius, -3 * radius, radius);
                float zrandom = randomFloat(3 * radius, -3 * radius, radius);
                addNewCube(x + xrandom, y + yrandom, z + zrandom, randomcolor, className, method);
            }
        }

        rootBranchGroup.addChild(tg);

        // for each keyset of forloop, create a sphere
//        if (classLoopMethodinfo.containsKey(className)) {
//            System.out.println("0000000000");
//            for (String loop: classLoopMethodinfo.keySet()) {
//                float xrandom = randomFloat(3 * radius, -3 * radius, radius);
//                float yrandom = randomFloat(3 * radius, -3 * radius, radius);
//                float zrandom = randomFloat(3 * radius, -3 * radius, radius);
//                System.out.println(loop.toString());
////                createSatellite();
//                TransformGroup transformGroup = new TransformGroup();
//                transform3D = new Transform3D();
//
//                transform3D.setTranslation( new Vector3d(x + xrandom, y + yrandom, z + zrandom));
//                transform3D.setRotation(new AxisAngle4f(0.0f, 0.5f, 0.0f, 3.0f));
//                transform3D.setScale(0.5);
//
//                transformGroup.setTransform(transform3D);
//
//                Appearance ap = setupAppearance(new Color3f(0.0f, 2.0f, 0.6f));
//
//
//                rootBranchGroup.addChild(transformGroup);
//            }
//        }


    }

    private void addNewCube(float xpos, float ypos, float zpos, Color3f color, String className, String method) {
        Appearance app = new Appearance();
        Material mat = new Material(color, BLACK, color, WHITE, 128.0f);
        //mat.setShininess(100.0f);
        app.setMaterial(mat);
        float l = 0.015f;
        Box b = new Box(l, l, l, app);

        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        // set location of sphere at (x,y,z) in the scene
        transform.setTranslation(new Vector3f(xpos, ypos, zpos));
        tg.setTransform(transform);
        tg.addChild(b);
//        tg.addChild(rotation(tg, 100L));
        //cuberoate.addChild(tg);
        rootBranchGroup.addChild(tg);

        // the newly added method will blink several times
        TransparencyAttributes transparency = new TransparencyAttributes(1, 1.0f);
        transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
        transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);

        app.setTransparencyAttributes(transparency);
        app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);

        Alpha alpha1 = new Alpha(5, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE,
                0, 0, 2000, 0, 0, 2000, 0, 0);
        TransparencyInterpolator transparency1 = new TransparencyInterpolator(alpha1, transparency, 0.0f, 1.0f);
        transparency1.setSchedulingBounds(bound);
        tg.addChild(transparency1);

        allowMouseRotateTranslate(tg);

        // objRotate(sub);
    }

    private void addText(String className, float x, float y, float z) {
        Transform3D t3D = new Transform3D();
        t3D.setTranslation(new Vector3f(x, y + 0.05f, z));

        TransformGroup objMove = new TransformGroup(t3D);
        rootBranchGroup.addChild(objMove);

        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objMove.addChild(objSpin);

        Appearance textAppear = new Appearance();
        ColoringAttributes textColor = new ColoringAttributes();
        textColor.setColor(1.0f, 0.0f, 0.0f);
        textAppear.setColoringAttributes(textColor);
        textAppear.setMaterial(new Material());

        // Create a simple shape leaf node, add it to the scene graph.
        Font3D font3D = new Font3D(new Font("Helvetica", Font.PLAIN, 1),
                new FontExtrusion());
        Text3D textGeom = new Text3D(font3D, new String(className));
        textGeom.setAlignment(Text3D.ALIGN_CENTER);
        Shape3D textShape = new Shape3D();
        textShape.setGeometry(textGeom);
        textShape.setAppearance(textAppear);
        objSpin.addChild(textShape);

        Transform3D trans3d = new Transform3D();
        trans3d.setScale(0.08);
        objSpin.setTransform(trans3d);

    }

    // randomly pick up a float in the range (min, -radius) ,(radius, max)
    public float randomFloat(float max, float min, float radius) {
        Random r = new Random();

        float random = min + r.nextFloat() * (max - min);
        while (random > -radius && random < radius) {
            random = min + r.nextFloat() * (max - min);
        }
        return random;
    }


    // a cube represents a method
    public void addCube(float xpos, float ypos, float zpos, Color3f color, String className, String method) {
        Appearance app = new Appearance();
        Material mat = new Material(color, BLACK, color, WHITE, 128.0f);
        //mat.setShininess(100.0f);
        app.setMaterial(mat);
        float l = 0.015f;
        Box b = new Box(l, l, l, app);

        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        // set location of sphere at (x,y,z) in the scene
        transform.setTranslation(new Vector3f(xpos, ypos, zpos));
        tg.setTransform(transform);
        tg.addChild(b);
        rootBranchGroup.addChild(tg);

        // if the method is deleted in next commit, then the cube will keep blinking
        if (deleteMethod.containsKey(className)) {
            if (deleteMethod.get(className).contains(method)) {
                TransparencyAttributes transparency = new TransparencyAttributes(1, 1.0f);
                transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
                transparency.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);

                app.setTransparencyAttributes(transparency);
                app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
                app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);

                Alpha alpha1 = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE,
                        0, 0, 1000, 0, 0, 1000, 0, 0);
                TransparencyInterpolator transparency1 = new TransparencyInterpolator(alpha1, transparency, 0.0f, 1.0f);
                transparency1.setSchedulingBounds(bound);
                tg.addChild(transparency1);
            }
        }

        allowMouseRotateTranslate(tg);

        // if the method has for-loop in the body, then the rotation self rotates
        //objRotate(tg);
    }

    // randomly pick a brighter color
    public Color3f randomColor3f() {
        Random rand = new Random();
        float r = rand.nextFloat() / 2f + 0.5f;
        float g = rand.nextFloat() / 2f + 0.5f;
        float b = rand.nextFloat() / 2f + 0.5f;

        return new Color3f(r, g, b);
    }

    // not working here????
    public void objRotate(TransformGroup objTrans) {
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
                0, 0,
                4000, 0, 0,
                0, 0, 0);
        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objTrans, yAxis,
                0.0f, (float) Math.PI * 2.0f);
        rotator.setSchedulingBounds(bound);
        objTrans.addChild(rotator);
    }

    public void allowMouseRotateTranslate(TransformGroup tg) {
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

    public BranchGroup createSatellite( float radius, float x, float y, float z) {
//        BranchGroup branchGroup = new BranchGroup();
//        TransformGroup transformGroup = new TransformGroup();
//        Transform3D transform3D = new Transform3D();
//
//        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//
//        transform3D.setTranslation(new Vector3d(0.0, 0.0, -15.0));
//        transformGroup.setTransform(transform3D);
//
//
//
//        TransformGroup satellite_body = new TransformGroup();
//        Transform3D body3D = new Transform3D();
//
//        body3D.setTranslation(new Vector3d(0.0, 0.0, 0.0));
//        satellite_body.setTransform(body3D);
//
////
//        Appearance ap = new Appearance();
//        Material material = new Material();
//        material.setDiffuseColor(new Color3f(0.0f, 0.5f, 0.5f));
//        ap.setMaterial(material);
//        satellite_body.addChild(new Sphere(1.0f, ap));
//        satellite_body.addChild(rotation(satellite_body, 1000L));
//
//
//        transformGroup.addChild(satellite_body);
//
//
//        transformGroup.setTransform(transform3D);
//
//        DirectionalLight directionalLight = new DirectionalLight(true, new Color3f(-0.5f,0.5f,1.0f), new Vector3f(-0.5f, 0.5f, 0.0f));
//
//        directionalLight.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));
//
//        branchGroup.addChild(directionalLight);
//
//        branchGroup.addChild(transformGroup);
//
//        branchGroup.compile();
//
//        return branchGroup;
        float xrand = randomFloat( radius, -1 * radius, radius);
        float yrand = randomFloat(radius, -1 * radius, radius);
        float zrand = randomFloat(radius, -1 * radius, radius);


        BranchGroup group = new BranchGroup();
        TransformGroup tg = new TransformGroup();
        Transform3D transform3D = new Transform3D();

        transform3D.setTranslation(new Vector3d(x, y, z));
        transform3D.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f));
        transform3D.setScale(1.0);

        tg.setTransform(transform3D);

        TransformGroup tg_loop = new TransformGroup();


        Appearance ap = setupAppearance(new Color3f(0.0f, 0.0f, 0.5f));
        tg_loop.addChild(new Sphere(radius, ap));

        tg_loop.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        tg_loop.addChild(rotation(tg_loop, 10000L));

        TransformGroup tg2 = new TransformGroup();
        Transform3D tg2_3d = new Transform3D();

        tg2_3d.setTranslation(new Vector3d(x+ xrand, y+yrand, z+zrand));
        tg2_3d.setRotation(new AxisAngle4f(1.0f, 1.0f, 1.0f, 3.0f));
        tg2_3d.setScale(0.1);

        tg2.setTransform(tg2_3d);

        TransformGroup tg2_loop = new TransformGroup();

        Appearance ap_2 = setupAppearance(new Color3f(0.0f, 1.0f, 0.0f));
        tg2_loop.addChild(new Sphere(0.5f, ap_2));

        tg2_loop.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        tg2_loop.addChild(rotation(tg2_loop, 3000L));

        tg_loop.addChild(setupLight(6.0f, 1.0f, 1.0f, -0.3f, -0.2f, -1.0f));

        tg2_loop.addChild(setupLight(10.7f, 0.7f, 5.0f, -0.3f, -0.2f, 1.0f));


        tg2.addChild(tg2_loop);

        tg_loop.addChild(tg2);

        tg.addChild(tg_loop);


        group.addChild(tg);


        group.compile();

        return group;


    }

    private Appearance setupAppearance(Color3f a) {
        Appearance ap = new Appearance();
        Material material = new Material();
        material.setDiffuseColor(a);
        ap.setMaterial(material);
        return ap;
    }

    private Light setupLight(float a, float b, float c, float d, float e, float g) {
        DirectionalLight light = new DirectionalLight(true, new Color3f(a,b,c), new Vector3f(d,e,g));
        light.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));
        return light;
    }

    public RotationInterpolator rotation(TransformGroup transformGroup, long time) {
        Alpha alpha = new Alpha(-1, time);
        RotationInterpolator rotationInterpolator = new RotationInterpolator(alpha, transformGroup);
        rotationInterpolator.setSchedulingBounds(bound);

        return rotationInterpolator;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    class MovingView extends Behavior {

        private Transform3D transform3D = new Transform3D();
        private WakeupOnElapsedFrames moveFrame = null;

        public MovingView() {

            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
            this.setSchedulingBounds(bounds);
        }

        public void initialize() {
            moveFrame = new WakeupOnElapsedFrames(0);
            wakeupOn(moveFrame);
        }

        @Override
//    public void processStimulus(Enumeration enumeration) {
//
//    }

        public void processStimulus(Enumeration criteria) {
            transform3D.set(new Vector3d(0.0, 0.0, -0.1f));
            transformGroup.getTransform(transform3D);
            transform3D.mul(transform3D);
            transformGroup.setTransform(transform3D);

            wakeupOn(moveFrame);
        }
    }
}
