package me.xcabbage.amniora.util;

public class Geometry {
	public Icosahedron ico;
	public float[] vertices;

	public Geometry() {
		ico = new Icosahedron();
		vertices = ico.verticesArray;
		for (int a = 0; a < vertices.length; a = a + 3)
			System.out.println(vertices[a] + ", " + vertices[a + 1] + ", "
					+ vertices[a + 2]);
	}
}
