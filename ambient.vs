attribute vec4 jvr_Vertex;
attribute vec2 jvr_TexCoord;

varying vec2 texture_coordinate;

uniform mat4 jvr_ModelViewProjectionMatrix;

void main(void)
{
  gl_Position = jvr_ModelViewProjectionMatrix * jvr_Vertex;
  texture_coordinate = jvr_TexCoord;
}