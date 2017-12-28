package backend;

import backend.compiler.CodeGenerator;
import backend.interpreter.Executor;

/**
 * 
 * Backend component which is responsible for creating compiled and interpreter components
 */
public class BackendFactory {

	
	public static Backend createBackend(String component) throws Exception {
		if (component.equalsIgnoreCase("compile")) {
			return new CodeGenerator();
		}
		else if (component.equalsIgnoreCase("interpret")) {
			return new Executor();
		}
		else {
			throw new Exception("Backend factory: Invalid component " + component);
		}
	}
}
