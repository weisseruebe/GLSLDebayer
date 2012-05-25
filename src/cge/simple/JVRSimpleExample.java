package cge.simple;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;

import de.bht.jvr.collada14.loader.ColladaLoader;
import de.bht.jvr.core.CameraNode;
import de.bht.jvr.core.Finder;
import de.bht.jvr.core.GroupNode;
import de.bht.jvr.core.PointLightNode;
import de.bht.jvr.core.Printer;
import de.bht.jvr.core.SceneNode;
import de.bht.jvr.core.ShaderMaterial;
import de.bht.jvr.core.ShaderProgram;
import de.bht.jvr.core.ShapeNode;
import de.bht.jvr.core.Texture2D;
import de.bht.jvr.core.Transform;
import de.bht.jvr.core.pipeline.Pipeline;
import de.bht.jvr.core.uniforms.UniformFloat;
import de.bht.jvr.core.uniforms.UniformSampler2D;
import de.bht.jvr.core.uniforms.UniformValue;
import de.bht.jvr.core.uniforms.UniformVector2;
import de.bht.jvr.core.uniforms.UniformVector4;
import de.bht.jvr.math.Vector2;
import de.bht.jvr.math.Vector4;
import de.bht.jvr.renderer.AwtRenderWindow;
import de.bht.jvr.renderer.RenderWindow;
import de.bht.jvr.renderer.Viewer;
import de.bht.jvr.util.Color;
import de.bht.jvr.util.InputState;
import de.bht.jvr.util.StopWatch;

/**
 * This basic sample demonstrates how to setup a very simple jVR application. It
 * opens a single window that shows a centered box geometry which is lit by a
 * point light source and can be rotated interactively with some keys. This
 * example uses the default phong shader that is applied to geometry loaded from
 * Collada files.
 * 
 * @author Marc Roßbach
 * @author Henrik Tramberend
 */

public class JVRSimpleExample {

    public static void main(String[] args) throws Exception {
        GroupNode root = new GroupNode("scene root");
        GroupNode t = new GroupNode("t");
        SceneNode box = ColladaLoader.load(new File("res/plane.dae"));
        PointLightNode light = new PointLightNode("sun");
        light.setEnabled(true);
        light.setAmbientColor(new Color(1,1,1));
        light.setColor(new Color(1,1,1));
        light.setTransform(Transform.translate(3, 0, 3));

        CameraNode camera = new CameraNode("camera", 4f / 3f, 60);
        camera.setTransform(Transform.translate(0, 0, 10));
        t.addChildNode(box);
        float scale = 4.7f;
        t.setTransform(Transform.scale(scale,scale/1.5f,1));
        root.addChildNodes(t, light, camera);
        
        Printer.print(root);
    
        ShaderProgram lightingProgram = new ShaderProgram( 
                new File("shader/malvar.vs"),
                new File("shader/malvar.fs"));  
    
        lightingProgram.setParameter(GL2GL3.GL_GEOMETRY_VERTICES_OUT_ARB, 40);
        lightingProgram.setParameter(GL2GL3.GL_GEOMETRY_INPUT_TYPE_ARB,	GL2.GL_TRIANGLE_STRIP);
        lightingProgram.setParameter(GL2GL3.GL_TEXTURE_MAG_FILTER,	GL2.GL_NEAREST);
        lightingProgram.setParameter(GL2GL3.GL_TEXTURE_MIN_FILTER,	GL2.GL_NEAREST);

        ShaderMaterial boardMat = new ShaderMaterial();
        boardMat.setShaderProgram("LIGHTING", lightingProgram);
        boardMat.setTexture("LIGHTING", "source", new Texture2D(new File("res/igel.jpg")));
        BufferedImage f = ImageIO.read(new File("res/igel.jpg"));
        float w = f.getWidth();
        float h = f.getHeight();
        
        Finder.find(t, ShapeNode.class, "Plane01_Shape").setMaterial(boardMat);
        
        Pipeline pipeline = new Pipeline(root);
        pipeline.setUniform("sourceSize", new UniformVector4(new Vector4(w,h,1/w,1/h)));
        pipeline.setUniform("firstRed", new UniformVector2(new Vector2(1,1)));

        pipeline.clearBuffers(true, true, new Color(0, 0, 0));
        pipeline.switchCamera(camera);
       
        //pipeline.drawGeometry("LIGHTING", null);
        pipeline.doLightLoop(true, true).drawGeometry("LIGHTING",null);

        InputState input = new InputState();
        RenderWindow win = new AwtRenderWindow(pipeline, (int)w, (int)h);
        win.addKeyListener(input);

        StopWatch time = new StopWatch();
        Viewer v = new Viewer(win);

        float angleY = 0;
        float angleX = 0;
        float speed = 90;

        while (v.isRunning()) {
            float elapsed = time.elapsed();

            if (input.isOneDown('W', java.awt.event.KeyEvent.VK_UP))
                angleX += elapsed * speed;
            if (input.isOneDown('S', java.awt.event.KeyEvent.VK_DOWN))
                angleX -= elapsed * speed;
            if (input.isOneDown('D', java.awt.event.KeyEvent.VK_RIGHT))
                angleY += elapsed * speed;
            if (input.isOneDown('A', java.awt.event.KeyEvent.VK_LEFT))
                angleY -= elapsed * speed;
            if (input.isOneDown('P'))
                scale += 0.01;
            if (input.isOneDown('O'))
                scale -= 0.01;
            t.setTransform(Transform.scale(scale,scale/(800/533f),1));
            box.setTransform(Transform.rotateYDeg(angleY).mul(Transform.rotateXDeg(angleX)));

            if (input.isDown('Q'))
                System.exit(0);

            v.display();
        }
    }
}
