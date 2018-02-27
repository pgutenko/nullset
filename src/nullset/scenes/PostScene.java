package nullset.scenes;

import mote4.scenegraph.Scene;
import mote4.util.shader.ShaderMap;
import mote4.util.texture.TextureMap;
import mote4.util.vertex.mesh.MeshMap;

import static org.lwjgl.opengl.GL11.*;

public class PostScene implements Scene {

    public PostScene() {

    }

    @Override
    public void update(double time, double delta) {

    }

    @Override
    public void render(double time, double delta) {
        glDisable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT);

        ShaderMap.use("quad");
        TextureMap.bind("fbo_scene");
        MeshMap.render("quad");
    }

    @Override
    public void framebufferResized(int width, int height) {

    }

    @Override
    public void destroy() {

    }
}
