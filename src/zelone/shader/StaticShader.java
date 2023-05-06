/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zelone.shader;

import zelone.entities.Camera;
import zelone.entities.Light;
import zelone.toolBox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Jhawar
 */
public class StaticShader extends ShaderProgram

{

    private static final String VERTIX_FILE = "src/zelone/shader/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/zelone/shader/fragmentShader.glsl";
    private int location_transformationMatrix;
    private int loction_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightColor;
    private int locaiton_lightPosition;
    private int location_reflectivity;
    private int location_shineDamper;
    private int location_useFakeLighting;
    private int location_density;
    private int location_gradient;
    private int location_sky_color;

    public StaticShader()
    {
        super(VERTIX_FILE, FRAGMENT_FILE);

    }

    @Override
    protected void bindAttributes()
    {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getUniformLocations()
    {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        loction_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        locaiton_lightPosition = super.getUniformLocation("lightPositon");
        location_lightColor = super.getUniformLocation("lightColor");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_density = super.getUniformLocation("density");
        location_gradient = super.getUniformLocation("gradient");
        location_sky_color = super.getUniformLocation("skyColor");
    }

    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix)
    {
        super.loadMatrix(loction_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera)
    {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadShineVariables(float shineDamper, float reflectivity)
    {
        super.loadFloat(location_reflectivity, reflectivity);
        super.loadFloat(this.location_shineDamper, shineDamper);

    }

    public void loadLight(Light light)
    {
        super.loadVector(locaiton_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());

    }

    public void loadFog(float fog_density, float fog_gradient, float r,float g,float b)
    {
        super.loadFloat(location_density, fog_density);
        super.loadFloat(location_gradient, fog_gradient);
        super.loadVector(location_sky_color, new Vector3f(r,g,b));
    }

    public void useFakeLighting(boolean useFakeLighting)
    {
        super.loadBoolean(location_useFakeLighting, useFakeLighting);
    }
}
