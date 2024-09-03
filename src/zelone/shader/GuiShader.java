package zelone.shader;

import org.lwjgl.util.vector.Matrix4f;
import zelone.shader.ShaderProgram;



public class GuiShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "src/zelone/guis/guiVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/zelone/guis/guiFragmentShader.txt";
	
	private int location_transformationMatrix;

	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	//@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

    @Override
    protected void getUniformLocations() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
	
	
	

}
