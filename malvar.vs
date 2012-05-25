attribute vec4 jvr_Vertex;
attribute vec2 jvr_TexCoord;

uniform mat4  jvr_ProjectionMatrix;
uniform mat4  jvr_ModelViewMatrix;



/** (w,h,1/w,1/h) */
uniform vec4 sourceSize;

/** Pixel position of the first red pixel in the Bayer pattern. [{0,1}, {0,1}]*/ 
uniform vec2 firstRed;

/** .xy = Pixel being sampled in the fragment shader on the range [0, 1] .zw = ...on the range [0, sourceSize], offset by firstRed */
varying vec4 center;

/** center.x + (-2/w, -1/w, 1/w, 2/w); These are the x-positions of the adjacent pixels.*/ 
varying vec4 xCoord;

/** center.y + (-2/h, -1/h, 1/h, 2/h); These are the y-positions of the adjacent pixels.*/ 
varying vec4 yCoord;

void main(void) {
	center.xy = jvr_TexCoord.xy;
	center.zw = jvr_TexCoord.xy * sourceSize.xy + firstRed;
	vec2 invSize = sourceSize.zw;
	xCoord = center.x + vec4(-2.0 * invSize.x, -invSize.x, invSize.x, 2.0 * invSize.x); 
	yCoord = center.y + vec4(-2.0 * invSize.y, -invSize.y, invSize.y, 2.0 * invSize.y);
	//gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex; 
	gl_Position = jvr_ProjectionMatrix * jvr_ModelViewMatrix * jvr_Vertex;
	
}