package backend;

import backend.compiler.CodeGenerator;
import backend.compiler.Executor;

/**
 * 
 * Backend component which is responsible for creating compiled and interpreter components
 */
public class BackendFactory {

	
	public static Backend createBackend(String component) throws Exception {
		if (component.equalsIgnoreCase("compiler")) {
			return new CodeGenerator();
		}
		else if (component.equalsIgnoreCase("interpreter")) {
			return new Executor();
		}
		else {
			throw new Exception("Backend factory: Invalid component " + component);
		}
	}
}
