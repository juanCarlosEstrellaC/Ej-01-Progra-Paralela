package com.programacion;

// Importamos las clases necesarias para trabajar con LWJGL (ventanas y OpenGL)
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.GLFW.*;

public class EjemploMainMio {

    // Declaramos la variable `window` para manejar la ventana principal
    private static long window;

    // Metodo principal para ejecutar el programa
    static void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!"); // Muestra la versión de LWJGL
        init(); // Llama al método para inicializar la ventana
        loop(); // Llama al método que mantiene el programa en un bucle para renderizar
    }

    // Metodo de inicialización de la ventana y configuración de OpenGL
    static void init() {
        // Configuramos un callback para mostrar errores en la consola
        GLFWErrorCallback.createPrint(System.err).set();

        // Inicializamos GLFW y verificamos que la inicialización fue exitosa
        if (!glfwInit()) {
            throw new IllegalStateException("No se pudo inicializar GLFW");
        }

        // Configuramos GLFW: ocultamos la ventana después de crearla y permitimos que sea redimensionable
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Oculta la ventana al inicio
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Permite que la ventana se redimensione

        // Creamos la ventana con dimensiones 800x600 y el título "Ejemplo 01"
        window = glfwCreateWindow(800, 600, "Ejemplo 01 MIO", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("No se pudo crear la ventana GLFW");
        }

        // Centramos la ventana en la pantalla
        {
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - 800) / 2,
                    (vidmode.height() - 600) / 2
            );
        }

        // Configuramos un callback para cerrar la ventana al presionar la tecla ESCAPE
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // Cierra la ventana
            }
        });

        // Ajustamos el área de dibujo cuando se cambia el tamaño de la ventana
        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            glViewport(0, 0, width, height);
        });

        // Hacemos que el contexto de OpenGL sea el actual
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // Imprimimos información de la versión de OpenGL, el proveedor y el renderizador
        String version = glGetString(GL_VERSION);
        String vendor = glGetString(GL_VENDOR);
        String renderer = glGetString(GL_RENDERER);
        System.out.println("OpenGL version: " + version);
        System.out.println("OpenGL vendor: " + vendor);
        System.out.println("OpenGL renderer: " + renderer);

        // Configuramos una proyección ortogonal para trabajar en 2D
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-1, 1, -1, 1, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        // Activamos el V-Sync para que la animación sea más fluida
        glfwSwapInterval(1);

        // Finalmente, mostramos la ventana en la pantalla
        glfwShowWindow(window);
    }

    // Metodo de bucle principal que mantiene la ventana abierta y renderiza el contenido
    static void loop() {
        // Configuramos el color de fondo de la ventana (negro)
        glClearColor(0f, 0f, 0f, 0f);

        // Bucle principal de renderizado, se ejecuta mientras la ventana esté abierta
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Limpia el framebuffer

            paintTriangulos(); // Llama al metodo para dibujar un triángulo

            glfwSwapBuffers(window); // Intercambia los búferes de color para actualizar la ventana
            glfwPollEvents(); // Escucha eventos de la ventana (teclado, mouse, etc.)
        }
    }

    // Metodo que dibuja un triángulo en la ventana
    static void paintTriangulos() {
        glBegin(GL_TRIANGLES); // Inicia el dibujo de un triángulo
        glVertex2d(-1, -1); // Vértice 1 en la esquina inferior izquierda
        glVertex2d(0, 0);   // Vértice 2 en el centro de la ventana
        glVertex2d(0, -1);  // Vértice 3 en la parte inferior central
        glEnd(); // Finaliza el dibujo del triángulo
    }

    // Metodo `main` que inicia la ejecución del programa
    public static void main(String[] args) {
        run(); // Llama al metodo `run` para ejecutar el programa
    }
}
