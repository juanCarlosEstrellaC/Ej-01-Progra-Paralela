package com.programacion;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;

import static java.awt.SystemColor.window;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.GLFW.*;

public class EjemploMain {



    private static long window;

    static void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();
    }

    static void init() {
        // Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // Optional: current window hints are the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // The window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // The window will be resizable

        // Create the window
        window = glfwCreateWindow(800, 600, "Ejemplo 01", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        {
            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - 800) / 2,
                    (vidmode.height() - 600) / 2
            );
        }


        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        glfwSetFramebufferSizeCallback(window,(window,width,heigth)->{
            glViewport(0,0,width,heigth);
        });
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // Output OpenGL version, vendor, and renderer
        String version = glGetString(GL_VERSION);
        String vendor =glGetString(GL_VENDOR);
        String renderer = glGetString(GL_RENDERER);
        System.out.println("OpenGL version: " + version);
        System.out.println("OpenGL vendor: " + vendor);
        System.out.println("OpenGL renderer: " + renderer);

        //PROYECCION ORTOGONAL
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-1,1,-1,1,-1,1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    static void loop() {
        // Set the clear color (purple background)
        glClearColor(0f, 0f, 0f, 0f);

        // Run the rendering loop until the user has attempted to close the window or pressed ESC
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear the framebuffer

            paintTriangulos();

            glfwSwapBuffers(window); // Swap the color buffers

            // Poll for window events (e.g., key presses)
            glfwPollEvents();

        }
    }

    static void paintTriangulos(){
        //DIBUJAR TRIANMGULOS
        glBegin(GL_TRIANGLES);
        {glVertex2d(-1,-1);
        glVertex2d(0,0);
        glVertex2d(0,-1);
        }
        glEnd();
    }
    public static void main(String[] args) {
        run();
        // Optional: Output OpenGL version
        // String version = GL11.glGetString(GL11.GL_VERSION);
        // System.out.println("OpenGL Version:" + version);
    }
}
