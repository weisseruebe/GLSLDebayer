attribute vec4 jvr_Vertex;
attribute vec2 jvr_TexCoord;

uniform mat4  jvr_ProjectionMatrix;
uniform mat4  jvr_ModelViewMatrix;
uniform sampler2D texture0;

varying vec2 texture_coordinate;

void main(void)
{
	gl_Position = jvr_ProjectionMatrix * jvr_ModelViewMatrix * jvr_Vertex;
	texture_coordinate = jvr_TexCoord;
}